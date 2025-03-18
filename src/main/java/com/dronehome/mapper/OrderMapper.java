package com.dronehome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dronehome.entity.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface OrderMapper extends BaseMapper<Order> {
    
    /**
     * 更新订单状态
     *
     * @param id 订单ID
     * @param oldStatus 原状态
     * @param newStatus 新状态
     * @return 影响行数
     */
    @Update("UPDATE `order` SET status = #{newStatus}, update_time = NOW() WHERE id = #{id} AND status = #{oldStatus}")
    int updateStatus(@Param("id") Long id, @Param("oldStatus") Integer oldStatus, @Param("newStatus") Integer newStatus);
    
    /**
     * 支付订单
     *
     * @param id 订单ID
     * @return 影响行数
     */
    @Update("UPDATE `order` SET status = 1, payment_time = NOW(), update_time = NOW() WHERE id = #{id} AND status = 0")
    int payOrder(@Param("id") Long id);
    
    /**
     * 发货
     *
     * @param id 订单ID
     * @return 影响行数
     */
    @Update("UPDATE `order` SET status = 2, delivery_time = NOW(), update_time = NOW() WHERE id = #{id} AND status = 1")
    int deliverOrder(@Param("id") Long id);
    
    /**
     * 完成订单
     *
     * @param id 订单ID
     * @return 影响行数
     */
    @Update("UPDATE `order` SET status = 3, completion_time = NOW(), update_time = NOW() WHERE id = #{id} AND status = 2")
    int completeOrder(@Param("id") Long id);
    
    /**
     * 取消订单
     *
     * @param id 订单ID
     * @return 影响行数
     */
    @Update("UPDATE `order` SET status = 4, update_time = NOW() WHERE id = #{id} AND status = 0")
    int cancelOrder(@Param("id") Long id);
} 