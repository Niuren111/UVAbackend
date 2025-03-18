package com.dronehome.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@TableName("`order`")
public class Order {
    
    @TableId(type = IdType.AUTO)
    private Long id;
    
    private String orderNo;
    
    private Long userId;
    
    private Long productId;
    
    private String productName;
    
    private String productImage;
    
    private Integer quantity;
    
    private BigDecimal price;
    
    private BigDecimal totalAmount;
    
    private Integer status; // 0-待付款，1-待发货，2-待收货，3-已完成，4-已取消，5-已退款
    
    private String receiverName;
    
    private String receiverPhone;
    
    private String receiverAddress;
    
    private String paymentMethod;
    
    private String paymentNo;
    
    private LocalDateTime paymentTime;
    
    private String deliveryCompany;
    
    private String deliveryNo;
    
    private LocalDateTime deliveryTime;
    
    private LocalDateTime completionTime;
    
    private String remark;
    
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    
    @TableLogic
    private Boolean isDeleted;
} 