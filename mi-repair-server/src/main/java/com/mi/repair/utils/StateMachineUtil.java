package com.mi.repair.utils;

import com.mi.repair.entity.StateMachineRepairOrder;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.statemachine.RepairOrderProcessor;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;
import org.springframework.stereotype.Component;
import javax.annotation.Resource;

@Component
public class StateMachineUtil {
    @Resource
    StateMachine<RepairOrderStatus, RepairOrderEvent> repairOrderStateMachine;
    @Resource
    RepairOrderProcessor repairOrderProcessor;
    @Resource(name="repairOrderRedisPersister")
    private StateMachinePersister<RepairOrderStatus, RepairOrderEvent, String> repairOrderRedisPersister;

    public void saveAndSendEvent(Long id,RepairOrderEvent repairOrderEvent) {
        try {
            StateMachine<RepairOrderStatus, RepairOrderEvent> restore = repairOrderRedisPersister.restore(repairOrderStateMachine, String.valueOf(id));
            StateMachineRepairOrder stateMachineRepairOrder = new StateMachineRepairOrder();
            stateMachineRepairOrder.setRepairOrderStatus(restore.getState().getId());
            stateMachineRepairOrder.setId(id);
            repairOrderProcessor.process(stateMachineRepairOrder,repairOrderEvent);
            repairOrderRedisPersister.persist(restore,String.valueOf(stateMachineRepairOrder.getId()));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
