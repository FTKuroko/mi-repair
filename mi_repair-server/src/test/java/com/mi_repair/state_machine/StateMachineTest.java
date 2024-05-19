package com.mi_repair.state_machine;

import com.mi_repair.entity.OrderRepair;
import com.mi_repair.enums.RepairOrderEvent;
import com.mi_repair.enums.RepairOrderStatus;
import com.mi_repair.statemachine.RepairOrderProcessor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.annotation.OnTransition;

import javax.annotation.Resource;

@SpringBootTest
public class StateMachineTest {

    @Resource
    StateMachine<RepairOrderStatus, RepairOrderEvent> repairOrderStateMachine;

    @Resource
    RepairOrderProcessor repairOrderProcessor;

    @Test
    public void test() {
        System.out.println("------------等待工程师接单------------------");
        OrderRepair orderRepair = new OrderRepair();
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
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_ACCEPT_ORDER);
    }

    @Test
    public void userCancelOrderByWaitingWorker() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }

    @Test
    public void userConfirmOrder() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CONFIRM_ORDER);
    }
    @Test
    public void userCancelOrderByUserConfirmation() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }
    //用户已确认 -> 工程师检测失败 -> 等待工程师接单
    @Test
    public void workerInspectionFailed() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.CONFIRMED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_INSPECTION_FAILED);
    }
    //用户已确认 -> 工程师检测成功 -> 申请材料
    @Test
    public void workerInspectionSuccess() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.CONFIRMED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.WORKER_INSPECTION_SUCCESS);
    }
    //申请材料 -> 申请材料成功 -> 维修
    @Test
    public void applicationMaterialsSuccessByApplicationMaterials() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.APPLICATION_MATERIALS);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS);
    }
    //申请材料 -> 申请材料失败 -> 等待材料
    @Test
    public void applicationMaterialsFailed() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.APPLICATION_MATERIALS);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.APPLICATION_MATERIALS_FAILED);
    }
    //等待材料 -> 用户取消 -> 维修失败
    @Test
    public void userCancelByWaitingMaterials() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_MATERIALS);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_CANCEL_ORDER);
    }
    //等待材料 -> 等待材料成功 -> 维修
    @Test
    public void applicationMaterialsSuccessByWaitingMaterials() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_MATERIALS);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS);
    }
    //维修 -> 维修成功 -> 复检
    @Test
    public void repairSuccess() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.REPAIR);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.REPAIR_SUCCESS);
    }
    //维修 -> 维修失败 -> 维修失败状态
    @Test
    public void repairFailed() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.REPAIR);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.REPAIR_FAILED);
    }
    //复检 -> 复检成功 -> 待支付
    @Test
    public void retestSuccess() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.RETEST);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.RETEST_SUCCESS);
    }
    //复检 -> 复检失败 -> 申请材料
    @Test
    public void retestFailed() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.RETEST);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.RETEST_FAILED);
    }
    //复检 -> 复检超出次数 -> 维修失败
    @Test
    public void retestNumberOfExc() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.RETEST);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.RETEST_NUMBER_OF_EXCEEDANCES);
    }
    //待支付 -> 用户支付 -> 已支付
    @Test
    public void userPay() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.WAITING_PAY);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.USER_PAY);
    }
    //已支付 -> 设备归还 -> 已完成
    @Test
    public void returnDeviceByPayed() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.PAYED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.DEVICE_RETURN);
    }
    //维修失败 -> 设备归还 -> 已完成
    @Test
    public void returnDeviceByRepairFailed() {
        OrderRepair orderRepair = new OrderRepair();
        orderRepair.setRepairOrderStatus(RepairOrderStatus.REPAIR_FAILED);
        repairOrderProcessor.process(orderRepair, RepairOrderEvent.DEVICE_RETURN);
    }

}
