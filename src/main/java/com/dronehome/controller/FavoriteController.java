package com.dronehome.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dronehome.common.Result;
import com.dronehome.dto.FavoriteAddDTO;
import com.dronehome.dto.FavoriteQueryDTO;
import com.dronehome.service.FavoriteService;
import com.dronehome.vo.FavoriteVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "收藏管理")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {
    
    @Autowired
    private FavoriteService favoriteService;
    
    @ApiOperation("添加收藏")
    @PostMapping
    public Result<FavoriteVO> addFavorite(@Validated @RequestBody FavoriteAddDTO favoriteAddDTO) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(favoriteService.addFavorite(favoriteAddDTO, userId));
    }
    
    @ApiOperation("取消收藏")
    @DeleteMapping("/{id}")
    public Result<Void> cancelFavorite(@PathVariable Long id) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        favoriteService.cancelFavorite(id, userId);
        return Result.success();
    }
    
    @ApiOperation("根据商品ID取消收藏")
    @DeleteMapping("/product/{productId}")
    public Result<Void> cancelFavoriteByProductId(@PathVariable Long productId) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        favoriteService.cancelFavoriteByProductId(productId, userId);
        return Result.success();
    }
    
    @ApiOperation("获取收藏列表")
    @GetMapping
    public Result<IPage<FavoriteVO>> getFavoriteList(FavoriteQueryDTO queryDTO) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(favoriteService.getFavoriteList(queryDTO, userId));
    }
    
    @ApiOperation("检查商品是否已收藏")
    @GetMapping("/check/{productId}")
    public Result<Boolean> isFavorite(@PathVariable Long productId) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(favoriteService.isFavorite(productId, userId));
    }
    
    @ApiOperation("获取用户收藏数量")
    @GetMapping("/count")
    public Result<Integer> getUserFavoriteCount() {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(favoriteService.getUserFavoriteCount(userId));
    }
} 