package com.mi.repair.state_machine;

import com.mi.repair.entity.OrderRepair;
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
    @Resource(name="orderRedisPersister")
    private StateMachinePersister<RepairOrderStatus, RepairOrderEvent, String> orderRedisPersister;

//    @Test
//    public void testRedisPersister() throws Exception {
//        OrderRepair orderRepair = new OrderRepair();
//        orderRepair.setId(1L);
//        orderRedisPersister.persist(repairOrderStateMachine, String.valueOf(orderRepair.getId()));
//    }


    @Test
    public void testRedisPersister() throws Exception {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setId(2L);
        repairOrderProcessor.process(orderRepair,RepairOrderEvent.WORKER_ACCEPT_ORDER);
        orderRedisPersister.persist(repairOrderStateMachine, String.valueOf(orderRepair.getId()));
    }

    @Test
    public void testRestore() throws Exception {
        Long id = 2L;
        StateMachine<RepairOrderStatus, RepairOrderEvent> restore = orderRedisPersister.restore(repairOrderStateMachine, String.valueOf(id));
        System.out.println("恢复状态机后的状态为：" + restore.getState().getId());
    }

}
