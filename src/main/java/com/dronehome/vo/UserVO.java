package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel(description = "用户信息")
public class UserVO {
    
    @ApiModelProperty(value = "用户ID", example = "1")
    private Long id;
    
    @ApiModelProperty(value = "用户名", example = "testuser")
    private String username;
    
    @ApiModelProperty(value = "邮箱", example = "test@example.com")
    private String email;
    
    @ApiModelProperty(value = "手机号", example = "13800138000")
    private String phone;
    
    @ApiModelProperty(value = "用户状态：0-禁用，1-正常", example = "1")
    private Integer status;
    
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
    
    @ApiModelProperty(value = "更新时间")
    private LocalDateTime updateTime;
} 