package com.dronehome.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.dronehome.entity.LowAltitudePassenger;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface LowAltitudePassengerMapper extends BaseMapper<LowAltitudePassenger> {
    
    /**
     * 更新状态
     *
     * @param id 主键ID
     * @param status 状态
     * @return 影响行数
     */
    @Update("UPDATE low_altitude_passenger SET status = #{status}, update_time = NOW() WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") Integer status);
    
    /**
     * 增加已预订人数
     *
     * @param id 主键ID
     * @param count 增加数量
     * @return 影响行数
     */
    @Update("UPDATE low_altitude_passenger SET booked_count = booked_count + #{count}, update_time = NOW() WHERE id = #{id} AND (passenger_capacity - booked_count) >= #{count}")
    int increaseBookedCount(@Param("id") Long id, @Param("count") Integer count);
    
    /**
     * 减少已预订人数
     *
     * @param id 主键ID
     * @param count 减少数量
     * @return 影响行数
     */
    @Update("UPDATE low_altitude_passenger SET booked_count = booked_count - #{count}, update_time = NOW() WHERE id = #{id} AND booked_count >= #{count}")
    int decreaseBookedCount(@Param("id") Long id, @Param("count") Integer count);
} 