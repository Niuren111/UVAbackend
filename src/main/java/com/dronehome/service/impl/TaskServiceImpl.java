package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.TaskCreateDTO;
import com.dronehome.dto.TaskQueryDTO;
import com.dronehome.entity.Task;
import com.dronehome.entity.User;
import com.dronehome.mapper.TaskMapper;
import com.dronehome.service.TaskService;
import com.dronehome.service.UserService;
import com.dronehome.vo.TaskVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

@Service
public class TaskServiceImpl extends ServiceImpl<TaskMapper, Task> implements TaskService {
    
    @Autowired
    private UserService userService;
    
    @Override
    public TaskVO createTask(TaskCreateDTO createDTO, Long publisherId) {
        Task task = new Task();
        BeanUtils.copyProperties(createDTO, task);
        task.setPublisherId(publisherId);
        task.setStatus(0); // 设置初始状态为待接单
        
        this.save(task);
        
        return convertToVO(task);
    }
    
    @Override
    public TaskVO getTaskById(Long id) {
        Task task = this.getById(id);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }
        return convertToVO(task);
    }
    
    @Override
    public IPage<TaskVO> getTaskList(TaskQueryDTO queryDTO) {
        Page<Task> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        
        LambdaQueryWrapper<Task> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.hasText(queryDTO.getTitle())) {
            queryWrapper.like(Task::getTitle, queryDTO.getTitle());
        }
        if (StringUtils.hasText(queryDTO.getLocation())) {
            queryWrapper.like(Task::getLocation, queryDTO.getLocation());
        }
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(Task::getStatus, queryDTO.getStatus());
        }
        if (queryDTO.getPublisherId() != null) {
            queryWrapper.eq(Task::getPublisherId, queryDTO.getPublisherId());
        }
        if (queryDTO.getExecutorId() != null) {
            queryWrapper.eq(Task::getExecutorId, queryDTO.getExecutorId());
        }
        queryWrapper.orderByDesc(Task::getCreateTime);
        
        IPage<Task> taskPage = this.page(page, queryWrapper);
        
        return taskPage.convert(this::convertToVO);
    }
    
    @Override
    public TaskVO updateTaskStatus(Long id, Integer status) {
        Task task = this.getById(id);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }
        
        task.setStatus(status);
        this.updateById(task);
        
        return convertToVO(task);
    }
    
    @Override
    @Transactional
    public TaskVO acceptTask(Long id, Long executorId) {
        Task task = this.getById(id);
        if (task == null) {
            throw new RuntimeException("任务不存在");
        }
        
        if (task.getStatus() != 0) {
            throw new RuntimeException("任务状态不正确");
        }
        
        if (task.getPublisherId().equals(executorId)) {
            throw new RuntimeException("不能接取自己发布的任务");
        }
        
        task.setExecutorId(executorId);
        task.setStatus(1); // 设置为进行中状态
        this.updateById(task);
        
        return convertToVO(task);
    }
    
    @Override
    public void deleteTask(Long id) {
        this.removeById(id);
    }
    
    private TaskVO convertToVO(Task task) {
        TaskVO taskVO = new TaskVO();
        BeanUtils.copyProperties(task, taskVO);
        
        // 获取发布者信息
        User publisher = userService.getById(task.getPublisherId());
        if (publisher != null) {
            taskVO.setPublisherName(publisher.getUsername());
        }
        
        // 获取执行者信息
        if (task.getExecutorId() != null) {
            User executor = userService.getById(task.getExecutorId());
            if (executor != null) {
                taskVO.setExecutorName(executor.getUsername());
            }
        }
        
        return taskVO;
    }
} 