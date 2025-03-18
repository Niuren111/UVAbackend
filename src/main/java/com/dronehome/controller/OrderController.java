package com.dronehome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.common.Result;
import com.dronehome.dto.OrderCreateDTO;
import com.dronehome.dto.OrderQueryDTO;
import com.dronehome.service.OrderService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.OrderVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/orders")
@Api(tags = "订单管理接口")
public class OrderController {

    @Autowired
    private OrderService orderService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ApiOperation("创建订单")
    public Result<OrderVO> createOrder(@RequestBody @Valid OrderCreateDTO orderCreateDTO, HttpServletRequest request) {
        // 从JWT中获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(request);
        
        OrderVO orderVO = orderService.createOrder(orderCreateDTO, userId);
        return Result.success(orderVO, "订单创建成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取订单详情")
    public Result<OrderVO> getOrderDetail(
            @ApiParam(value = "订单ID", required = true) @PathVariable Long id) {
        OrderVO orderVO = orderService.getOrderDetail(id);
        return Result.success(orderVO);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询订单")
    public Result<Page<OrderVO>> pageOrders(@Valid OrderQueryDTO queryDTO, HttpServletRequest request) {
        // 从JWT中获取用户ID，普通用户只能查询自己的订单
        Long userId = jwtUtil.getUserIdFromToken(request);
        String role = jwtUtil.getRoleFromToken(request);
        
        // 如果不是管理员，强制设置为只查询自己的订单
        if (!"ROLE_ADMIN".equals(role)) {
            queryDTO.setUserId(userId);
        }
        
        Page<OrderVO> page = orderService.pageOrders(queryDTO);
        return Result.success(page);
    }

    @PostMapping("/{id}/pay")
    @ApiOperation("支付订单")
    public Result<Boolean> payOrder(
            @ApiParam(value = "订单ID", required = true) @PathVariable Long id) {
        boolean result = orderService.payOrder(id);
        return Result.success(result, "支付成功");
    }

    @PostMapping("/{id}/deliver")
    @ApiOperation("发货")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Boolean> deliverOrder(
            @ApiParam(value = "订单ID", required = true) @PathVariable Long id) {
        boolean result = orderService.deliverOrder(id);
        return Result.success(result, "发货成功");
    }

    @PostMapping("/{id}/confirm")
    @ApiOperation("确认收货")
    public Result<Boolean> confirmOrder(
            @ApiParam(value = "订单ID", required = true) @PathVariable Long id,
            HttpServletRequest request) {
        // 从JWT中获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(request);
        
        // 验证订单所有权
        OrderVO orderVO = orderService.getOrderDetail(id);
        if (!orderVO.getUserId().equals(userId)) {
            return Result.fail("无权操作此订单");
        }
        
        boolean result = orderService.confirmOrder(id);
        return Result.success(result, "确认收货成功");
    }

    @PostMapping("/{id}/cancel")
    @ApiOperation("取消订单")
    public Result<Boolean> cancelOrder(
            @ApiParam(value = "订单ID", required = true) @PathVariable Long id,
            HttpServletRequest request) {
        // 从JWT中获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(request);
        
        // 验证订单所有权
        OrderVO orderVO = orderService.getOrderDetail(id);
        if (!orderVO.getUserId().equals(userId)) {
            return Result.fail("无权操作此订单");
        }
        
        boolean result = orderService.cancelOrder(id);
        return Result.success(result, "取消订单成功");
    }

    @GetMapping("/{id}/can-comment")
    @ApiOperation("检查订单是否可评价")
    public Result<Boolean> canComment(
            @ApiParam(value = "订单ID", required = true) @PathVariable Long id) {
        boolean result = orderService.canComment(id);
        return Result.success(result);
    }
} 