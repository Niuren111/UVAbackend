package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@ApiModel("订单创建DTO")
public class OrderCreateDTO {
    
    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    private Long productId;
    
    @NotNull(message = "购买数量不能为空")
    @Min(value = 1, message = "购买数量最小为1")
    @ApiModelProperty(value = "购买数量", required = true, example = "1")
    private Integer quantity;
} 