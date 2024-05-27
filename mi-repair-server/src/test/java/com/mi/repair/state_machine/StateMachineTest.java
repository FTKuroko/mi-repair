package com.mi.repair.state_machine;

import com.mi.repair.entity.StateMachineRepairOrder;
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
    public void test() {
        System.out.println("------------等待工程师接单------------------");
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_ACCEPT_ORDER);
        System.out.println("------------等待用户确认------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CONFIRM_ORDER);
        System.out.println("------------等待工程师检测------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.CONFIRMED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_INSPECTION_SUCCESS);
        System.out.println("-----------申请材料----------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.APPLICATION_MATERIALS);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS);
        System.out.println("--------------维修--------------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.REPAIR);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.REPAIR_SUCCESS);
        System.out.println("--------------复检--------------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.RETEST);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.RETEST_SUCCESS);
        System.out.println("--------------待支付--------------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_PAY);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_PAY);
        System.out.println("--------------设备归还--------------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.PAYED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.DEVICE_RETURN);
    }

    @Test
    public void workerAcceptOrder() {
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_ACCEPT_ORDER);
    }

    @Test
    public void userCancelOrderByWaitingWorker() {
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }

    @Test
    public void userConfirmOrder() {
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CONFIRM_ORDER);
    }
    @Test
    public void userCancelOrderByUserConfirmation() {
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }
}
