package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
@ApiModel("收藏添加DTO")
public class FavoriteAddDTO {
    
    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    private Long productId;
} 