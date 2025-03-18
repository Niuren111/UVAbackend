package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("培训机构查询DTO")
public class TrainingInstitutionQueryDTO {
    
    @ApiModelProperty(value = "机构名称", example = "无人机")
    private String name;
    
    @ApiModelProperty(value = "机构地址", example = "北京")
    private String address;
    
    @ApiModelProperty(value = "状态：0-待审核，1-已通过，2-已拒绝，3-已关闭", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "创建者ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "当前页码", example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页数量", example = "10")
    private Integer pageSize = 10;
} 