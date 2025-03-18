package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel("商品查询DTO")
public class ProductQueryDTO {
    
    @ApiModelProperty(value = "商品名称", example = "无人机")
    private String name;
    
    @ApiModelProperty(value = "商品分类", example = "无人机")
    private String category;
    
    @ApiModelProperty(value = "品牌", example = "大疆")
    private String brand;
    
    @ApiModelProperty(value = "最低价格", example = "1000")
    private BigDecimal minPrice;
    
    @ApiModelProperty(value = "最高价格", example = "20000")
    private BigDecimal maxPrice;
    
    @ApiModelProperty(value = "状态：0-待上架，1-已上架，2-已下架", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "创建者ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
} 