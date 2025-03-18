package com.dronehome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.dto.LowAltitudePassengerCreateDTO;
import com.dronehome.dto.LowAltitudePassengerQueryDTO;
import com.dronehome.vo.LowAltitudePassengerVO;

public interface LowAltitudePassengerService {
    
    /**
     * 创建低空旅客服务
     *
     * @param createDTO 创建DTO
     * @param userId 用户ID
     * @return 低空旅客VO
     */
    LowAltitudePassengerVO createLowAltitudePassenger(LowAltitudePassengerCreateDTO createDTO, Long userId);
    
    /**
     * 获取低空旅客服务详情
     *
     * @param id 主键ID
     * @return 低空旅客VO
     */
    LowAltitudePassengerVO getLowAltitudePassengerDetail(Long id);
    
    /**
     * 分页查询低空旅客服务
     *
     * @param queryDTO 查询DTO
     * @return 低空旅客VO分页
     */
    Page<LowAltitudePassengerVO> pageLowAltitudePassengers(LowAltitudePassengerQueryDTO queryDTO);
    
    /**
     * 更新低空旅客服务状态
     *
     * @param id 主键ID
     * @param status 状态
     * @return 是否成功
     */
    boolean updateStatus(Long id, Integer status);
    
    /**
     * 删除低空旅客服务
     *
     * @param id 主键ID
     * @return 是否成功
     */
    boolean deleteLowAltitudePassenger(Long id);
    
    /**
     * 预订低空旅客服务
     *
     * @param id 主键ID
     * @param count 预订人数
     * @return 是否成功
     */
    boolean bookLowAltitudePassenger(Long id, Integer count);
    
    /**
     * 取消预订低空旅客服务
     *
     * @param id 主键ID
     * @param count 取消人数
     * @return 是否成功
     */
    boolean cancelBooking(Long id, Integer count);
    
    /**
     * 检查是否已满
     *
     * @param id 主键ID
     * @return 是否已满
     */
    boolean isFull(Long id);
    
    /**
     * 获取剩余座位数
     *
     * @param id 主键ID
     * @return 剩余座位数
     */
    int getRemainingSeats(Long id);
} 