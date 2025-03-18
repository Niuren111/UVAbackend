package com.dronehome.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("order_item")
public class OrderItem {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private Long orderId;
    
    private String orderNo;
    
    private Long productId;
    
    private String productName;
    
    private String productImage;
    
    private BigDecimal productPrice;
    
    private Integer quantity;
    
    private BigDecimal totalPrice;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Boolean isDeleted;
} 