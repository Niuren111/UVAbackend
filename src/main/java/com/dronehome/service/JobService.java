package com.dronehome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dronehome.dto.JobCreateDTO;
import com.dronehome.dto.JobQueryDTO;
import com.dronehome.vo.JobVO;

public interface JobService {
    
    JobVO createJob(JobCreateDTO createDTO, Long publisherId);
    
    JobVO getJobById(Long id);
    
    IPage<JobVO> getJobList(JobQueryDTO queryDTO);
    
    JobVO updateJobStatus(Long id, Integer status);
    
    void deleteJob(Long id);
} 