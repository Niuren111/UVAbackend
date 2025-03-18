package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.math.BigDecimal;

@Data
@ApiModel("培训机构创建DTO")
public class TrainingInstitutionCreateDTO {
    
    @NotBlank(message = "机构名称不能为空")
    @ApiModelProperty(value = "机构名称", required = true, example = "无人机培训中心")
    private String name;
    
    @ApiModelProperty(value = "机构描述", example = "专业的无人机驾驶员培训机构")
    private String description;
    
    @NotBlank(message = "机构地址不能为空")
    @ApiModelProperty(value = "机构地址", required = true, example = "北京市海淀区XX路XX号")
    private String address;
    
    @NotBlank(message = "联系人不能为空")
    @ApiModelProperty(value = "联系人", required = true, example = "张三")
    private String contact;
    
    @NotBlank(message = "营业执照不能为空")
    @ApiModelProperty(value = "营业执照", required = true, example = "营业执照图片URL")
    private String license;
    
    @NotBlank(message = "资质证书不能为空")
    @ApiModelProperty(value = "资质证书", required = true, example = "资质证书图片URL")
    private String qualification;
    
    @NotNull(message = "培训价格不能为空")
    @Positive(message = "培训价格必须大于0")
    @ApiModelProperty(value = "培训价格", required = true, example = "5000.00")
    private BigDecimal price;
    
    @ApiModelProperty(value = "课程信息", example = "无人机驾驶员培训课程详情")
    private String courseInfo;
    
    @ApiModelProperty(value = "教师信息", example = "专业无人机培训教师团队介绍")
    private String teacherInfo;
    
    @ApiModelProperty(value = "设备信息", example = "专业无人机培训设备清单")
    private String equipmentInfo;
} 