package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.ProductCreateDTO;
import com.dronehome.dto.ProductQueryDTO;
import com.dronehome.entity.Product;
import com.dronehome.entity.User;
import com.dronehome.mapper.ProductMapper;
import com.dronehome.service.ProductService;
import com.dronehome.service.UserService;
import com.dronehome.vo.ProductVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements ProductService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public ProductVO createProduct(ProductCreateDTO createDTO, Long userId) {
        Product product = new Product();
        BeanUtils.copyProperties(createDTO, product);
        product.setUserId(userId);
        product.setStatus(0); // 设置初始状态为待上架
        
        this.save(product);
        
        return convertToVO(product);
    }
    
    @Override
    public ProductVO getProductById(Long id) {
        Product product = this.getById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        return convertToVO(product);
    }
    
    @Override
    public IPage<ProductVO> getProductList(ProductQueryDTO queryDTO) {
        Page<Product> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        LambdaQueryWrapper<Product> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(queryDTO.getName())) {
            queryWrapper.like(Product::getName, queryDTO.getName());
        }
        if (StringUtils.hasText(queryDTO.getCategory())) {
            queryWrapper.eq(Product::getCategory, queryDTO.getCategory());
        }
        if (StringUtils.hasText(queryDTO.getBrand())) {
            queryWrapper.eq(Product::getBrand, queryDTO.getBrand());
        }
        if (queryDTO.getMinPrice() != null) {
            queryWrapper.ge(Product::getPrice, queryDTO.getMinPrice());
        }
        if (queryDTO.getMaxPrice() != null) {
            queryWrapper.le(Product::getPrice, queryDTO.getMaxPrice());
        }
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(Product::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getUserId() != null) {
            queryWrapper.eq(Product::getUserId, queryDTO.getUserId());
        }
        queryWrapper.orderByDesc(Product::getCreateTime);
        
        IPage<Product> productPage = this.page(page, queryWrapper);
        
        return productPage.convert(this::convertToVO);
    }
    
    @Override
    public ProductVO updateProductStatus(Long id, Integer status) {
        Product product = this.getById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        product.setStatus(status);
        this.updateById(product);
        
        return convertToVO(product);
    }
    
    @Override
    public ProductVO updateProductStock(Long id, Integer stock) {
        Product product = this.getById(id);
        if (product == null) {
            throw new RuntimeException("商品不存在");
        }
        
        product.setStock(stock);
        this.updateById(product);
        
        return convertToVO(product);
    }
    
    @Override
    public void deleteProduct(Long id) {
        this.removeById(id);
    }
    
    private ProductVO convertToVO(Product product) {
        ProductVO productVO = new ProductVO();
        BeanUtils.copyProperties(product, productVO);
        
        // 获取创建者信息
        User user = userService.getById(product.getUserId());
        if (user != null) {
            productVO.setUserName(user.getUsername());
        }
        
        return productVO;
    }
} 