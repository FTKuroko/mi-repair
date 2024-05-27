package com.mi.repair.statemachine;

import com.mi.repair.entity.OrderRepair;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * author: 李晴
 * description: 状态机使用
 */
@Slf4j
@Component
public class RepairOrderProcessor {

    @Resource
    private StateMachine<RepairOrderStatus, RepairOrderEvent> repairOrderStateMachine;

    public boolean process(OrderRepair repairOrder, RepairOrderEvent repairOrderEvent) {
        Message<RepairOrderEvent> message = MessageBuilder.withPayload(repairOrderEvent).setHeader("repairOrder",repairOrder).build();
        boolean flag = sendEvent(message);
        return flag;
    }

    @SneakyThrows
    private boolean sendEvent(Message<RepairOrderEvent> message) {
        OrderRepair repairOrder = (OrderRepair) message.getHeaders().get("repairOrder");
        boolean result = repairOrderStateMachine.sendEvent(message);
        return result;
    }
}
