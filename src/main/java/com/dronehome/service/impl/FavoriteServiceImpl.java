package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.FavoriteAddDTO;
import com.dronehome.dto.FavoriteQueryDTO;
import com.dronehome.entity.Favorite;
import com.dronehome.entity.Product;
import com.dronehome.mapper.FavoriteMapper;
import com.dronehome.service.FavoriteService;
import com.dronehome.service.ProductService;
import com.dronehome.vo.FavoriteVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {
    
    @Autowired
    private ProductService productService;
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public FavoriteVO addFavorite(FavoriteAddDTO favoriteAddDTO, Long userId) {
        // 检查商品是否存在
        Product product = productService.getById(favoriteAddDTO.getProductId());
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        // 检查是否已收藏
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, favoriteAddDTO.getProductId());
        
        Favorite favorite = this.getOne(queryWrapper);
        
        if (favorite != null) {
            throw new RuntimeException("已收藏该商品");
        }
        
        // 添加收藏
        favorite = new Favorite();
        favorite.setUserId(userId);
        favorite.setProductId(favoriteAddDTO.getProductId());
        
        this.save(favorite);
        
        return convertToVO(favorite, product);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelFavorite(Long id, Long userId) {
        // 检查收藏是否存在
        Favorite favorite = this.getById(id);
        if (favorite == null) {
            throw new RuntimeException("收藏不存在");
        }
        
        // 检查是否是当前用户的收藏
        if (!favorite.getUserId().equals(userId)) {
            throw new RuntimeException("无权操作他人收藏");
        }
        
        this.removeById(id);
    }
    
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void cancelFavoriteByProductId(Long productId, Long userId) {
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, productId);
        
        this.remove(queryWrapper);
    }
    
    @Override
    public IPage<FavoriteVO> getFavoriteList(FavoriteQueryDTO queryDTO, Long userId) {
        Page<Favorite> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId);
        
        // 如果有商品名称查询条件，需要先查询符合条件的商品ID
        if (StringUtils.hasText(queryDTO.getProductName())) {
            List<Long> productIds = getProductIdsByName(queryDTO.getProductName());
            if (productIds.isEmpty()) {
                // 如果没有符合条件的商品，直接返回空结果
                return new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
            }
            queryWrapper.in(Favorite::getProductId, productIds);
        }
        
        queryWrapper.orderByDesc(Favorite::getCreateTime);
        
        IPage<Favorite> favoritePage = this.page(page, queryWrapper);
        
        return convertToVOPage(favoritePage);
    }
    
    @Override
    public boolean isFavorite(Long productId, Long userId) {
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId)
                .eq(Favorite::getProductId, productId);
        
        return this.count(queryWrapper) > 0;
    }
    
    @Override
    public Integer getUserFavoriteCount(Long userId) {
        LambdaQueryWrapper<Favorite> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Favorite::getUserId, userId);
        
        return Math.toIntExact(this.count(queryWrapper));
    }
    
    private List<Long> getProductIdsByName(String productName) {
        List<Product> products = productService.list(new LambdaQueryWrapper<Product>()
                .like(Product::getName, productName));
        
        List<Long> productIds = new ArrayList<>();
        for (Product product : products) {
            productIds.add(product.getId());
        }
        
        return productIds;
    }
    
    private FavoriteVO convertToVO(Favorite favorite, Product product) {
        FavoriteVO favoriteVO = new FavoriteVO();
        BeanUtils.copyProperties(favorite, favoriteVO);
        
        favoriteVO.setProductName(product.getName());
        favoriteVO.setProductImage(product.getImages() != null ? product.getImages().split(",")[0] : null);
        favoriteVO.setProductPrice(product.getPrice());
        favoriteVO.setProductDescription(product.getDescription());
        
        return favoriteVO;
    }
    
    private IPage<FavoriteVO> convertToVOPage(IPage<Favorite> favoritePage) {
        IPage<FavoriteVO> favoriteVOPage = new Page<>(favoritePage.getCurrent(), favoritePage.getSize(), favoritePage.getTotal());
        
        List<FavoriteVO> favoriteVOList = new ArrayList<>();
        for (Favorite favorite : favoritePage.getRecords()) {
            Product product = productService.getById(favorite.getProductId());
            if (product != null) {
                favoriteVOList.add(convertToVO(favorite, product));
            }
        }
        
        favoriteVOPage.setRecords(favoriteVOList);
        
        return favoriteVOPage;
    }
} 