package com.dronehome.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dronehome.dto.TaskCreateDTO;
import com.dronehome.dto.TaskQueryDTO;
import com.dronehome.vo.TaskVO;

public interface TaskService {
    
    TaskVO createTask(TaskCreateDTO createDTO, Long publisherId);
    
    TaskVO getTaskById(Long id);
    
    IPage<TaskVO> getTaskList(TaskQueryDTO queryDTO);
    
    TaskVO updateTaskStatus(Long id, Integer status);
    
    TaskVO acceptTask(Long id, Long executorId);
    
    void deleteTask(Long id);
} 