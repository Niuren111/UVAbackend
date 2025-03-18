package com.dronehome.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ApiModel("评论回复DTO")
public class CommentReplyDTO {
    
    @NotNull(message = "评论ID不能为空")
    @ApiModelProperty(value = "评论ID", required = true, example = "1")
    private Long commentId;
    
    @ApiModelProperty(value = "回复者ID", hidden = true)
    private Long replyId;
    
    @NotBlank(message = "回复内容不能为空")
    @Size(max = 500, message = "回复内容不能超过500个字符")
    @ApiModelProperty(value = "回复内容", required = true, example = "感谢您的评价，我们会继续努力提供更好的产品和服务！")
    private String replyContent;
} 