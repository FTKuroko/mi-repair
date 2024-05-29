package com.mi.repair.controller.schedule;

import com.mi.repair.dto.ScheduleDTO;
import com.mi.repair.result.PageResult;
import com.mi.repair.result.Result;
import com.mi.repair.service.ScheduleService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 罗慧
 */
@RestController
@Slf4j
@RequestMapping("/schedule")
public class ScheduleController {

    @Autowired
    private ScheduleService scheduleService;

    @PostMapping("/get")
    @ApiOperation("查询工单进度")
    public Result<PageResult> pageQuery(@RequestBody ScheduleDTO scheduleDTO){
        log.info("查询工单进度:{}", scheduleDTO);
        PageResult result = scheduleService.getScheduleByOrderId(scheduleDTO);
        return Result.success(result);
    }
}
