package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("评论查询DTO")
public class CommentQueryDTO {
    
    @ApiModelProperty(value = "商品ID", example = "1")
    private Long productId;
    
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long userId;
    
    @ApiModelProperty(value = "评论状态：0-待审核，1-已通过，2-已拒绝", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "评分：1-5", example = "5")
    private Integer rating;
    
    @ApiModelProperty(value = "是否有图片", example = "true")
    private Boolean hasImage;
    
    @ApiModelProperty(value = "是否有回复", example = "true")
    private Boolean hasReply;
    
    @ApiModelProperty(value = "当前页码", required = true, example = "1")
    private Integer pageNum = 1;
    
    @ApiModelProperty(value = "每页记录数", required = true, example = "10")
    private Integer pageSize = 10;
} 