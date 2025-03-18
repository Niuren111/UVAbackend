package com.dronehome.controller;

import com.dronehome.common.Result;
import com.dronehome.dto.CartAddDTO;
import com.dronehome.dto.CartUpdateDTO;
import com.dronehome.service.CartService;
import com.dronehome.vo.CartVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "购物车管理")
@RestController
@RequestMapping("/api/carts")
public class CartController {
    
    @Autowired
    private CartService cartService;
    
    @ApiOperation("添加商品到购物车")
    @PostMapping
    public Result<CartVO> addToCart(@Validated @RequestBody CartAddDTO cartAddDTO) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(cartService.addToCart(cartAddDTO, userId));
    }
    
    @ApiOperation("更新购物车")
    @PutMapping
    public Result<CartVO> updateCart(@Validated @RequestBody CartUpdateDTO cartUpdateDTO) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(cartService.updateCart(cartUpdateDTO, userId));
    }
    
    @ApiOperation("删除购物车商品")
    @DeleteMapping("/{id}")
    public Result<Void> deleteCart(@PathVariable Long id) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        cartService.deleteCart(id, userId);
        return Result.success();
    }
    
    @ApiOperation("清空购物车")
    @DeleteMapping
    public Result<Void> clearCart() {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        cartService.clearCart(userId);
        return Result.success();
    }
    
    @ApiOperation("获取购物车列表")
    @GetMapping
    public Result<List<CartVO>> getUserCartList() {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(cartService.getUserCartList(userId));
    }
    
    @ApiOperation("全选/取消全选")
    @PutMapping("/select-all")
    public Result<List<CartVO>> selectAllCart(@RequestParam Boolean selected) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(cartService.selectAllCart(selected, userId));
    }
    
    @ApiOperation("获取购物车商品数量")
    @GetMapping("/count")
    public Result<Integer> getUserCartProductCount() {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(cartService.getUserCartProductCount(userId));
    }
} 