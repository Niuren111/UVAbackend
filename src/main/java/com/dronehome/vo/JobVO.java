package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "职位信息")
public class JobVO {
    
    @ApiModelProperty(value = "职位ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "职位标题", example = "无人机飞手")
    private String title;
    
    @ApiModelProperty(value = "公司名称", example = "XX科技有限公司")
    private String companyName;
    
    @ApiModelProperty(value = "薪资", example = "8000-15000")
    private String salary;
    
    @ApiModelProperty(value = "工作地点", example = "北京市朝阳区")
    private String location;
    
    @ApiModelProperty(value = "职位描述", example = "负责无人机飞行任务...")
    private String description;
    
    @ApiModelProperty(value = "任职要求", example = "1. 持有无人机驾驶证...")
    private String requirement;
    
    @ApiModelProperty(value = "联系方式", example = "13800138000")
    private String contact;
    
    @ApiModelProperty(value = "状态：0-招聘中，1-已关闭", example = "0")
    private Integer status;
    
    @ApiModelProperty(value = "发布者ID", example = "1")
    private Long publisherId;
    
    @ApiModelProperty(value = "发布者名称")
    private String publisherName;
    
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
} 