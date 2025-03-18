package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("低空旅客VO")
public class LowAltitudePassengerVO {
    
    @ApiModelProperty(value = "ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "标题", example = "深圳湾低空观光之旅")
    private String title;
    
    @ApiModelProperty(value = "描述", example = "乘坐无人机从空中俯瞰深圳湾美景，体验低空飞行的刺激与美妙。")
    private String description;
    
    @ApiModelProperty(value = "封面图片", example = "https://example.com/images/cover.jpg")
    private String coverImage;
    
    @ApiModelProperty(value = "图片列表")
    private List<String> imageList;
    
    @ApiModelProperty(value = "出发地点", example = "深圳湾体育中心")
    private String departureLocation;
    
    @ApiModelProperty(value = "到达地点", example = "深圳湾体育中心")
    private String arrivalLocation;
    
    @ApiModelProperty(value = "出发时间", example = "2024-04-01 10:00:00")
    private LocalDateTime departureTime;
    
    @ApiModelProperty(value = "到达时间", example = "2024-04-01 11:00:00")
    private LocalDateTime arrivalTime;
    
    @ApiModelProperty(value = "飞行时长（分钟）", example = "60")
    private Integer duration;
    
    @ApiModelProperty(value = "载客容量", example = "4")
    private Integer capacity;
    
    @ApiModelProperty(value = "已预订人数", example = "2")
    private Integer bookedCount;
    
    @ApiModelProperty(value = "价格", example = "999.00")
    private BigDecimal price;
    
    @ApiModelProperty(value = "无人机型号", example = "大疆 Mavic 3 Pro")
    private String droneModel;
    
    @ApiModelProperty(value = "飞行员姓名", example = "张三")
    private String pilotName;
    
    @ApiModelProperty(value = "飞行员执照编号", example = "P123456")
    private String pilotLicense;
    
    @ApiModelProperty(value = "飞行员经验", example = "5年飞行经验，累计飞行时长1000小时")
    private String pilotExperience;
    
    @ApiModelProperty(value = "安全措施", example = "配备专业安全设备，飞行前进行安全培训，全程专业人员陪同")
    private String safetyMeasures;
    
    @ApiModelProperty(value = "天气要求", example = "晴天或多云，风力不超过4级")
    private String weatherRequirements;
    
    @ApiModelProperty(value = "取消政策", example = "24小时前取消全额退款，24小时内取消扣除50%费用")
    private String cancellationPolicy;
    
    @ApiModelProperty(value = "状态：0-待上架，1-已上架，2-已下架", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "状态描述", example = "已上架")
    private String statusDesc;
    
    @ApiModelProperty(value = "创建者ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "创建者名称", example = "admin")
    private String userName;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-21 09:00:00")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间", example = "2024-03-21 09:00:00")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否已满", example = "false")
    private Boolean isFull;
    
    @ApiModelProperty(value = "剩余座位数", example = "2")
    private Integer remainingSeats;
} 