package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.TrainingInstitutionCreateDTO;
import com.dronehome.dto.TrainingInstitutionQueryDTO;
import com.dronehome.entity.TrainingInstitution;
import com.dronehome.entity.User;
import com.dronehome.mapper.TrainingInstitutionMapper;
import com.dronehome.service.TrainingInstitutionService;
import com.dronehome.service.UserService;
import com.dronehome.vo.TrainingInstitutionVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class TrainingInstitutionServiceImpl extends ServiceImpl<TrainingInstitutionMapper, TrainingInstitution> implements TrainingInstitutionService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public TrainingInstitutionVO createInstitution(TrainingInstitutionCreateDTO createDTO, Long userId) {
        TrainingInstitution institution = new TrainingInstitution();
        BeanUtils.copyProperties(createDTO, institution);
        institution.setUserId(userId);
        institution.setStatus(0); // 设置初始状态为待审核
        
        this.save(institution);
        
        return convertToVO(institution);
    }
    
    @Override
    public TrainingInstitutionVO getInstitutionById(Long id) {
        TrainingInstitution institution = this.getById(id);
        if (institution == null) {
            throw new RuntimeException("培训机构不存在");
        }
        return convertToVO(institution);
    }
    
    @Override
    public IPage<TrainingInstitutionVO> getInstitutionList(TrainingInstitutionQueryDTO queryDTO) {
        Page<TrainingInstitution> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        LambdaQueryWrapper<TrainingInstitution> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(queryDTO.getName())) {
            queryWrapper.like(TrainingInstitution::getName, queryDTO.getName());
        }
        if (StringUtils.hasText(queryDTO.getAddress())) {
            queryWrapper.like(TrainingInstitution::getAddress, queryDTO.getAddress());
        }
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(TrainingInstitution::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getUserId() != null) {
            queryWrapper.eq(TrainingInstitution::getUserId, queryDTO.getUserId());
        }
        queryWrapper.orderByDesc(TrainingInstitution::getCreateTime);
        
        IPage<TrainingInstitution> institutionPage = this.page(page, queryWrapper);
        
        return institutionPage.convert(this::convertToVO);
    }
    
    @Override
    public TrainingInstitutionVO updateInstitutionStatus(Long id, Integer status) {
        TrainingInstitution institution = this.getById(id);
        if (institution == null) {
            throw new RuntimeException("培训机构不存在");
        }
        
        institution.setStatus(status);
        this.updateById(institution);
        
        return convertToVO(institution);
    }
    
    @Override
    public void deleteInstitution(Long id) {
        this.removeById(id);
    }
    
    private TrainingInstitutionVO convertToVO(TrainingInstitution institution) {
        TrainingInstitutionVO institutionVO = new TrainingInstitutionVO();
        BeanUtils.copyProperties(institution, institutionVO);
        
        // 获取创建者信息
        User user = userService.getById(institution.getUserId());
        if (user != null) {
            institutionVO.setUserName(user.getUsername());
        }
        
        return institutionVO;
    }
} 