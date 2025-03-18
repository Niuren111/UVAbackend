package com.dronehome.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dronehome.common.Result;
import com.dronehome.dto.TaskCreateDTO;
import com.dronehome.dto.TaskQueryDTO;
import com.dronehome.service.TaskService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.TaskVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/task")
@Tag(name = "任务接口", description = "任务相关接口")
public class TaskController {
    
    @Autowired
    private TaskService taskService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping
    @Operation(summary = "发布任务", description = "发布新的任务信息",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<TaskVO> createTask(@RequestBody @Validated TaskCreateDTO createDTO,
                                   @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long publisherId = jwtUtil.getUserIdFromToken(token);
        TaskVO taskVO = taskService.createTask(createDTO, publisherId);
        return Result.success(taskVO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取任务详情", description = "根据ID获取任务详细信息")
    public Result<TaskVO> getTaskById(@PathVariable Long id) {
        TaskVO taskVO = taskService.getTaskById(id);
        return Result.success(taskVO);
    }
    
    @GetMapping("/list")
    @Operation(summary = "获取任务列表", description = "分页获取任务列表")
    public Result<IPage<TaskVO>> getTaskList(TaskQueryDTO queryDTO) {
        IPage<TaskVO> taskPage = taskService.getTaskList(queryDTO);
        return Result.success(taskPage);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "更新任务状态", description = "更新任务的状态",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<TaskVO> updateTaskStatus(@PathVariable Long id,
                                         @RequestParam Integer status,
                                         @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        TaskVO taskVO = taskService.getTaskById(id);
        
        // 验证当前用户是否为任务发布者或执行者
        if (!taskVO.getPublisherId().equals(userId) && !userId.equals(taskVO.getExecutorId())) {
            return Result.error(403, "无权操作此任务");
        }
        
        taskVO = taskService.updateTaskStatus(id, status);
        return Result.success(taskVO);
    }
    
    @PostMapping("/{id}/accept")
    @Operation(summary = "接取任务", description = "接取指定的任务",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<TaskVO> acceptTask(@PathVariable Long id,
                                   @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long executorId = jwtUtil.getUserIdFromToken(token);
        TaskVO taskVO = taskService.acceptTask(id, executorId);
        return Result.success(taskVO);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除任务", description = "删除指定的任务信息",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<Void> deleteTask(@PathVariable Long id,
                                 @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        TaskVO taskVO = taskService.getTaskById(id);
        
        // 验证当前用户是否为任务发布者
        if (!taskVO.getPublisherId().equals(userId)) {
            return Result.error(403, "无权删除此任务");
        }
        
        taskService.deleteTask(id);
        return Result.success();
    }
} 