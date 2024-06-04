package com.mi.repair.service;

import com.mi.repair.dto.ScheduleDTO;
import com.mi.repair.entity.Schedule;
import com.mi.repair.result.PageResult;

/**
 * 罗慧
 */
public interface ScheduleService {
    PageResult getScheduleByOrderId(ScheduleDTO scheduleDTO);

    int insertSchedule(Long orderId,Integer status,Integer type);

    Schedule getScheduleByOrderIdAndStatus(Long orderId, Integer status);
}
