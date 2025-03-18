package com.dronehome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.dto.LowAltitudePassengerCreateDTO;
import com.dronehome.dto.LowAltitudePassengerQueryDTO;
import com.dronehome.service.LowAltitudePassengerService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.LowAltitudePassengerVO;
import com.dronehome.vo.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/api/low-altitude-passengers")
@Api(tags = "低空旅客管理接口")
public class LowAltitudePassengerController {

    @Autowired
    private LowAltitudePassengerService lowAltitudePassengerService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ApiOperation("创建低空旅客服务")
    @PreAuthorize("hasRole('ADMIN') or hasRole('SELLER')")
    public Result<LowAltitudePassengerVO> createLowAltitudePassenger(
            @RequestBody @Valid LowAltitudePassengerCreateDTO createDTO,
            HttpServletRequest request) {
        // 从JWT中获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(request);
        
        LowAltitudePassengerVO vo = lowAltitudePassengerService.createLowAltitudePassenger(createDTO, userId);
        return Result.success(vo, "创建成功");
    }

    @GetMapping("/{id}")
    @ApiOperation("获取低空旅客服务详情")
    public Result<LowAltitudePassengerVO> getLowAltitudePassengerDetail(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id) {
        LowAltitudePassengerVO vo = lowAltitudePassengerService.getLowAltitudePassengerDetail(id);
        return Result.success(vo);
    }

    @GetMapping("/page")
    @ApiOperation("分页查询低空旅客服务")
    public Result<Page<LowAltitudePassengerVO>> pageLowAltitudePassengers(
            @Valid LowAltitudePassengerQueryDTO queryDTO) {
        Page<LowAltitudePassengerVO> page = lowAltitudePassengerService.pageLowAltitudePassengers(queryDTO);
        return Result.success(page);
    }

    @PutMapping("/{id}/status/{status}")
    @ApiOperation("更新低空旅客服务状态")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isLowAltitudePassengerOwner(#id)")
    public Result<Boolean> updateStatus(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id,
            @ApiParam(value = "状态：0-待上架，1-已上架，2-已下架", required = true) @PathVariable Integer status) {
        boolean result = lowAltitudePassengerService.updateStatus(id, status);
        return Result.success(result, "状态更新成功");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除低空旅客服务")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isLowAltitudePassengerOwner(#id)")
    public Result<Boolean> deleteLowAltitudePassenger(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id) {
        boolean result = lowAltitudePassengerService.deleteLowAltitudePassenger(id);
        return Result.success(result, "删除成功");
    }

    @PostMapping("/{id}/book")
    @ApiOperation("预订低空旅客服务")
    public Result<Boolean> bookLowAltitudePassenger(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id,
            @ApiParam(value = "预订人数", required = true) @RequestParam Integer count) {
        boolean result = lowAltitudePassengerService.bookLowAltitudePassenger(id, count);
        return Result.success(result, "预订成功");
    }

    @PostMapping("/{id}/cancel")
    @ApiOperation("取消预订低空旅客服务")
    public Result<Boolean> cancelBooking(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id,
            @ApiParam(value = "取消人数", required = true) @RequestParam Integer count) {
        boolean result = lowAltitudePassengerService.cancelBooking(id, count);
        return Result.success(result, "取消预订成功");
    }

    @GetMapping("/{id}/is-full")
    @ApiOperation("检查低空旅客服务是否已满")
    public Result<Boolean> isFull(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id) {
        boolean result = lowAltitudePassengerService.isFull(id);
        return Result.success(result);
    }

    @GetMapping("/{id}/remaining-seats")
    @ApiOperation("获取低空旅客服务剩余座位数")
    public Result<Integer> getRemainingSeats(
            @ApiParam(value = "低空旅客服务ID", required = true) @PathVariable Long id) {
        int result = lowAltitudePassengerService.getRemainingSeats(id);
        return Result.success(result);
    }
} 