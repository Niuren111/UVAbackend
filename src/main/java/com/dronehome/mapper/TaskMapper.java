package com.dronehome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dronehome.entity.Task;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface TaskMapper extends BaseMapper<Task> {
} 