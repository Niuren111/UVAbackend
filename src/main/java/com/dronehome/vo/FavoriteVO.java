package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("收藏VO")
public class FavoriteVO {
    
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
    
    @ApiModelProperty(value = "商品描述", example = "大疆 Mavic 3 Pro 无人机，专业航拍利器")
    private String productDescription;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-20 10:00:00")
    private LocalDateTime createTime;
} 