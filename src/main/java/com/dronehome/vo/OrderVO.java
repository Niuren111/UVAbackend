package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("订单VO")
public class OrderVO {
    
    @ApiModelProperty(value = "订单ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "订单编号", example = "202403210001")
    private String orderNo;
    
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "用户名称", example = "user1")
    private String userName;
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Long productId;
    
    @ApiModelProperty(value = "商品名称", example = "大疆 Mavic 3 Pro 无人机")
    private String productName;
    
    @ApiModelProperty(value = "商品图片", example = "https://example.com/images/mavic3pro.jpg")
    private String productImage;
    
    @ApiModelProperty(value = "购买数量", example = "1")
    private Integer quantity;
    
    @ApiModelProperty(value = "商品单价", example = "13999.00")
    private BigDecimal price;
    
    @ApiModelProperty(value = "订单总金额", example = "13999.00")
    private BigDecimal totalAmount;
    
    @ApiModelProperty(value = "订单状态：0-待付款，1-待发货，2-待收货，3-已完成，4-已取消，5-已退款", example = "0")
    private Integer status;
    
    @ApiModelProperty(value = "订单状态描述", example = "待付款")
    private String statusDesc;
    
    @ApiModelProperty(value = "支付时间", example = "2024-03-21 10:00:00")
    private LocalDateTime paymentTime;
    
    @ApiModelProperty(value = "发货时间", example = "2024-03-22 10:00:00")
    private LocalDateTime deliveryTime;
    
    @ApiModelProperty(value = "完成时间", example = "2024-03-25 10:00:00")
    private LocalDateTime completionTime;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-21 09:00:00")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间", example = "2024-03-21 09:00:00")
    private LocalDateTime updateTime;
    
    @ApiModelProperty(value = "是否可评价", example = "true")
    private Boolean canComment;
    
    @ApiModelProperty(value = "收货人姓名", example = "张三")
    private String receiverName;
    
    @ApiModelProperty(value = "收货人电话", example = "13800138000")
    private String receiverPhone;
    
    @ApiModelProperty(value = "收货地址", example = "北京市海淀区XX路XX号")
    private String receiverAddress;
    
    @ApiModelProperty(value = "支付方式", example = "支付宝")
    private String paymentMethod;
    
    @ApiModelProperty(value = "支付流水号", example = "2024032000000001")
    private String paymentNo;
    
    @ApiModelProperty(value = "物流公司", example = "顺丰速运")
    private String deliveryCompany;
    
    @ApiModelProperty(value = "物流单号", example = "SF1234567890")
    private String deliveryNo;
    
    @ApiModelProperty(value = "订单备注", example = "请尽快发货")
    private String remark;
    
    @ApiModelProperty(value = "订单项列表")
    private List<OrderItemVO> orderItems;
    
    @Data
    @ApiModel("订单项VO")
    public static class OrderItemVO {
        
        @ApiModelProperty(value = "主键ID", example = "1")
        private Long id;
        
        @ApiModelProperty(value = "订单ID", example = "1")
        private Long orderId;
        
        @ApiModelProperty(value = "订单编号", example = "202403200001")
        private String orderNo;
        
        @ApiModelProperty(value = "商品ID", example = "1")
        private Long productId;
        
        @ApiModelProperty(value = "商品名称", example = "大疆 Mavic 3 Pro 无人机")
        private String productName;
        
        @ApiModelProperty(value = "商品图片", example = "https://example.com/image1.jpg")
        private String productImage;
        
        @ApiModelProperty(value = "商品单价", example = "15999.00")
        private BigDecimal productPrice;
        
        @ApiModelProperty(value = "购买数量", example = "1")
        private Integer quantity;
        
        @ApiModelProperty(value = "商品总价", example = "15999.00")
        private BigDecimal totalPrice;
    }
} 