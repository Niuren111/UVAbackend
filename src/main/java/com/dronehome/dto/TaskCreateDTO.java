package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@ApiModel(description = "任务创建请求")
public class TaskCreateDTO {
    
    @NotBlank(message = "任务标题不能为空")
    @ApiModelProperty(value = "任务标题", required = true, example = "无人机航拍任务")
    private String title;
    
    @NotBlank(message = "任务描述不能为空")
    @ApiModelProperty(value = "任务描述", required = true, example = "需要无人机进行航拍...")
    private String description;
    
    @NotBlank(message = "任务要求不能为空")
    @ApiModelProperty(value = "任务要求", required = true, example = "1. 需要持有无人机驾驶证...")
    private String requirement;
    
    @NotNull(message = "任务预算不能为空")
    @DecimalMin(value = "0.01", message = "任务预算必须大于0")
    @ApiModelProperty(value = "任务预算", required = true, example = "1000.00")
    private BigDecimal budget;
    
    @NotBlank(message = "任务地点不能为空")
    @ApiModelProperty(value = "任务地点", required = true, example = "北京市朝阳区")
    private String location;
    
    @NotBlank(message = "联系方式不能为空")
    @ApiModelProperty(value = "联系方式", required = true, example = "13800138000")
    private String contact;
} 