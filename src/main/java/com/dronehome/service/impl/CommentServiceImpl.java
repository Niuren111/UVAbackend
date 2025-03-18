package com.dronehome.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dronehome.dto.CommentAddDTO;
import com.dronehome.dto.CommentQueryDTO;
import com.dronehome.dto.CommentReplyDTO;
import com.dronehome.entity.Comment;
import com.dronehome.entity.User;
import com.dronehome.exception.BusinessException;
import com.dronehome.mapper.CommentMapper;
import com.dronehome.mapper.ProductMapper;
import com.dronehome.mapper.UserMapper;
import com.dronehome.service.CommentService;
import com.dronehome.vo.CommentVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private ProductMapper productMapper;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Long addComment(CommentAddDTO commentAddDTO) {
        // 验证评分范围
        if (commentAddDTO.getRating() < 1 || commentAddDTO.getRating() > 5) {
            throw new BusinessException("评分必须在1-5之间");
        }
        
        // 构建评论实体
        Comment comment = new Comment();
        BeanUtils.copyProperties(commentAddDTO, comment);
        
        // 处理图片列表
        if (commentAddDTO.getImageList() != null && !commentAddDTO.getImageList().isEmpty()) {
            comment.setImages(String.join(",", commentAddDTO.getImageList()));
        }
        
        // 设置初始状态（待审核）
        comment.setStatus(0);
        
        // 保存评论
        save(comment);
        return comment.getId();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean replyComment(CommentReplyDTO commentReplyDTO) {
        Comment comment = getById(commentReplyDTO.getCommentId());
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        
        comment.setReplyId(commentReplyDTO.getReplyId());
        comment.setReplyContent(commentReplyDTO.getReplyContent());
        comment.setReplyTime(LocalDateTime.now());
        
        return updateById(comment);
    }

    @Override
    public Page<CommentVO> pageComments(CommentQueryDTO queryDTO) {
        // 构建查询条件
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        
        if (queryDTO.getProductId() != null) {
            queryWrapper.eq(Comment::getProductId, queryDTO.getProductId());
        }
        
        if (queryDTO.getUserId() != null) {
            queryWrapper.eq(Comment::getUserId, queryDTO.getUserId());
        }
        
        if (queryDTO.getStatus() != null) {
            queryWrapper.eq(Comment::getStatus, queryDTO.getStatus());
        }
        
        if (queryDTO.getRating() != null) {
            queryWrapper.eq(Comment::getRating, queryDTO.getRating());
        }
        
        if (queryDTO.getHasImage() != null && queryDTO.getHasImage()) {
            queryWrapper.isNotNull(Comment::getImages);
        }
        
        if (queryDTO.getHasReply() != null && queryDTO.getHasReply()) {
            queryWrapper.isNotNull(Comment::getReplyContent);
        }
        
        // 按创建时间降序排序
        queryWrapper.orderByDesc(Comment::getCreateTime);
        
        // 分页查询
        Page<Comment> page = new Page<>(queryDTO.getPageNum(), queryDTO.getPageSize());
        Page<Comment> commentPage = page(page, queryWrapper);
        
        // 转换为VO
        Page<CommentVO> resultPage = new Page<>();
        BeanUtils.copyProperties(commentPage, resultPage, "records");
        
        List<CommentVO> commentVOList = commentPage.getRecords().stream().map(this::convertToVO).collect(Collectors.toList());
        resultPage.setRecords(commentVOList);
        
        return resultPage;
    }

    @Override
    public CommentVO getCommentDetail(Long id) {
        Comment comment = getById(id);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        
        return convertToVO(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean auditComment(Long id, Integer status) {
        Comment comment = getById(id);
        if (comment == null) {
            throw new BusinessException("评论不存在");
        }
        
        // 验证状态值
        if (status != 1 && status != 2) {
            throw new BusinessException("状态值无效，只能为1(通过)或2(拒绝)");
        }
        
        // 如果是通过审核，更新商品评分和评论数量
        if (status == 1) {
            updateProductRatingAndCommentCount(comment.getProductId());
        }
        
        comment.setStatus(status);
        return updateById(comment);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteComment(Long id) {
        Comment comment = getById(id);
        if (comment == null) {
            return false;
        }
        
        boolean result = removeById(id);
        
        // 如果删除成功且评论状态为已通过，更新商品评分和评论数量
        if (result && comment.getStatus() == 1) {
            updateProductRatingAndCommentCount(comment.getProductId());
        }
        
        return result;
    }

    @Override
    public Double getAverageRating(Long productId) {
        return baseMapper.getAverageRating(productId);
    }

    @Override
    public Integer getCommentCount(Long productId) {
        return baseMapper.getCommentCount(productId);
    }
    
    /**
     * 更新商品评分和评论数量
     */
    private void updateProductRatingAndCommentCount(Long productId) {
        Double rating = getAverageRating(productId);
        Integer count = getCommentCount(productId);
        productMapper.updateRatingAndCommentCount(productId, rating, count);
    }
    
    /**
     * 将评论实体转换为VO
     */
    private CommentVO convertToVO(Comment comment) {
        CommentVO vo = new CommentVO();
        BeanUtils.copyProperties(comment, vo);
        
        // 处理图片列表
        if (StringUtils.hasText(comment.getImages())) {
            vo.setImageList(Arrays.asList(comment.getImages().split(",")));
        } else {
            vo.setImageList(new ArrayList<>());
        }
        
        // 获取用户信息
        User user = userMapper.selectById(comment.getUserId());
        if (user != null) {
            vo.setUserName(user.getUsername());
            vo.setUserAvatar(user.getAvatar());
        }
        
        // 获取回复者信息
        if (comment.getReplyId() != null) {
            User replyUser = userMapper.selectById(comment.getReplyId());
            if (replyUser != null) {
                vo.setReplyName(replyUser.getUsername());
            }
        }
        
        return vo;
    }
} 