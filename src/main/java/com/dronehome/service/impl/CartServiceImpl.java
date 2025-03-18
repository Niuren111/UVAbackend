package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.CartAddDTO;
import com.dronehome.dto.CartUpdateDTO;
import com.dronehome.entity.Cart;
import com.dronehome.entity.Product;
import com.dronehome.mapper.CartMapper;
import com.dronehome.service.CartService;
import com.dronehome.service.ProductService;
import com.dronehome.vo.CartVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements CartService {
    
    @Autowired
    private ProductService productService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartVO addToCart(CartAddDTO cartAddDTO, Long userId) {
        // 检查商品是否存在
        Product product = productService.getById(cartAddDTO.getProductId());
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 检查商品库存
        if (product.getStock() < cartAddDTO.getQuantity()) {
            throw new RuntimeException("商品库存不足");
        }
        
        // 检查购物车中是否已存在该商品
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .eq(Cart::getProductId, cartAddDTO.getProductId());
        
        Cart cart = this.getOne(queryWrapper);
        
        if (cart != null) {
            // 更新购物车商品数量
            cart.setQuantity(cart.getQuantity() + cartAddDTO.getQuantity());
            cart.setSelected(cartAddDTO.getSelected());
            this.updateById(cart);
        } else {
            // 添加新商品到购物车
            cart = new Cart();
            cart.setUserId(userId);
            cart.setProductId(cartAddDTO.getProductId());
            cart.setQuantity(cartAddDTO.getQuantity());
            cart.setSelected(cartAddDTO.getSelected());
            
            this.save(cart);
        }
        
        return convertToVO(cart, product);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public CartVO updateCart(CartUpdateDTO cartUpdateDTO, Long userId) {
        // 检查购物车商品是否存在
        Cart cart = this.getById(cartUpdateDTO.getId());
        if (cart == null) {
            throw new RuntimeException("购物车商品不存在");
        }
        
        // 检查是否是当前用户的购物车
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作他人购物车");
        }
        
        // 检查商品是否存在
        Product product = productService.getById(cart.getProductId());
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 更新购物车
        if (cartUpdateDTO.getQuantity() != null) {
            // 检查商品库存
            if (product.getStock() < cartUpdateDTO.getQuantity()) {
                throw new RuntimeException("商品库存不足");
            }
            cart.setQuantity(cartUpdateDTO.getQuantity());
        }
        
        if (cartUpdateDTO.getSelected() != null) {
            cart.setSelected(cartUpdateDTO.getSelected());
        }
        
        this.updateById(cart);
        
        return convertToVO(cart, product);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteCart(Long id, Long userId) {
        // 检查购物车商品是否存在
        Cart cart = this.getById(id);
        if (cart == null) {
            throw new RuntimeException("购物车商品不存在");
        }
        
        // 检查是否是当前用户的购物车
        if (!cart.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作他人购物车");
        }
        
        this.removeById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void clearCart(Long userId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        
        this.remove(queryWrapper);
    }
    
    @Override
    public List<CartVO> getUserCartList(Long userId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId)
                .orderByDesc(Cart::getUpdateTime);
        
        List<Cart> cartList = this.list(queryWrapper);
        
        return convertToVOList(cartList);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<CartVO> selectAllCart(Boolean selected, Long userId) {
        LambdaUpdateWrapper<Cart> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Cart::getUserId, userId)
                .set(Cart::getSelected, selected);
        
        this.update(updateWrapper);
        
        return getUserCartList(userId);
    }
    
    @Override
    public Integer getUserCartProductCount(Long userId) {
        LambdaQueryWrapper<Cart> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Cart::getUserId, userId);
        
        List<Cart> cartList = this.list(queryWrapper);
        
        int count = 0;
        for (Cart cart : cartList) {
            count += cart.getQuantity();
        }
        
        return count;
    }
    
    private CartVO convertToVO(Cart cart, Product product) {
        CartVO cartVO = new CartVO();
        BeanUtils.copyProperties(cart, cartVO);
        
        cartVO.setProductName(product.getName());
        cartVO.setProductImage(product.getImages() != null ? product.getImages().split(",")[0] : null);
        cartVO.setProductPrice(product.getPrice());
        cartVO.setTotalPrice(product.getPrice().multiply(new BigDecimal(cart.getQuantity())));
        
        return cartVO;
    }
    
    private List<CartVO> convertToVOList(List<Cart> cartList) {
        List<CartVO> cartVOList = new ArrayList<>();
        
        for (Cart cart : cartList) {
            Product product = productService.getById(cart.getProductId());
            if (product != null) {
                cartVOList.add(convertToVO(cart, product));
            }
        }
        
        return cartVOList;
    }
} 