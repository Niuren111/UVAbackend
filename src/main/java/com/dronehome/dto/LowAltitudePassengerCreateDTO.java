package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.Future;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("低空旅客创建DTO")
public class LowAltitudePassengerCreateDTO {
    
    @NotBlank(message = "标题不能为空")
    @ApiModelProperty(value = "标题", required = true, example = "深圳湾低空观光之旅")
    private String title;
    
    @NotBlank(message = "描述不能为空")
    @ApiModelProperty(value = "描述", required = true, example = "乘坐无人机从空中俯瞰深圳湾美景，体验低空飞行的刺激与美妙。")
    private String description;
    
    @NotBlank(message = "封面图片不能为空")
    @ApiModelProperty(value = "封面图片", required = true, example = "https://example.com/images/cover.jpg")
    private String coverImage;
    
    @ApiModelProperty(value = "图片列表", example = "[\"https://example.com/images/1.jpg\", \"https://example.com/images/2.jpg\"]")
    private List<String> imageList;
    
    @NotBlank(message = "出发地点不能为空")
    @ApiModelProperty(value = "出发地点", required = true, example = "深圳湾体育中心")
    private String departureLocation;
    
    @NotBlank(message = "到达地点不能为空")
    @ApiModelProperty(value = "到达地点", required = true, example = "深圳湾体育中心")
    private String arrivalLocation;
    
    @NotNull(message = "出发时间不能为空")
    @Future(message = "出发时间必须是未来时间")
    @ApiModelProperty(value = "出发时间", required = true, example = "2024-04-01 10:00:00")
    private LocalDateTime departureTime;
    
    @NotNull(message = "到达时间不能为空")
    @Future(message = "到达时间必须是未来时间")
    @ApiModelProperty(value = "到达时间", required = true, example = "2024-04-01 11:00:00")
    private LocalDateTime arrivalTime;
    
    @NotNull(message = "飞行时长不能为空")
    @Min(value = 1, message = "飞行时长最小为1分钟")
    @ApiModelProperty(value = "飞行时长（分钟）", required = true, example = "60")
    private Integer duration;
    
    @NotNull(message = "载客容量不能为空")
    @Min(value = 1, message = "载客容量最小为1人")
    @ApiModelProperty(value = "载客容量", required = true, example = "4")
    private Integer capacity;
    
    @NotNull(message = "价格不能为空")
    @Min(value = 0, message = "价格不能小于0")
    @ApiModelProperty(value = "价格", required = true, example = "999.00")
    private BigDecimal price;
    
    @NotBlank(message = "无人机型号不能为空")
    @ApiModelProperty(value = "无人机型号", required = true, example = "大疆 Mavic 3 Pro")
    private String droneModel;
    
    @NotBlank(message = "飞行员姓名不能为空")
    @ApiModelProperty(value = "飞行员姓名", required = true, example = "张三")
    private String pilotName;
    
    @NotBlank(message = "飞行员执照编号不能为空")
    @ApiModelProperty(value = "飞行员执照编号", required = true, example = "P123456")
    private String pilotLicense;
    
    @NotBlank(message = "飞行员经验不能为空")
    @ApiModelProperty(value = "飞行员经验", required = true, example = "5年飞行经验，累计飞行时长1000小时")
    private String pilotExperience;
    
    @NotBlank(message = "安全措施不能为空")
    @ApiModelProperty(value = "安全措施", required = true, example = "配备专业安全设备，飞行前进行安全培训，全程专业人员陪同")
    private String safetyMeasures;
    
    @NotBlank(message = "天气要求不能为空")
    @ApiModelProperty(value = "天气要求", required = true, example = "晴天或多云，风力不超过4级")
    private String weatherRequirements;
    
    @NotBlank(message = "取消政策不能为空")
    @ApiModelProperty(value = "取消政策", required = true, example = "24小时前取消全额退款，24小时内取消扣除50%费用")
    private String cancellationPolicy;
} 