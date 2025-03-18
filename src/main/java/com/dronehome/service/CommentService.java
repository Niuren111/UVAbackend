package com.dronehome.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dronehome.dto.CommentAddDTO;
import com.dronehome.dto.CommentQueryDTO;
import com.dronehome.dto.CommentReplyDTO;
import com.dronehome.entity.Comment;
import com.dronehome.vo.CommentVO;

public interface CommentService {
    
    /**
     * 添加评论
     *
     * @param commentAddDTO 评论信息
     * @return 评论ID
     */
    Long addComment(CommentAddDTO commentAddDTO);
    
    /**
     * 回复评论
     *
     * @param commentReplyDTO 评论回复信息
     * @return 是否成功
     */
    boolean replyComment(CommentReplyDTO commentReplyDTO);
    
    /**
     * 分页查询评论
     *
     * @param queryDTO 查询条件
     * @return 评论分页结果
     */
    Page<CommentVO> pageComments(CommentQueryDTO queryDTO);
    
    /**
     * 获取评论详情
     *
     * @param id 评论ID
     * @return 评论详情
     */
    CommentVO getCommentDetail(Long id);
    
    /**
     * 审核评论
     *
     * @param id 评论ID
     * @param status 状态（1-通过，2-拒绝）
     * @return 是否成功
     */
    boolean auditComment(Long id, Integer status);
    
    /**
     * 删除评论
     *
     * @param id 评论ID
     * @return 是否成功
     */
    boolean deleteComment(Long id);
    
    /**
     * 获取商品平均评分
     *
     * @param productId 商品ID
     * @return 平均评分
     */
    Double getAverageRating(Long productId);
    
    /**
     * 获取商品评论数量
     *
     * @param productId 商品ID
     * @return 评论数量
     */
    Integer getCommentCount(Long productId);
} 