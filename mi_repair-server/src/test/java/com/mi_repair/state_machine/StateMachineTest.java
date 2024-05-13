package com.mi_repair.state_machine;

import com.mi_repair.entity.OrderRepair;
import com.mi_repair.enums.RepairOrderEvent;
import com.mi_repair.enums.RepairOrderStatus;
import com.mi_repair.statemachine.RepairOrderProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class StateMachineTest {

    @Resource
    RepairOrderProcessor repairOrderProcessor;
    @Test
    public void testListener() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);

        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_ACCEPT_ORDER);
    }
}
