package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.JobCreateDTO;
import com.dronehome.dto.JobQueryDTO;
import com.dronehome.entity.Job;
import com.dronehome.entity.User;
import com.dronehome.mapper.JobMapper;
import com.dronehome.service.JobService;
import com.dronehome.service.UserService;
import com.dronehome.vo.JobVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job> implements JobService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public JobVO createJob(JobCreateDTO createDTO, Long publisherId) {
        Job job = new Job();
        BeanUtils.copyProperties(createDTO, job);
        job.setPublisherId(publisherId);
        job.setStatus(0); // 设置初始状态为招聘中
        
        this.save(job);
        
        return convertToVO(job);
    }
    
    @Override
    public JobVO getJobById(Long id) {
        Job job = this.getById(id);
        if (job == null) {
            throw new RuntimeException("职位不存在");
        }
        return convertToVO(job);
    }
    
    @Override
    public IPage<JobVO> getJobList(JobQueryDTO queryDTO) {
        Page<Job> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        LambdaQueryWrapper<Job> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(queryDTO.getTitle())) {
            queryWrapper.like(Job::getTitle, queryDTO.getTitle());
        }
        if (StringUtils.hasText(queryDTO.getCompanyName())) {
            queryWrapper.like(Job::getCompanyName, queryDTO.getCompanyName());
        }
        if (StringUtils.hasText(queryDTO.getLocation())) {
            queryWrapper.like(Job::getLocation, queryDTO.getLocation());
        }
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(Job::getStatus, queryDTO.getStatus());
        }
        queryWrapper.orderByDesc(Job::getCreateTime);
        
        IPage<Job> jobPage = this.page(page, queryWrapper);
        
        return jobPage.convert(this::convertToVO);
    }
    
    @Override
    public JobVO updateJobStatus(Long id, Integer status) {
        Job job = this.getById(id);
        if (job == null) {
            throw new RuntimeException("职位不存在");
        }
        
        job.setStatus(status);
        this.updateById(job);
        
        return convertToVO(job);
    }
    
    @Override
    public void deleteJob(Long id) {
        this.removeById(id);
    }
    
    private JobVO convertToVO(Job job) {
        JobVO jobVO = new JobVO();
        BeanUtils.copyProperties(job, jobVO);
        
        // 获取发布者信息
        User publisher = userService.getById(job.getPublisherId());
        if (publisher != null) {
            jobVO.setPublisherName(publisher.getUsername());
        }
        
        return jobVO;
    }
} 