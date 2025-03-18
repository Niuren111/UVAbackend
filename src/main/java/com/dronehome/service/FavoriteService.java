package com.dronehome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dronehome.dto.FavoriteAddDTO;
import com.dronehome.dto.FavoriteQueryDTO;
import com.dronehome.entity.Favorite;
import com.dronehome.vo.FavoriteVO;

public interface FavoriteService extends IService<Favorite> {
    
    /**
     * 添加收藏
     *
     * @param favoriteAddDTO 收藏添加DTO
     * @param userId 用户ID
     * @return 收藏VO
     */
    FavoriteVO addFavorite(FavoriteAddDTO favoriteAddDTO, Long userId);
    
    /**
     * 取消收藏
     *
     * @param id 收藏ID
     * @param userId 用户ID
     */
    void cancelFavorite(Long id, Long userId);
    
    /**
     * 根据商品ID取消收藏
     *
     * @param productId 商品ID
     * @param userId 用户ID
     */
    void cancelFavoriteByProductId(Long productId, Long userId);
    
    /**
     * 获取收藏列表
     *
     * @param queryDTO 查询DTO
     * @param userId 用户ID
     * @return 收藏VO分页列表
     */
    IPage<FavoriteVO> getFavoriteList(FavoriteQueryDTO queryDTO, Long userId);
    
    /**
     * 检查商品是否已收藏
     *
     * @param productId 商品ID
     * @param userId 用户ID
     * @return 是否已收藏
     */
    boolean isFavorite(Long productId, Long userId);
    
    /**
     * 获取用户收藏数量
     *
     * @param userId 用户ID
     * @return 收藏数量
     */
    Integer getUserFavoriteCount(Long userId);
} 