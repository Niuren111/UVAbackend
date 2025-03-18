package com.dronehome.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("low_altitude_passenger")
public class LowAltitudePassenger {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String title;
    
    private String description;
    
    private String departureLocation;
    
    private String destinationLocation;
    
    private String routeInfo;
    
    private Integer passengerCapacity;
    
    private Integer bookedCount;
    
    private BigDecimal price;
    
    private String vehicleInfo;
    
    private String pilotInfo;
    
    private String safetyMeasures;
    
    private String images;
    
    private Integer status;
    
    private Long userId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Boolean isDeleted;
} 