package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description = "Token信息")
public class TokenVO {
    
    @ApiModelProperty(value = "访问令牌", example = "eyJhbGciOiJIUzI1NiJ9...")
    private String token;
    
    @ApiModelProperty(value = "令牌类型", example = "Bearer")
    private String tokenType;
    
    @ApiModelProperty(value = "过期时间（秒）", example = "7200")
    private Long expiresIn;
} 