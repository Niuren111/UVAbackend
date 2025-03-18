package com.dronehome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dronehome.entity.Product;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
    
    /**
     * 更新商品评分和评论数量
     *
     * @param productId 商品ID
     * @param rating 评分
     * @param commentCount 评论数量
     * @return 影响行数
     */
    @Update("UPDATE product SET rating = #{rating}, comment_count = #{commentCount} WHERE id = #{productId}")
    int updateRatingAndCommentCount(@Param("productId") Long productId, @Param("rating") Double rating, @Param("commentCount") Integer commentCount);
    
    /**
     * 增加商品销量
     *
     * @param productId 商品ID
     * @param count 增加数量
     * @return 影响行数
     */
    @Update("UPDATE product SET sales = sales + #{count} WHERE id = #{productId}")
    int increaseSales(@Param("productId") Long productId, @Param("count") Integer count);
    
    /**
     * 减少商品库存
     *
     * @param productId 商品ID
     * @param count 减少数量
     * @return 影响行数
     */
    @Update("UPDATE product SET stock = stock - #{count} WHERE id = #{productId} AND stock >= #{count}")
    int decreaseStock(@Param("productId") Long productId, @Param("count") Integer count);
} 