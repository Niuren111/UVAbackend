package com.dronehome.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("product")
public class Product {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String description;
    
    private Long categoryId;
    
    private String categoryName;
    
    private Long brandId;
    
    private String brandName;
    
    private BigDecimal price;
    
    private BigDecimal originalPrice;
    
    private String mainImage;
    
    private String images;
    
    private String detail;
    
    private String specs;
    
    private Integer stock;
    
    private Integer sales;
    
    private Double rating;
    
    private Integer commentCount;
    
    private Integer status;
    
    private Long sellerId;
    
    private String sellerName;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Boolean isDeleted;
} 