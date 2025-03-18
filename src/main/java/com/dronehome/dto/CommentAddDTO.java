package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.*;
import java.util.List;

@Data
@ApiModel("评论添加DTO")
public class CommentAddDTO {
    
    @ApiModelProperty(value = "用户ID", hidden = true)
    private Long userId;
    
    @NotNull(message = "商品ID不能为空")
    @ApiModelProperty(value = "商品ID", required = true, example = "1")
    private Long productId;
    
    @NotNull(message = "订单ID不能为空")
    @ApiModelProperty(value = "订单ID", required = true, example = "1")
    private Long orderId;
    
    @NotBlank(message = "评论内容不能为空")
    @Size(max = 500, message = "评论内容不能超过500个字符")
    @ApiModelProperty(value = "评论内容", required = true, example = "商品质量很好，物流很快，服务态度也很好")
    private String content;
    
    @NotNull(message = "评分不能为空")
    @Min(value = 1, message = "评分最小为1")
    @Max(value = 5, message = "评分最大为5")
    @ApiModelProperty(value = "评分：1-5", required = true, example = "5")
    private Integer rating;
    
    @ApiModelProperty(value = "评论图片列表", example = "[\"https://example.com/image1.jpg\", \"https://example.com/image2.jpg\"]")
    private List<String> imageList;
} 