package com.dronehome.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.dto.CommentAddDTO;
import com.dronehome.dto.CommentQueryDTO;
import com.dronehome.dto.CommentReplyDTO;
import com.dronehome.service.CommentService;
import com.dronehome.util.JwtUtil;
import com.dronehome.vo.CommentVO;
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
@RequestMapping("/api/comments")
@Api(tags = "评论管理接口")
public class CommentController {

    @Autowired
    private CommentService commentService;
    
    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping
    @ApiOperation("添加评论")
    public Result<Long> addComment(@RequestBody @Valid CommentAddDTO commentAddDTO, HttpServletRequest request) {
        // 从JWT中获取用户ID
        Long userId = jwtUtil.getUserIdFromToken(request);
        commentAddDTO.setUserId(userId);
        
        Long commentId = commentService.addComment(commentAddDTO);
        return Result.success(commentId, "评论提交成功，等待审核");
    }

    @GetMapping("/page")
    @ApiOperation("分页查询评论")
    public Result<Page<CommentVO>> pageComments(@Valid CommentQueryDTO queryDTO) {
        Page<CommentVO> page = commentService.pageComments(queryDTO);
        return Result.success(page);
    }

    @GetMapping("/{id}")
    @ApiOperation("获取评论详情")
    public Result<CommentVO> getCommentDetail(
            @ApiParam(value = "评论ID", required = true) @PathVariable Long id) {
        CommentVO commentVO = commentService.getCommentDetail(id);
        return Result.success(commentVO);
    }

    @PostMapping("/reply")
    @ApiOperation("回复评论")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Boolean> replyComment(@RequestBody @Valid CommentReplyDTO commentReplyDTO, HttpServletRequest request) {
        // 从JWT中获取管理员ID
        Long adminId = jwtUtil.getUserIdFromToken(request);
        commentReplyDTO.setReplyId(adminId);
        
        boolean result = commentService.replyComment(commentReplyDTO);
        return Result.success(result, "回复成功");
    }

    @PutMapping("/audit/{id}/{status}")
    @ApiOperation("审核评论")
    @PreAuthorize("hasRole('ADMIN')")
    public Result<Boolean> auditComment(
            @ApiParam(value = "评论ID", required = true) @PathVariable Long id,
            @ApiParam(value = "状态：1-通过，2-拒绝", required = true) @PathVariable Integer status) {
        boolean result = commentService.auditComment(id, status);
        return Result.success(result, status == 1 ? "评论已通过" : "评论已拒绝");
    }

    @DeleteMapping("/{id}")
    @ApiOperation("删除评论")
    @PreAuthorize("hasRole('ADMIN') or @securityService.isCommentOwner(#id)")
    public Result<Boolean> deleteComment(
            @ApiParam(value = "评论ID", required = true) @PathVariable Long id) {
        boolean result = commentService.deleteComment(id);
        return Result.success(result, "删除成功");
    }

    @GetMapping("/rating/{productId}")
    @ApiOperation("获取商品平均评分")
    public Result<Double> getAverageRating(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long productId) {
        Double rating = commentService.getAverageRating(productId);
        return Result.success(rating);
    }

    @GetMapping("/count/{productId}")
    @ApiOperation("获取商品评论数量")
    public Result<Integer> getCommentCount(
            @ApiParam(value = "商品ID", required = true) @PathVariable Long productId) {
        Integer count = commentService.getCommentCount(productId);
        return Result.success(count);
    }
} 