package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("订单查询DTO")
public class OrderQueryDTO {
    
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Long productId;
    
    @ApiModelProperty(value = "订单状态：0-待付款，1-待发货，2-待收货，3-已完成，4-已取消，5-已退款", example = "0")
    private Integer status;
    
    @ApiModelProperty(value = "订单编号", example = "202403210001")
    private String orderNo;
    
    @ApiModelProperty(value = "开始时间", example = "2024-03-01 00:00:00")
    private LocalDateTime startTime;
    
    @ApiModelProperty(value = "结束时间", example = "2024-03-31 23:59:59")
    private LocalDateTime endTime;
    
    @ApiModelProperty(value = "当前页码", required = true, example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页记录数", required = true, example = "10")
    private Integer pageSize = 10;
} 