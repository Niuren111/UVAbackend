package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("收藏查询DTO")
public class FavoriteQueryDTO {
    
    @ApiModelProperty(value = "商品名称", example = "无人机")
    private String productName;
    
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
} 