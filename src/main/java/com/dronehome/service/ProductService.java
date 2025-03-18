package com.dronehome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.dto.ProductCreateDTO;
import com.dronehome.dto.ProductQueryDTO;
import com.dronehome.entity.Product;
import com.dronehome.vo.ProductVO;

public interface ProductService extends IService<Product> {
    
    /**
     * 创建商品
     *
     * @param createDTO 创建DTO
     * @param userId 创建者ID
     * @return 商品VO
     */
    ProductVO createProduct(ProductCreateDTO createDTO, Long userId);
    
    /**
     * 获取商品详情
     *
     * @param id 商品ID
     * @return 商品详情
     */
    ProductVO getProductDetail(Long id);
    
    /**
     * 获取商品列表
     *
     * @param queryDTO 查询DTO
     * @return 商品VO分页列表
     */
    IPage<ProductVO> getProductList(ProductQueryDTO queryDTO);
    
    /**
     * 更新商品状态
     *
     * @param id 商品ID
     * @param status 状态
     * @return 商品VO
     */
    ProductVO updateProductStatus(Long id, Integer status);
    
    /**
     * 更新商品库存
     *
     * @param id 商品ID
     * @param stock 库存数量
     * @return 商品VO
     */
    ProductVO updateProductStock(Long id, Integer stock);
    
    /**
     * 删除商品
     *
     * @param id 商品ID
     */
    void deleteProduct(Long id);
    
    /**
     * 分页查询商品
     *
     * @param categoryId 分类ID
     * @param brandId 品牌ID
     * @param keyword 关键词
     * @param minPrice 最低价格
     * @param maxPrice 最高价格
     * @param pageNum 页码
     * @param pageSize 每页数量
     * @param sortField 排序字段
     * @param sortOrder 排序方式
     * @return 商品分页结果
     */
    Page<ProductVO> pageProducts(Long categoryId, Long brandId, String keyword, 
                                 Integer minPrice, Integer maxPrice, 
                                 Integer pageNum, Integer pageSize, 
                                 String sortField, String sortOrder);
    
    /**
     * 更新商品评分和评论数量
     *
     * @param productId 商品ID
     * @param rating 评分
     * @param commentCount 评论数量
     * @return 是否成功
     */
    boolean updateRatingAndCommentCount(Long productId, Double rating, Integer commentCount);
} 