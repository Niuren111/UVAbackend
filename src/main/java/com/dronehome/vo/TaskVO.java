package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel(description = "任务信息")
public class TaskVO {
    
    @ApiModelProperty(value = "任务ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "任务标题", example = "无人机航拍任务")
    private String title;
    
    @ApiModelProperty(value = "任务描述", example = "需要无人机进行航拍...")
    private String description;
    
    @ApiModelProperty(value = "任务要求", example = "1. 需要持有无人机驾驶证...")
    private String requirement;
    
    @ApiModelProperty(value = "任务预算", example = "1000.00")
    private BigDecimal budget;
    
    @ApiModelProperty(value = "任务地点", example = "北京市朝阳区")
    private String location;
    
    @ApiModelProperty(value = "联系方式", example = "13800138000")
    private String contact;
    
    @ApiModelProperty(value = "任务状态：0-待接单，1-进行中，2-已完成，3-已取消", example = "0")
    private Integer status;
    
    @ApiModelProperty(value = "发布者ID", example = "1")
    private Long publisherId;
    
    @ApiModelProperty(value = "发布者名称")
    private String publisherName;
    
    @ApiModelProperty(value = "执行者ID", example = "2")
    private Long executorId;
    
    @ApiModelProperty(value = "执行者名称")
    private String executorName;
    
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
} 