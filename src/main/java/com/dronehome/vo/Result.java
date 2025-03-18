package com.dronehome.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 统一响应结果
 */
@Data
@ApiModel("统一响应结果")
public class Result<T> {
    
    @ApiModelProperty(value = "状态码", example = "200")
    private Integer code;
    
    @ApiModelProperty(value = "消息", example = "操作成功")
    private String message;
    
    @ApiModelProperty(value = "数据")
    private T data;
    
    private Result() {
    }
    
    private Result(Integer code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }
    
    /**
     * 成功（无数据）
     */
    public static <T> Result<T> success() {
        return new Result<>(200, "操作成功", null);
    }
    
    /**
     * 成功（有数据）
     */
    public static <T> Result<T> success(T data) {
        return new Result<>(200, "操作成功", data);
    }
    
    /**
     * 成功（有数据和消息）
     */
    public static <T> Result<T> success(T data, String message) {
        return new Result<>(200, message, data);
    }
    
    /**
     * 失败（默认错误码）
     */
    public static <T> Result<T> fail(String message) {
        return new Result<>(500, message, null);
    }
    
    /**
     * 失败（自定义错误码）
     */
    public static <T> Result<T> fail(Integer code, String message) {
        return new Result<>(code, message, null);
    }
} 