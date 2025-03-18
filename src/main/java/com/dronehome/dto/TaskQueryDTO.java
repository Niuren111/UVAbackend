package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "任务查询请求")
public class TaskQueryDTO {
    
    @ApiModelProperty(value = "任务标题", example = "航拍")
    private String title;
    
    @ApiModelProperty(value = "任务地点", example = "北京")
    private String location;
    
    @ApiModelProperty(value = "任务状态：0-待接单，1-进行中，2-已完成，3-已取消", example = "0")
    private Integer status;
    
    @ApiModelProperty(value = "发布者ID", example = "1")
    private Long publisherId;
    
    @ApiModelProperty(value = "执行者ID", example = "2")
    private Long executorId;
    
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
} 