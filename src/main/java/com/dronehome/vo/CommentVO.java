package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@ApiModel("评论VO")
public class CommentVO {
    
    @ApiModelProperty(value = "主键ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "用户名称", example = "张三")
    private String userName;
    
    @ApiModelProperty(value = "用户头像", example = "https://example.com/avatar.jpg")
    private String userAvatar;
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Long productId;
    
    @ApiModelProperty(value = "商品名称", example = "大疆 Mavic 3 Pro 无人机")
    private String productName;
    
    @ApiModelProperty(value = "订单ID", example = "1")
    private Long orderId;
    
    @ApiModelProperty(value = "评论内容", example = "商品质量很好，物流很快，服务态度也很好")
    private String content;
    
    @ApiModelProperty(value = "评分：1-5", example = "5")
    private Integer rating;
    
    @ApiModelProperty(value = "评论图片列表")
    private List<String> imageList;
    
    @ApiModelProperty(value = "状态：0-待审核，1-已通过，2-已拒绝", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "回复人ID", example = "1")
    private Long replyId;
    
    @ApiModelProperty(value = "回复人名称", example = "客服小王")
    private String replyName;
    
    @ApiModelProperty(value = "回复内容", example = "感谢您的评价，我们会继续努力提供更好的服务")
    private String replyContent;
    
    @ApiModelProperty(value = "回复时间", example = "2024-03-21 10:00:00")
    private LocalDateTime replyTime;
    
    @ApiModelProperty(value = "创建时间", example = "2024-03-20 10:00:00")
    private LocalDateTime createTime;
} 