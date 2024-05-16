package com.mi_repair.controller.worker;

import com.mi_repair.dto.WorkerOrderPageQueryDTO;
import com.mi_repair.result.PageResult;
import com.mi_repair.result.Result;
import com.mi_repair.service.OrderRepairService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 17:03
 */
@RestController
@Slf4j
@RequestMapping("/worker/orderRepair")
public class WorkOrderRepairController {
    @Autowired
    OrderRepairService orderRepairService;
    @PutMapping("/confirm")
    @ApiOperation("工程师接单")
    public Result<String> cofirm(@RequestParam("id") Long id){
        log.info("工程师接单:{}", id);
        // 1、 修改订单状态
        int i = orderRepairService.workerConfirm(id);
        // TODO：2、 向用户发起通知

        return i > 0 ? Result.success("工程师已接单") :
                Result.error("工程师接单失败");
    }

    @GetMapping("/page")
    @ApiOperation("工程师查询订单")
    public Result<PageResult> page(@RequestBody WorkerOrderPageQueryDTO workerOrderPageQueryDTO){
        log.info("工程师查询订单:{}", workerOrderPageQueryDTO);
        PageResult result = orderRepairService.pageQuery(workerOrderPageQueryDTO);
        return Result.success(result);
    }
}
