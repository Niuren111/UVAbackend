package com.dronehome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dronehome.entity.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface CommentMapper extends BaseMapper<Comment> {
    
    /**
     * 获取商品的平均评分
     *
     * @param productId 商品ID
     * @return 平均评分
     */
    @Select("SELECT IFNULL(AVG(rating), 0) FROM comment WHERE product_id = #{productId} AND status = 1 AND is_deleted = 0")
    Double getAverageRating(@Param("productId") Long productId);
    
    /**
     * 获取商品的评论数量
     *
     * @param productId 商品ID
     * @return 评论数量
     */
    @Select("SELECT COUNT(*) FROM comment WHERE product_id = #{productId} AND status = 1 AND is_deleted = 0")
    Integer getCommentCount(@Param("productId") Long productId);
} 