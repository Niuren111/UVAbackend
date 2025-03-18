package com.dronehome.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.common.Result;
import com.dronehome.dto.ProductCreateDTO;
import com.dronehome.dto.ProductQueryDTO;
import com.dronehome.service.ProductService;
import com.dronehome.vo.ProductVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "商品管理接口")
@RestController
@RequestMapping("/api/products")
public class ProductController {
    
    @Autowired
    private ProductService productService;
    
    @ApiOperation("创建商品")
    @PostMapping
    public Result<ProductVO> createProduct(@Validated @RequestBody ProductCreateDTO createDTO) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(productService.createProduct(createDTO, userId));
    }
    
    @GetMapping("/{id}")
    @ApiOperation("获取商品详情")
    public Result<ProductVO> getProductDetail(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long id) {
        ProductVO productVO = productService.getProductDetail(id);
        return Result.success(productVO);
    }
    
    @GetMapping("/page")
    @ApiOperation("分页查询商品")
    public Result<Page<ProductVO>> pageProducts(
            @ApiParam(value = "分类ID") @RequestParam(required = false) Long categoryId,
            @ApiParam(value = "品牌ID") @RequestParam(required = false) Long brandId,
            @ApiParam(value = "关键词") @RequestParam(required = false) String keyword,
            @ApiParam(value = "最低价格") @RequestParam(required = false) Integer minPrice,
            @ApiParam(value = "最高价格") @RequestParam(required = false) Integer maxPrice,
            @ApiParam(value = "页码", defaultValue = "1") @RequestParam(defaultValue = "1") Integer pageNum,
            @ApiParam(value = "每页数量", defaultValue = "10") @RequestParam(defaultValue = "10") Integer pageSize,
            @ApiParam(value = "排序字段", defaultValue = "createTime") @RequestParam(defaultValue = "createTime") String sortField,
            @ApiParam(value = "排序方式", defaultValue = "desc") @RequestParam(defaultValue = "desc") String sortOrder) {
        
        Page<ProductVO> page = productService.pageProducts(categoryId, brandId, keyword, 
                minPrice, maxPrice, pageNum, pageSize, sortField, sortOrder);
        return Result.success(page);
    }
    
    @ApiOperation("获取商品列表")
    @GetMapping
    public Result<IPage<ProductVO>> getProductList(ProductQueryDTO queryDTO) {
        return Result.success(productService.getProductList(queryDTO));
    }
    
    @ApiOperation("更新商品状态")
    @PutMapping("/{id}/status")
    public Result<ProductVO> updateProductStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        return Result.success(productService.updateProductStatus(id, status));
    }
    
    @ApiOperation("更新商品库存")
    @PutMapping("/{id}/stock")
    public Result<ProductVO> updateProductStock(
            @PathVariable Long id,
            @RequestParam Integer stock) {
        return Result.success(productService.updateProductStock(id, stock));
    }
    
    @ApiOperation("删除商品")
    @DeleteMapping("/{id}")
    public Result<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return Result.success();
    }
} 