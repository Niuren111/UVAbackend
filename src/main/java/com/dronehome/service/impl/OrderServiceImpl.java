package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.OrderCreateDTO;
import com.dronehome.dto.OrderQueryDTO;
import com.dronehome.entity.Order;
import com.dronehome.entity.OrderItem;
import com.dronehome.entity.Product;
import com.dronehome.entity.User;
import com.dronehome.exception.BusinessException;
import com.dronehome.mapper.OrderItemMapper;
import com.dronehome.mapper.OrderMapper;
import com.dronehome.mapper.ProductMapper;
import com.dronehome.mapper.UserMapper;
import com.dronehome.service.OrderService;
import com.dronehome.service.ProductService;
import com.dronehome.service.UserService;
import com.dronehome.vo.OrderVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {
    
    @Autowired
    private OrderItemMapper orderItemMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProductService productService;
    
    @Autowired
    private UserService userService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public OrderVO createOrder(OrderCreateDTO orderCreateDTO, Long userId) {
        // 查询商品信息
        Product product = productMapper.selectById(orderCreateDTO.getProductId());
        if (product == null) {
            throw new BusinessException("商品不存在");
        }
        
        // 检查商品状态
        if (product.getStatus() != 1) {
            throw new BusinessException("商品已下架");
        }
        
        // 检查库存
        if (product.getStock() < orderCreateDTO.getQuantity()) {
            throw new BusinessException("商品库存不足");
        }
        
        // 创建订单
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(userId);
        order.setProductId(product.getId());
        order.setProductName(product.getName());
        order.setProductImage(product.getMainImage());
        order.setQuantity(orderCreateDTO.getQuantity());
        order.setPrice(product.getPrice());
        order.setTotalAmount(product.getPrice().multiply(new BigDecimal(orderCreateDTO.getQuantity())));
        order.setStatus(0); // 待付款
        
        // 保存订单
        save(order);
        
        // 减少商品库存
        int rows = productMapper.decreaseStock(product.getId(), orderCreateDTO.getQuantity());
        if (rows <= 0) {
            throw new BusinessException("库存不足");
        }
        
        return convertToVO(order);
    }
    
    @Override
    public OrderVO getOrderDetail(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        return convertToVO(order);
    }
    
    @Override
    public Page<OrderVO> pageOrders(OrderQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Order> queryWrapper = new LambdaQueryWrapper<>();
        
        if (queryDTO.getUserId() != null) {
            queryWrapper.eq(Order::getUserId, queryDTO.getUserId());
        }
        
        if (queryDTO.getProductId() != null) {
            queryWrapper.eq(Order::getProductId, queryDTO.getProductId());
        }
        
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(Order::getStatus, queryDTO.getStatus());
        }
        
        if (StringUtils.hasText(queryDTO.getOrderNo())) {
            queryWrapper.eq(Order::getOrderNo, queryDTO.getOrderNo());
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Order::getCreateTime);
        
        // 分页查询
        Page<Order> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Order> orderPage = page(page, queryWrapper);
        
        // 转换为VO
        Page<OrderVO> resultPage = new Page<>();
        BeanUtils.copyProperties(orderPage, resultPage, "records");
        
        List<OrderVO> orderVOList = orderPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        resultPage.setRecords(orderVOList);
        
        return resultPage;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean payOrder(Long id) {
        int rows = baseMapper.payOrder(id);
        return rows > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deliverOrder(Long id) {
        int rows = baseMapper.deliverOrder(id);
        return rows > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean confirmOrder(Long id) {
        int rows = baseMapper.completeOrder(id);
        
        if (rows > 0) {
            // 增加商品销量
            Order order = getById(id);
            productMapper.increaseSales(order.getProductId(), order.getQuantity());
        }
        
        return rows > 0;
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelOrder(Long id) {
        Order order = getById(id);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        
        // 只有待付款的订单可以取消
        if (order.getStatus() != 0) {
            throw new BusinessException("订单状态不允许取消");
        }
        
        int rows = baseMapper.cancelOrder(id);
        
        if (rows > 0) {
            // 恢复商品库存
            productMapper.decreaseStock(order.getProductId(), -order.getQuantity());
        }
        
        return rows > 0;
    }
    
    @Override
    public boolean canComment(Long id) {
        Order order = getById(id);
        if (order == null) {
            return false;
        }
        
        // 只有已完成的订单可以评价
        return order.getStatus() == 3;
    }
    
    /**
     * 生成订单编号
     */
    private String generateOrderNo() {
        // 生成格式：yyyyMMdd + 4位随机数
        String date = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String random = String.format("%04d", (int) (Math.random() * 10000));
        return date + random;
    }
    
    /**
     * 将订单实体转换为VO
     */
    private OrderVO convertToVO(Order order) {
        OrderVO vo = new OrderVO();
        BeanUtils.copyProperties(order, vo);
        
        // 设置订单状态描述
        vo.setStatusDesc(getStatusDesc(order.getStatus()));
        
        // 获取用户信息
        User user = userMapper.selectById(order.getUserId());
        if (user != null) {
            vo.setUserName(user.getUsername());
        }
        
        // 设置是否可评价
        vo.setCanComment(canComment(order.getId()));
        
        return vo;
    }
    
    /**
     * 获取订单状态描述
     */
    private String getStatusDesc(Integer status) {
        switch (status) {
            case 0:
                return "待付款";
            case 1:
                return "待发货";
            case 2:
                return "待收货";
            case 3:
                return "已完成";
            case 4:
                return "已取消";
            case 5:
                return "已退款";
            default:
                return "未知状态";
        }
    }
} 