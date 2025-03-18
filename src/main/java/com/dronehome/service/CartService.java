package com.dronehome.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dronehome.dto.CartAddDTO;
import com.dronehome.dto.CartUpdateDTO;
import com.dronehome.entity.Cart;
import com.dronehome.vo.CartVO;

import java.util.List;

public interface CartService extends IService<Cart> {
    
    /**
     * 添加商品到购物车
     *
     * @param cartAddDTO 购物车添加DTO
     * @param userId 用户ID
     * @return 购物车VO
     */
    CartVO addToCart(CartAddDTO cartAddDTO, Long userId);
    
    /**
     * 更新购物车
     *
     * @param cartUpdateDTO 购物车更新DTO
     * @param userId 用户ID
     * @return 购物车VO
     */
    CartVO updateCart(CartUpdateDTO cartUpdateDTO, Long userId);
    
    /**
     * 删除购物车商品
     *
     * @param id 购物车ID
     * @param userId 用户ID
     */
    void deleteCart(Long id, Long userId);
    
    /**
     * 清空购物车
     *
     * @param userId 用户ID
     */
    void clearCart(Long userId);
    
    /**
     * 获取用户购物车列表
     *
     * @param userId 用户ID
     * @return 购物车VO列表
     */
    List<CartVO> getUserCartList(Long userId);
    
    /**
     * 全选/取消全选
     *
     * @param selected 是否选中
     * @param userId 用户ID
     * @return 购物车VO列表
     */
    List<CartVO> selectAllCart(Boolean selected, Long userId);
    
    /**
     * 获取用户购物车商品数量
     *
     * @param userId 用户ID
     * @return 商品数量
     */
    Integer getUserCartProductCount(Long userId);
} 