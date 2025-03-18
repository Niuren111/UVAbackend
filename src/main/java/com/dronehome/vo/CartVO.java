package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("购物车VO")
public class CartVO {
    
    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Long productId;
    
    @ApiModelProperty(value = "商品名称", example = "大疆 Mavic 3 Pro 无人机")
    private String productName;
    
    @ApiModelProperty(value = "商品图片", example = "https://example.com/image1.jpg")
    private String productImage;
    
    @ApiModelProperty(value = "商品价格", example = "15999.00")
    private BigDecimal productPrice;
    
    @ApiModelProperty(value = "商品数量", example = "1")
    private Integer quantity;
    
    @ApiModelProperty(value = "是否选中", example = "true")
    private Boolean selected;
    
    @ApiModelProperty(value = "商品总价", example = "15999.00")
    private BigDecimal totalPrice;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-20 10:00:00")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间", example = "2024-03-20 10:00:00")
    private LocalDateTime updateTime;
} 