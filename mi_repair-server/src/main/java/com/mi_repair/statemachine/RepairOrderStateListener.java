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

@Slf4j
@WithStateMachine
@Component
@Transactional
public class RepairOrderStateListener {
    //工程师接单
    @OnTransition(source = "WAITING_FOR_WORKER_ACCEPTANCE",target = "WAITING_FOR_USER_CONFIRMATION")
    public boolean workerAcceptOrder(Message<RepairOrderEvent> message) {
        log.info("工程师接取维修订单");
        OrderRepair repairOrder = (OrderRepair) message.getHeaders().get("repairOrder");
        log.info("修改前"+repairOrder.getRepairOrderStatus().getCode());
        repairOrder.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        log.info("修改后"+repairOrder.getRepairOrderStatus().getCode());
        return true;
    }
}