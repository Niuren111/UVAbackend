package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.LowAltitudePassengerCreateDTO;
import com.dronehome.dto.LowAltitudePassengerQueryDTO;
import com.dronehome.entity.LowAltitudePassenger;
import com.dronehome.entity.User;
import com.dronehome.exception.BusinessException;
import com.dronehome.mapper.LowAltitudePassengerMapper;
import com.dronehome.mapper.UserMapper;
import com.dronehome.service.LowAltitudePassengerService;
import com.dronehome.vo.LowAltitudePassengerVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LowAltitudePassengerServiceImpl extends ServiceImpl<LowAltitudePassengerMapper, LowAltitudePassenger> implements LowAltitudePassengerService {

    @Autowired
    private UserMapper userMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public LowAltitudePassengerVO createLowAltitudePassenger(LowAltitudePassengerCreateDTO createDTO, Long userId) {
        // 创建实体
        LowAltitudePassenger passenger = new LowAltitudePassenger();
        BeanUtils.copyProperties(createDTO, passenger);
        
        // 处理图片列表
        if (createDTO.getImageList() != null && !createDTO.getImageList().isEmpty()) {
            passenger.setImages(String.join(",", createDTO.getImageList()));
        }
        
        // 设置初始值
        passenger.setBookedCount(0);
        passenger.setStatus(0); // 待上架
        passenger.setUserId(userId);
        
        // 保存
        save(passenger);
        
        return convertToVO(passenger);
    }

    @Override
    public LowAltitudePassengerVO getLowAltitudePassengerDetail(Long id) {
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        return convertToVO(passenger);
    }

    @Override
    public Page<LowAltitudePassengerVO> pageLowAltitudePassengers(LowAltitudePassengerQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<LowAltitudePassenger> queryWrapper = new LambdaQueryWrapper<>();
        
        // 关键词搜索
        if (StringUtils.hasText(queryDTO.getKeyword())) {
            queryWrapper.and(wrapper -> 
                wrapper.like(LowAltitudePassenger::getTitle, queryDTO.getKeyword())
                    .or()
                    .like(LowAltitudePassenger::getDescription, queryDTO.getKeyword())
            );
        }
        
        // 出发地点
        if (StringUtils.hasText(queryDTO.getDepartureLocation())) {
            queryWrapper.like(LowAltitudePassenger::getDepartureLocation, queryDTO.getDepartureLocation());
        }
        
        // 到达地点
        if (StringUtils.hasText(queryDTO.getArrivalLocation())) {
            queryWrapper.like(LowAltitudePassenger::getArrivalLocation, queryDTO.getArrivalLocation());
        }
        
        // 出发时间范围
        if (queryDTO.getStartDepartureTime() != null) {
            queryWrapper.ge(LowAltitudePassenger::getDepartureTime, queryDTO.getStartDepartureTime());
        }
        if (queryDTO.getEndDepartureTime() != null) {
            queryWrapper.le(LowAltitudePassenger::getDepartureTime, queryDTO.getEndDepartureTime());
        }
        
        // 价格范围
        if (queryDTO.getMinPrice() != null) {
            queryWrapper.ge(LowAltitudePassenger::getPrice, queryDTO.getMinPrice());
        }
        if (queryDTO.getMaxPrice() != null) {
            queryWrapper.le(LowAltitudePassenger::getPrice, queryDTO.getMaxPrice());
        }
        
        // 状态
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(LowAltitudePassenger::getStatus, queryDTO.getStatus());
        }
        
        // 创建者ID
        if (queryDTO.getUserId() != null) {
            queryWrapper.eq(LowAltitudePassenger::getUserId, queryDTO.getUserId());
        }
        
        // 排序
        if (StringUtils.hasText(queryDTO.getSortField())) {
            boolean isAsc = "asc".equalsIgnoreCase(queryDTO.getSortOrder());
            
            switch (queryDTO.getSortField()) {
                case "price":
                    queryWrapper.orderBy(true, isAsc, LowAltitudePassenger::getPrice);
                    break;
                case "departureTime":
                    queryWrapper.orderBy(true, isAsc, LowAltitudePassenger::getDepartureTime);
                    break;
                case "createTime":
                    queryWrapper.orderBy(true, isAsc, LowAltitudePassenger::getCreateTime);
                    break;
                default:
                    queryWrapper.orderByDesc(LowAltitudePassenger::getCreateTime);
                    break;
            }
        } else {
            // 默认按创建时间降序
            queryWrapper.orderByDesc(LowAltitudePassenger::getCreateTime);
        }
        
        // 分页查询
        Page<LowAltitudePassenger> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<LowAltitudePassenger> passengerPage = page(page, queryWrapper);
        
        // 转换为VO
        Page<LowAltitudePassengerVO> resultPage = new Page<>();
        BeanUtils.copyProperties(passengerPage, resultPage, "records");
        
        List<LowAltitudePassengerVO> passengerVOList = passengerPage.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
        resultPage.setRecords(passengerVOList);
        
        return resultPage;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean updateStatus(Long id, Integer status) {
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        // 验证状态值
        if (status < 0 || status > 2) {
            throw new BusinessException("状态值无效");
        }
        
        int rows = baseMapper.updateStatus(id, status);
        return rows > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteLowAltitudePassenger(Long id) {
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        // 已有预订的不能删除
        if (passenger.getBookedCount() > 0) {
            throw new BusinessException("该服务已有预订，不能删除");
        }
        
        return removeById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean bookLowAltitudePassenger(Long id, Integer count) {
        if (count <= 0) {
            throw new BusinessException("预订人数必须大于0");
        }
        
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        // 检查状态
        if (passenger.getStatus() != 1) {
            throw new BusinessException("该服务未上架，不能预订");
        }
        
        // 检查是否已满
        if (passenger.getBookedCount() + count > passenger.getPassengerCapacity()) {
            throw new BusinessException("剩余座位不足");
        }
        
        int rows = baseMapper.increaseBookedCount(id, count);
        return rows > 0;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean cancelBooking(Long id, Integer count) {
        if (count <= 0) {
            throw new BusinessException("取消人数必须大于0");
        }
        
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        // 检查已预订人数
        if (passenger.getBookedCount() < count) {
            throw new BusinessException("取消人数不能大于已预订人数");
        }
        
        int rows = baseMapper.decreaseBookedCount(id, count);
        return rows > 0;
    }

    @Override
    public boolean isFull(Long id) {
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        return passenger.getBookedCount() >= passenger.getPassengerCapacity();
    }

    @Override
    public int getRemainingSeats(Long id) {
        LowAltitudePassenger passenger = getById(id);
        if (passenger == null) {
            throw new BusinessException("低空旅客服务不存在");
        }
        
        return passenger.getPassengerCapacity() - passenger.getBookedCount();
    }
    
    /**
     * 将实体转换为VO
     */
    private LowAltitudePassengerVO convertToVO(LowAltitudePassenger passenger) {
        LowAltitudePassengerVO vo = new LowAltitudePassengerVO();
        BeanUtils.copyProperties(passenger, vo);
        
        // 处理图片列表
        if (StringUtils.hasText(passenger.getImages())) {
            vo.setImageList(Arrays.asList(passenger.getImages().split(",")));
        }
        
        // 设置状态描述
        vo.setStatusDesc(getStatusDesc(passenger.getStatus()));
        
        // 获取创建者信息
        User user = userMapper.selectById(passenger.getUserId());
        if (user != null) {
            vo.setUserName(user.getUsername());
        }
        
        // 设置是否已满和剩余座位数
        vo.setIsFull(passenger.getBookedCount() >= passenger.getPassengerCapacity());
        vo.setRemainingSeats(passenger.getPassengerCapacity() - passenger.getBookedCount());
        
        return vo;
    }
    
    /**
     * 获取状态描述
     */
    private String getStatusDesc(Integer status) {
        switch (status) {
            case 0:
                return "待上架";
            case 1:
                return "已上架";
            case 2:
                return "已下架";
            default:
                return "未知状态";
        }
    }
} 