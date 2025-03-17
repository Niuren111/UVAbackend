package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.UserLoginDTO;
import com.dronehome.dto.UserRegisterDTO;
import com.dronehome.entity.User;
import com.dronehome.mapper.UserMapper;
import com.dronehome.service.UserService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.TokenVO;
import com.dronehome.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @Override
    public UserVO register(UserRegisterDTO registerDTO) {
        // 检查用户名是否已存在
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, registerDTO.getUsername());
        if (this.count(queryWrapper) > 0) {
            throw new RuntimeException("用户名已存在");
        }
        
        // 创建用户
        User user = new User();
        BeanUtils.copyProperties(registerDTO, user);
        // 加密密码
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        // 设置默认状态
        user.setStatus(0);
        
        // 保存用户
        this.save(user);
        
        // 返回用户信息
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
    
    @Override
    public TokenVO login(UserLoginDTO loginDTO) {
        // 查询用户
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, loginDTO.getUsername());
        User user = this.getOne(queryWrapper);
        
        // 验证用户存在且密码正确
        if (user == null || !passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
            throw new BadCredentialsException("用户名或密码错误");
        }
        
        // 检查用户状态
        if (user.getStatus() != 0) {
            throw new RuntimeException("账号已被禁用");
        }
        
        // 生成JWT令牌
        String token = jwtUtil.generateToken(user.getId(), user.getUsername());
        
        return new TokenVO(token, "Bearer", jwtUtil.getExpiration());
    }
    
    @Override
    public UserVO getUserInfo(Long userId) {
        User user = this.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在");
        }
        
        UserVO userVO = new UserVO();
        BeanUtils.copyProperties(user, userVO);
        return userVO;
    }
} 