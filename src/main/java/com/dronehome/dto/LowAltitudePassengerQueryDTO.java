package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("低空旅客查询DTO")
public class LowAltitudePassengerQueryDTO {
    
    @ApiModelProperty(value = "关键词（标题、描述）", example = "深圳湾")
    private String keyword;
    
    @ApiModelProperty(value = "出发地点", example = "深圳湾")
    private String departureLocation;
    
    @ApiModelProperty(value = "到达地点", example = "深圳湾")
    private String arrivalLocation;
    
    @ApiModelProperty(value = "最早出发时间", example = "2024-04-01 00:00:00")
    private LocalDateTime startDepartureTime;
    
    @ApiModelProperty(value = "最晚出发时间", example = "2024-04-30 23:59:59")
    private LocalDateTime endDepartureTime;
    
    @ApiModelProperty(value = "最低价格", example = "100")
    private BigDecimal minPrice;
    
    @ApiModelProperty(value = "最高价格", example = "1000")
    private BigDecimal maxPrice;
    
    @ApiModelProperty(value = "状态：0-待上架，1-已上架，2-已下架", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "创建者ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "当前页码", required = true, example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页记录数", required = true, example = "10")
    private Integer pageSize = 10;
    
    @ApiModelProperty(value = "排序字段", example = "price")
    private String sortField;
    
    @ApiModelProperty(value = "排序方式：asc-升序，desc-降序", example = "asc")
    private String sortOrder;
} 