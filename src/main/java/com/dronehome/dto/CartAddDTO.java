package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@ApiModel("购物车添加DTO")
public class CartAddDTO {
    
    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    private Long productId;
    
    @NotNull(message = "商品数量不能为空")
    @Positive(message = "商品数量必须大于0")
    @ApiModelProperty(value = "商品数量", required = true, example = "1")
    private Integer quantity;
    
    @ApiModelProperty(value = "是否选中", example = "true")
    private Boolean selected = true;
} 