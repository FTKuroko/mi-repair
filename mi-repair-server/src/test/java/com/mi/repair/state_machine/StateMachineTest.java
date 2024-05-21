package com.mi.repair.state_machine;

import com.mi.repair.entity.OrderRepair;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.statemachine.RepairOrderProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
public class StateMachineTest {

    @Resource
    RepairOrderProcessor repairOrderProcessor;
    @Test
    public void testListenerWorkerAcceptOrder() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_ACCEPT_ORDER);
    }

    @Test
    public void testListenerUserCancelOrderByWaitingWorker() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }

    @Test
    public void testListenerUserConfirmOrder() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CONFIRM_ORDER);
    }
    @Test
    public void testListerUserCancelOrderByUserConfirmation() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }
}
