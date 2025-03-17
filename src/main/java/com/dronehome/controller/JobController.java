package com.dronehome.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dronehome.common.Result;
import com.dronehome.dto.JobCreateDTO;
import com.dronehome.dto.JobQueryDTO;
import com.dronehome.service.JobService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.JobVO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/job")
@Tag(name = "职位接口", description = "职位相关接口")
public class JobController {
    
    @Autowired
    private JobService jobService;
    
    @Autowired
    private JwtUtil jwtUtil;
    
    @PostMapping
    @Operation(summary = "发布职位", description = "发布新的职位信息",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<JobVO> createJob(@RequestBody @Validated JobCreateDTO createDTO,
                                 @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long publisherId = jwtUtil.getUserIdFromToken(token);
        JobVO jobVO = jobService.createJob(createDTO, publisherId);
        return Result.success(jobVO);
    }
    
    @GetMapping("/{id}")
    @Operation(summary = "获取职位详情", description = "根据ID获取职位详细信息")
    public Result<JobVO> getJobById(@PathVariable Long id) {
        JobVO jobVO = jobService.getJobById(id);
        return Result.success(jobVO);
    }
    
    @GetMapping("/list")
    @Operation(summary = "获取职位列表", description = "分页获取职位列表")
    public Result<IPage<JobVO>> getJobList(JobQueryDTO queryDTO) {
        IPage<JobVO> jobPage = jobService.getJobList(queryDTO);
        return Result.success(jobPage);
    }
    
    @PutMapping("/{id}/status")
    @Operation(summary = "更新职位状态", description = "更新职位的招聘状态",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<JobVO> updateJobStatus(@PathVariable Long id,
                                       @RequestParam Integer status,
                                       @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        JobVO jobVO = jobService.getJobById(id);
        
        // 验证当前用户是否为职位发布者
        if (!jobVO.getPublisherId().equals(userId)) {
            return Result.error(403, "无权操作此职位");
        }
        
        jobVO = jobService.updateJobStatus(id, status);
        return Result.success(jobVO);
    }
    
    @DeleteMapping("/{id}")
    @Operation(summary = "删除职位", description = "删除指定的职位信息",
            security = @SecurityRequirement(name = "bearerAuth"))
    public Result<Void> deleteJob(@PathVariable Long id,
                                @RequestHeader("Authorization") String authorization) {
        String token = authorization.substring(7);
        Long userId = jwtUtil.getUserIdFromToken(token);
        JobVO jobVO = jobService.getJobById(id);
        
        // 验证当前用户是否为职位发布者
        if (!jobVO.getPublisherId().equals(userId)) {
            return Result.error(403, "无权操作此职位");
        }
        
        jobService.deleteJob(id);
        return Result.success();
    }
} 