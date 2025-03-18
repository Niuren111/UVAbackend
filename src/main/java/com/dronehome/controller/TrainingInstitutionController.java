package com.dronehome.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.dronehome.common.Result;
import com.dronehome.dto.TrainingInstitutionCreateDTO;
import com.dronehome.dto.TrainingInstitutionQueryDTO;
import com.dronehome.service.TrainingInstitutionService;
import com.dronehome.vo.TrainingInstitutionVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "培训机构管理")
@RestController
@RequestMapping("/api/training-institutions")
public class TrainingInstitutionController {
    
    @Autowired
    private TrainingInstitutionService trainingInstitutionService;
    
    @ApiOperation("创建培训机构")
    @PostMapping
    public Result<TrainingInstitutionVO> createInstitution(@Validated @RequestBody TrainingInstitutionCreateDTO createDTO) {
        Long userId = Long.parseLong(SecurityContextHolder.getContext().getAuthentication().getName());
        return Result.success(trainingInstitutionService.createInstitution(createDTO, userId));
    }
    
    @ApiOperation("获取培训机构详情")
    @GetMapping("/{id}")
    public Result<TrainingInstitutionVO> getInstitution(@PathVariable Long id) {
        return Result.success(trainingInstitutionService.getInstitutionById(id));
    }
    
    @ApiOperation("获取培训机构列表")
    @GetMapping
    public Result<IPage<TrainingInstitutionVO>> getInstitutionList(TrainingInstitutionQueryDTO queryDTO) {
        return Result.success(trainingInstitutionService.getInstitutionList(queryDTO));
    }
    
    @ApiOperation("更新培训机构状态")
    @PutMapping("/{id}/status")
    public Result<TrainingInstitutionVO> updateInstitutionStatus(
            @PathVariable Long id,
            @RequestParam Integer status) {
        return Result.success(trainingInstitutionService.updateInstitutionStatus(id, status));
    }
    
    @ApiOperation("删除培训机构")
    @DeleteMapping("/{id}")
    public Result<Void> deleteInstitution(@PathVariable Long id) {
        trainingInstitutionService.deleteInstitution(id);
        return Result.success();
    }
} 