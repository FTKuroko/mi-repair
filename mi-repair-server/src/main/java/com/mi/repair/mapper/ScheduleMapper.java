package com.mi.repair.mapper;

import com.mi.repair.dto.ScheduleDTO;
import com.mi.repair.entity.Schedule;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author 罗慧
 */
@Mapper
public interface ScheduleMapper {
    List<Schedule> getScheduleByOrderId(ScheduleDTO scheduleDTO);
    int insertSchedule(Schedule schedule);

    Schedule getScheduleByOrderIdAndStatus(Long orderId, Integer status);
}
