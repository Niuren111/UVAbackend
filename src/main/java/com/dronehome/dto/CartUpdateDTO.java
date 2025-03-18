package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
@ApiModel("购物车更新DTO")
public class CartUpdateDTO {
    
    @NotNull(message = "购物车ID不能为空")
    @ApiModelProperty(value = "购物车ID", required = true, example = "1")
    private Long id;
    
    @Positive(message = "商品数量必须大于0")
    @ApiModelProperty(value = "商品数量", example = "2")
    private Integer quantity;
    
    @ApiModelProperty(value = "是否选中", example = "true")
    private Boolean selected;
} 