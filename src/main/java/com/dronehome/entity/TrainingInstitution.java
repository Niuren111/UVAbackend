package com.dronehome.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("training_institution")
public class TrainingInstitution {
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String name;
    
    private String description;
    
    private String address;
    
    private String contact;
    
    private String license;
    
    private String qualification;
    
    private BigDecimal price;
    
    private String courseInfo;
    
    private String teacherInfo;
    
    private String equipmentInfo;
    
    private Integer status; // 0-待审核，1-正常，2-已关闭
    
    private Long userId;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Boolean isDeleted;
} 