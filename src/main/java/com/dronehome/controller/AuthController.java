package com.dronehome.controller;

import com.dronehome.common.Result;
import com.dronehome.dto.UserLoginDTO;
import com.dronehome.dto.UserRegisterDTO;
import com.dronehome.service.UserService;
import com.dronehome.vo.TokenVO;
import com.dronehome.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@Tag(name = "认证接口", description = "用户注册、登录等认证相关接口")
public class AuthController {
    
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    @Operation(summary = "用户注册", description = "注册新用户")
    public Result<UserVO> register(@RequestBody @Validated UserRegisterDTO registerDTO) {
        UserVO userVO = userService.register(registerDTO);
        return Result.success(userVO);
    }
    
    @PostMapping("/login")
    @Operation(summary = "用户登录", description = "用户登录并获取令牌")
    public Result<TokenVO> login(@RequestBody @Validated UserLoginDTO loginDTO) {
        TokenVO tokenVO = userService.login(loginDTO);
        return Result.success(tokenVO);
    }
} 