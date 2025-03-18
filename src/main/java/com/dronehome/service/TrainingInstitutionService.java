package com.dronehome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.dronehome.dto.TrainingInstitutionCreateDTO;
import com.dronehome.dto.TrainingInstitutionQueryDTO;
import com.dronehome.entity.TrainingInstitution;
import com.dronehome.vo.TrainingInstitutionVO;

public interface TrainingInstitutionService extends IService<TrainingInstitution> {
    
    /**
     * 创建培训机构
     *
     * @param createDTO 创建DTO
     * @param userId 创建者ID
     * @return 培训机构VO
     */
    TrainingInstitutionVO createInstitution(TrainingInstitutionCreateDTO createDTO, Long userId);
    
    /**
     * 获取培训机构详情
     *
     * @param id 培训机构ID
     * @return 培训机构VO
     */
    TrainingInstitutionVO getInstitutionById(Long id);
    
    /**
     * 获取培训机构列表
     *
     * @param queryDTO 查询DTO
     * @return 培训机构VO分页列表
     */
    IPage<TrainingInstitutionVO> getInstitutionList(TrainingInstitutionQueryDTO queryDTO);
    
    /**
     * 更新培训机构状态
     *
     * @param id 培训机构ID
     * @param status 状态
     * @return 培训机构VO
     */
    TrainingInstitutionVO updateInstitutionStatus(Long id, Integer status);
    
    /**
     * 删除培训机构
     *
     * @param id 培训机构ID
     */
    void deleteInstitution(Long id);
} 