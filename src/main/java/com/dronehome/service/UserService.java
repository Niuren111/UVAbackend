package com.dronehome.service;

import com.dronehome.dto.UserLoginDTO;
import com.dronehome.dto.UserRegisterDTO;
import com.dronehome.vo.TokenVO;
import com.dronehome.vo.UserVO;

public interface UserService {
    UserVO register(UserRegisterDTO registerDTO);
    
    TokenVO login(UserLoginDTO loginDTO);
    
    UserVO getUserInfo(Long userId);
} 