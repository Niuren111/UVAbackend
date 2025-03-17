package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@ApiModel(description = "职位创建请求")
public class JobCreateDTO {
    
    @NotBlank(message = "职位标题不能为空")
    @ApiModelProperty(value = "职位标题", required = true, example = "无人机飞手")
    private String title;
    
    @NotBlank(message = "公司名称不能为空")
    @ApiModelProperty(value = "公司名称", required = true, example = "XX科技有限公司")
    private String companyName;
    
    @NotBlank(message = "薪资不能为空")
    @ApiModelProperty(value = "薪资", required = true, example = "8000-15000")
    private String salary;
    
    @NotBlank(message = "工作地点不能为空")
    @ApiModelProperty(value = "工作地点", required = true, example = "北京市朝阳区")
    private String location;
    
    @NotBlank(message = "职位描述不能为空")
    @ApiModelProperty(value = "职位描述", required = true, example = "负责无人机飞行任务...")
    private String description;
    
    @NotBlank(message = "任职要求不能为空")
    @ApiModelProperty(value = "任职要求", required = true, example = "1. 持有无人机驾驶证...")
    private String requirement;
    
    @NotBlank(message = "联系方式不能为空")
    @ApiModelProperty(value = "联系方式", required = true, example = "13800138000")
    private String contact;
} 