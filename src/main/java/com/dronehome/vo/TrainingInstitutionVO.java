package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@ApiModel("培训机构VO")
public class TrainingInstitutionVO {
    
    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "机构名称", example = "无人机培训中心")
    private String name;
    
    @ApiModelProperty(value = "机构描述", example = "专业的无人机驾驶员培训机构")
    private String description;
    
    @ApiModelProperty(value = "机构地址", example = "北京市海淀区XX路XX号")
    private String address;
    
    @ApiModelProperty(value = "联系人", example = "张三")
    private String contact;
    
    @ApiModelProperty(value = "营业执照", example = "营业执照图片URL")
    private String license;
    
    @ApiModelProperty(value = "资质证书", example = "资质证书图片URL")
    private String qualification;
    
    @ApiModelProperty(value = "培训价格", example = "5000.00")
    private BigDecimal price;
    
    @ApiModelProperty(value = "课程信息", example = "无人机驾驶员培训课程详情")
    private String courseInfo;
    
    @ApiModelProperty(value = "教师信息", example = "专业无人机培训教师团队介绍")
    private String teacherInfo;
    
    @ApiModelProperty(value = "设备信息", example = "专业无人机培训设备清单")
    private String equipmentInfo;
    
    @ApiModelProperty(value = "状态：0-待审核，1-已通过，2-已拒绝，3-已关闭", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "创建者ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "创建者名称", example = "admin")
    private String userName;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-20 10:00:00")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间", example = "2024-03-20 10:00:00")
    private LocalDateTime updateTime;
} 