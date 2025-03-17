package com.dronehome.controller;

import com.dronehome.common.Result;
import com.dronehome.service.UserService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.UserVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@Tag(name = "用户接口", description = "用户信息相关接口")
public class UserController {
    
    @Autowired
    private UserService userService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @GetMapping("/info")
    @Operation(summary = "获取用户信息", description = "获取当前登录用户的信息",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<UserVO> getUserInfo(@RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        UserVO userVO = userService.getUserInfo(userId);
        return Result.success(userVO);
    }
} 