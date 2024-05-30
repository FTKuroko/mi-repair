package com.mi.repair.controller.worker;

import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.dto.WorkerOrderPageQueryDTO;
import com.mi.repair.result.Result;
import com.mi.repair.utils.StateMachineUtil;
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
    @Autowired
    private StateMachineUtil stateMachineUtil;
    @PutMapping("/confirm")
    @ApiOperation("工程师接单")
    public Result<String> cofirm(@RequestParam("id") Long id){
        log.info("工程师接单:{}", id);
        // 向状态机发送 工程师接单事件
        stateMachineUtil.saveAndSendEvent(id,RepairOrderEvent.WORKER_ACCEPT_ORDER);
        // 1、 修改订单状态
        int i = orderRepairService.workerConfirm(id);
        // TODO：2、 向用户发起通知

        return i > 0 ? Result.success("工程师已接单") :
                Result.error("工程师接单失败");
    }

    @PostMapping("/page")
    @ApiOperation("工程师查询订单")
    public Result<PageResult> page(@RequestBody WorkerOrderPageQueryDTO workerOrderPageQueryDTO){
        log.info("工程师查询订单:{}", workerOrderPageQueryDTO);
        PageResult result = orderRepairService.pageQuery(workerOrderPageQueryDTO);
        return Result.success(result);
    }


    @PutMapping("/repairSuccess")
    @ApiOperation("工程师维修成功")
    public Result<String> repairSuccess(@RequestParam("id")Long id) {
        log.info("工程师维修成功:{}", id);
        // 向状态机发送 工程师维修成功
        stateMachineUtil.saveAndSendEvent(id,RepairOrderEvent.REPAIR_SUCCESS);
        // 1、 修改订单状态
        int i = orderRepairService.orderRepairSuccess(id);
        // TODO：2、 向用户发起通知

        return i > 0 ? Result.success("工程师维修成功") : Result.error("维修成功操作失效");
    }

    @PutMapping("/repairFailed")
    @ApiOperation("工程师维修失败")
    public Result<String> repairFailed(@RequestParam("id")Long id) {
        log.info("工程师维修失败:{}", id);
        // 向状态机发送 工程师维修失败
        stateMachineUtil.saveAndSendEvent(id,RepairOrderEvent.REPAIR_FAILED);
        // 1、 修改订单状态
        int i = orderRepairService.orderRepairFailed(id);
        // TODO：2、 向用户发起通知

        return i > 0 ? Result.success("工程师维修失败") : Result.error("维修失败操作失效");
    }
}
