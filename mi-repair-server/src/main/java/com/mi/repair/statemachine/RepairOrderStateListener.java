package com.mi.repair.statemachine;


import com.mi.repair.entity.OrderRepair;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.Message;
import org.springframework.statemachine.annotation.OnTransition;
import org.springframework.statemachine.annotation.WithStateMachine;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author 李晴
 * @description 状态机配监听
 */
@Slf4j
@WithStateMachine
@Component
@Transactional
public class RepairOrderStateListener {
    //等待工程师接单 -> 工程师接单 -> 等待用户确认
    @OnTransition(source = "WAITING_FOR_WORKER_ACCEPTANCE",target = "WAITING_FOR_USER_CONFIRMATION")
    public boolean workerAcceptOrder(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) message.getHeaders().get("repairOrder");
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);

        return true;
    }

    //等待工程师接单 -> 用户取消 -> 订单已取消
    @OnTransition(source = "WAITING_FOR_WORKER_ACCEPTANCE",target = "CANCEL")
    public boolean userCancelOrderByWaitingWorker(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");

        return true;
    }

    //等待用户确认 -> 用户确认 -> 用户已确认
    @OnTransition(source = "WAITING_FOR_USER_CONFIRMATION",target = "CONFIRMED")
    public boolean userConfirmOrder(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");

        return true;
    }

    //等待用户确认 -> 用户取消 -> 订单已取消
    @OnTransition(source = "WAITING_FOR_USER_CONFIRMATION",target = "CANCEL")
    public boolean userCancelOrderByUserConfirmation(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");

        return true;
    }

    //用户已确认 -> 工程师检测失败 -> 等待工程师接单
    @OnTransition(source = "CONFIRMED",target = "WAITING_FOR_WORKER_ACCEPTANCE")
    public boolean workerInspectionFailed(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //用户已确认 -> 工程师检测成功 -> 申请材料
    @OnTransition(source = "CONFIRMED",target = "APPLICATION_MATERIALS")
    public boolean workerInspectionSuccess(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //申请材料 -> 申请材料成功 -> 维修
    @OnTransition(source = "APPLICATION_MATERIALS",target = "REPAIR")
    public boolean applicationMaterialsSuccessByApplicationMaterials(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //申请材料 -> 申请材料失败 -> 等待材料
    @OnTransition(source = "APPLICATION_MATERIALS",target = "WAITING_MATERIALS")
    public boolean applicationMaterialsFailed(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //等待材料 -> 用户取消 -> 维修失败
    @OnTransition(source = "WAITING_MATERIALS",target = "REPAIR_FAILED")
    public boolean userCancelByWaitingMaterials(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //等待材料 -> 等待材料成功 -> 维修
    @OnTransition(source = "WAITING_MATERIALS",target = "REPAIR")
    public boolean applicationMaterialsSuccessByWaitingMaterials(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //维修 -> 维修成功 -> 复检
    @OnTransition(source = "REPAIR",target = "RETEST")
    public boolean reapairSuccess(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //维修 -> 维修失败 -> 维修失败状态
    @OnTransition(source = "REPAIR",target = "REPAIR_FAILED")
    public boolean repairFailed(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //复检 -> 复检成功 -> 待支付
    @OnTransition(source = "RETEST",target = "WAITING_PAY")
    public boolean RetestSuccess(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //复检 -> 复检失败 -> 申请材料
    @OnTransition(source = "RETEST",target = "WAITING_MATERIALS")
    public boolean RetestFailed(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //复检 -> 复检超出次数 -> 维修失败
    @OnTransition(source = "RETEST",target = "REPAIR_FAILED")
    public boolean retestNumberOfExceedances(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //待支付 -> 用户支付 -> 已支付
    @OnTransition(source = "WAITING_PAY",target = "PAYED")
    public boolean userPay(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //已支付 -> 设备归还 -> 已完成
    @OnTransition(source = "PAYED",target = "DONE")
    public boolean returnDeviceByPayed(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }
    //维修失败 -> 设备归还 -> 已完成
    @OnTransition(source = "REPAIR_FAILED",target = "DONE")
    public boolean returnDeviceByRepairFailed(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");
        return true;
    }

}