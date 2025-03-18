package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.dronehome.entity.Comment;
import com.dronehome.entity.LowAltitudePassenger;
import com.dronehome.entity.Order;
import com.dronehome.entity.Product;
import com.dronehome.mapper.CommentMapper;
import com.dronehome.mapper.LowAltitudePassengerMapper;
import com.dronehome.mapper.OrderMapper;
import com.dronehome.mapper.ProductMapper;
import com.dronehome.service.SecurityService;
import com.dronehome.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private HttpServletRequest request;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private ProductMapper productMapper;
    
    @Autowired
    private OrderMapper orderMapper;
    
    @Autowired
    private LowAltitudePassengerMapper lowAltitudePassengerMapper;

    @Override
    public boolean isCommentOwner(Long commentId) {
        // 获取当前用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(request);
        if (currentUserId == null) {
            return false;
        }
        
        // 查询评论
        Comment comment = commentMapper.selectById(commentId);
        if (comment == null) {
            return false;
        }
        
        // 检查是否为评论所有者
        return comment.getUserId().equals(currentUserId);
    }

    @Override
    public boolean isProductOwner(Long productId) {
        // 获取当前用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(request);
        if (currentUserId == null) {
            return false;
        }
        
        // 查询商品
        Product product = productMapper.selectById(productId);
        if (product == null) {
            return false;
        }
        
        // 检查是否为商品所有者
        return product.getSellerId().equals(currentUserId);
    }

    @Override
    public boolean isOrderOwner(Long orderId) {
        // 获取当前用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(request);
        if (currentUserId == null) {
            return false;
        }
        
        // 查询订单
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            return false;
        }
        
        // 检查是否为订单所有者
        return order.getUserId().equals(currentUserId);
    }

    @Override
    public boolean isLowAltitudePassengerOwner(Long lowAltitudePassengerId) {
        // 获取当前用户ID
        Long currentUserId = jwtUtil.getUserIdFromToken(request);
        if (currentUserId == null) {
            return false;
        }
        
        // 查询低空旅客服务
        LowAltitudePassenger passenger = lowAltitudePassengerMapper.selectById(lowAltitudePassengerId);
        if (passenger == null) {
            return false;
        }
        
        // 检查是否为低空旅客服务所有者
        return passenger.getUserId().equals(currentUserId);
    }
} 