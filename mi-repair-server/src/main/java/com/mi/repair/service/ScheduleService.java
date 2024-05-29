package com.mi.repair.service;

import com.mi.repair.dto.ScheduleDTO;
import com.mi.repair.result.PageResult;

/**
 * 罗慧
 */
public interface ScheduleService {
    PageResult getScheduleByOrderId(ScheduleDTO scheduleDTO);
}
