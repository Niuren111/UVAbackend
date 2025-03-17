package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "职位查询请求")
public class JobQueryDTO {
    
    @ApiModelProperty(value = "职位标题", example = "无人机")
    private String title;
    
    @ApiModelProperty(value = "公司名称", example = "科技")
    private String companyName;
    
    @ApiModelProperty(value = "工作地点", example = "北京")
    private String location;
    
    @ApiModelProperty(value = "状态：0-招聘中，1-已关闭", example = "0")
    private Integer status;
    
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
} 