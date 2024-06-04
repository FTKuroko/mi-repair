package com.mi.repair.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.mi.repair.context.BaseContext;
import com.mi.repair.dto.ScheduleDTO;
import com.mi.repair.entity.Schedule;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.mapper.ScheduleMapper;
import com.mi.repair.mapper.UserMapper;
import com.mi.repair.mapper.WorkerMapper;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.ScheduleService;
import com.mi.repair.vo.ScheduleVO;
import lombok.extern.slf4j.Slf4j;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 罗慧
 */
@Service
@Slf4j
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleMapper scheduleMapper;

    @Override
    public PageResult getScheduleByOrderId(ScheduleDTO scheduleDTO) {
        List<Schedule> list = scheduleMapper.getScheduleByOrderId(scheduleDTO);
        int size = list.size();
        List<ScheduleVO> result = new ArrayList<>(size);
        for(Schedule entity : list){
            ScheduleVO scheduleVo = new ScheduleVO();
            BeanUtil.copyProperties(entity,scheduleVo);
            for(RepairOrderStatus status : RepairOrderStatus.values()){
                if(entity.getStatus().equals(status.getCode())){
                    scheduleVo.setStatusInfo(status.getDescription());
                }
            }
            result.add(scheduleVo);
        }
        return  new PageResult(size,result);
    }

    @Override
    public int insertSchedule(Long orderId, Integer status, Integer type) {
        Schedule schedule = new Schedule();
        Long id = BaseContext.getCurrentId();
        schedule.setUserId(id);
        schedule.setOrderId(orderId);
        schedule.setStatus(status);
        schedule.setType(type);
        LocalDateTime time = LocalDateTime.now();
        schedule.setCreateTime(time);
        schedule.setUpdateTime(time);
        return scheduleMapper.insertSchedule(schedule);
    }

    @Override
    public Schedule getScheduleByOrderIdAndStatus(Long orderId, Integer status) {
        return scheduleMapper.getScheduleByOrderIdAndStatus(orderId, status);
    }
}
