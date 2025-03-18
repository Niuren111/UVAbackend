package com.dronehome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.dto.OrderCreateDTO;
import com.dronehome.dto.OrderQueryDTO;
import com.dronehome.vo.OrderVO;

public interface OrderService {
    
    /**
     * 创建订单
     *
     * @param orderCreateDTO 订单创建DTO
     * @param userId 用户ID
     * @return 订单VO
     */
    OrderVO createOrder(OrderCreateDTO orderCreateDTO, Long userId);
    
    /**
     * 获取订单详情
     *
     * @param id 订单ID
     * @return 订单VO
     */
    OrderVO getOrderDetail(Long id);
    
    /**
     * 分页查询订单
     *
     * @param queryDTO 查询条件
     * @return 订单分页结果
     */
    Page<OrderVO> pageOrders(OrderQueryDTO queryDTO);
    
    /**
     * 支付订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean payOrder(Long id);
    
    /**
     * 发货
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean deliverOrder(Long id);
    
    /**
     * 确认收货
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean confirmOrder(Long id);
    
    /**
     * 取消订单
     *
     * @param id 订单ID
     * @return 是否成功
     */
    boolean cancelOrder(Long id);
    
    /**
     * 检查订单是否可评价
     *
     * @param id 订单ID
     * @return 是否可评价
     */
    boolean canComment(Long id);
} 