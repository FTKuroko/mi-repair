package com.mi_repair.statemachine;


import com.mi_repair.entity.OrderRepair;
import com.mi_repair.enums.RepairOrderEvent;
import com.mi_repair.enums.RepairOrderStatus;
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
    //工程师接单
    @OnTransition(source = "WAITING_FOR_WORKER_ACCEPTANCE",target = "WAITING_FOR_USER_CONFIRMATION")
    public boolean workerAcceptOrder(Message<RepairOrderEvent> message) {
        OrderRepair repairOrder = (OrderRepair) message.getHeaders().get("repairOrder");
        log.info("工程师接取维修订单");

        repairOrder.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);

        return true;
    }

    @OnTransition(source = "WAITING_FOR_WORKER_ACCEPTANCE",target = "CANCEL")
    public boolean userCancelOrderByWaitingWorker(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");

        return true;
    }

    @OnTransition(source = "WAITING_FOR_USER_CONFIRMATION",target = "CONFIRMED")
    public boolean userConfirmOrder(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");

        return true;
    }

    @OnTransition(source = "WAITING_FOR_USER_CONFIRMATION",target = "CANCEL")
    public boolean userCancelOrderByUserConfirmation(Message<RepairOrderEvent> message) {
        OrderRepair orderRepair = (OrderRepair) (message.getHeaders().get("repairOrder"));
        log.info("状态机监听:{ 订单状态:" + orderRepair.getRepairOrderStatus() + "(" + orderRepair.getRepairOrderStatus().getDescription() +"),执行事件:" + message.getPayload().name() + "(" + message.getPayload().getDescription() +") }");

        return true;
    }

}