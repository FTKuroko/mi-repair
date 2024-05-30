package com.mi.repair.state_machine;

import com.mi.repair.entity.StateMachineRepairOrder;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.statemachine.RepairOrderProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.persist.StateMachinePersister;

import javax.annotation.Resource;

@SpringBootTest
public class RedisPersistTest {

    @Resource
    StateMachine<RepairOrderStatus, RepairOrderEvent> repairOrderStateMachine;

    @Resource
    RepairOrderProcessor repairOrderProcessor;
    @Resource(name="repairOrderRedisPersister")
    private StateMachinePersister<RepairOrderStatus, RepairOrderEvent, String> orderRedisPersister;

//    @Test
//    public void testRedisPersister() throws Exception {
//        OrderRepair orderRepair = new OrderRepair();
//        orderRepair.setId(1L);
//        orderRedisPersister.persist(repairOrderStateMachine, String.valueOf(orderRepair.getId()));
//    }


    @Test
    public void testRedisPersister() throws Exception {
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setId(2L);
        repairOrderProcessor.process(orderRepair,RepairOrderEvent.WORKER_ACCEPT_ORDER);
        orderRedisPersister.persist(repairOrderStateMachine, String.valueOf(orderRepair.getId()));
    }

    @Test
    public void testRestore() throws Exception {// 根据订单id查询状态机状态
        Long id = 12L;
        StateMachine<RepairOrderStatus, RepairOrderEvent> restore = orderRedisPersister.restore(repairOrderStateMachine, String.valueOf(id));
        System.out.println("恢复状态机后的状态为：" + restore.getState().getId());
    }

    @Test
    public void test() throws Exception {
        Long id = 100L;
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setId(id);
        orderRepair.setRepairOrderStatus(RepairOrderStatus.REPAIR);
//        repairOrderProcessor.process(orderRepair,RepairOrderEvent.WORKER_ACCEPT_ORDER);
//        System.out.println("===========>" + orderRepair.getRepairOrderStatus());
        orderRedisPersister.persist(repairOrderStateMachine, String.valueOf(orderRepair.getId()));

//        StateMachine<RepairOrderStatus, RepairOrderEvent> restore = orderRedisPersister.restore(repairOrderStateMachine, String.valueOf(id));
//        System.out.println("恢复状态机后的状态为：" + restore.getState().getId());
    }

    @Test
    public void t() throws Exception {
        StateMachine<RepairOrderStatus, RepairOrderEvent> restore = orderRedisPersister.restore(repairOrderStateMachine, String.valueOf(1L));
        restore.sendEvent(RepairOrderEvent.USER_CONFIRM_ORDER);
        orderRedisPersister.persist(restore, String.valueOf(1L));
    }

//    @Test
//    public void t2() throws Exception {
//        StateMachine<RepairOrderStatus, RepairOrderEvent> restore = orderRedisPersister.restore(repairOrderStateMachine, String.valueOf(1L));
//        StateMachineRepairOrder stateMachineRepairOrder = new StateMachineRepairOrder();
//        stateMachineRepairOrder.setRepairOrderStatus(restore.getState().getId());
//        stateMachineRepairOrder.setId(1L);
//        repairOrderProcessor.process(stateMachineRepairOrder,RepairOrderEvent.WORKER_INSPECTION_SUCCESS);
//        orderRedisPersister.persist(restore, String.valueOf(1L));
//    }

    @Test
    public void sendRepairStatus() throws Exception {//状态机订单状态设置为维修状态
        Long id = 100L;
        StateMachineRepairOrder orderRepair = new StateMachineRepairOrder();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_ACCEPT_ORDER);
        System.out.println("------------等待用户确认------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CONFIRM_ORDER);
        System.out.println("------------等待工程师检测------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.CONFIRMED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.UPLOAD_PICTURES);
        System.out.println("-----------申请材料----------------------");
        orderRepair.setRepairOrderStatus(RepairOrderStatus.APPLICATION_MATERIALS);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS);
        orderRepair.setId(id);
        orderRedisPersister.persist(repairOrderStateMachine, String.valueOf(id));

    }
}
