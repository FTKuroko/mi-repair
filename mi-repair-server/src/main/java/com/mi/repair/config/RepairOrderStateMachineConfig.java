package com.mi_repair.config;


import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
import java.util.EnumSet;

@EnableStateMachine
@Configuration
@Slf4j
public class RepairOrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<RepairOrderStatus, RepairOrderEvent> {
    @Override
    public void configure(StateMachineConfigurationConfigurer<RepairOrderStatus, RepairOrderEvent> config)
            throws Exception {
        config
                .withConfiguration()
                .autoStartup(true); // 设置自动启动
    }

    @Override
    public void configure(StateMachineStateConfigurer<RepairOrderStatus, RepairOrderEvent> stateConfigurer) throws Exception {
        stateConfigurer.withStates()
                .initial(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE) // 初始状态
                .states(EnumSet.allOf(RepairOrderStatus.class)); // 所有状态
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<RepairOrderStatus, RepairOrderEvent> transitions) throws Exception {
        transitions
                //等待工程师接单 -> 工程师接单 -> 等待用户确认
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE).target(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION)
                .event(RepairOrderEvent.WORKER_ACCEPT_ORDER)
                .and()
                //等待工程师接单 -> 用户取消 -> 订单已取消
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE).target(RepairOrderStatus.CANCEL)
                .event(RepairOrderEvent.USER_CANCEL_ORDER)
                .and()
                //等待用户确认 -> 用户确认 -> 用户已确认
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION).target(RepairOrderStatus.CONFIRMED)
                .event(RepairOrderEvent.USER_CONFIRM_ORDER)
                .and()
                //等待用户确认 -> 用户取消 -> 订单已取消
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION).target(RepairOrderStatus.CANCEL)
                .event(RepairOrderEvent.USER_CANCEL_ORDER)
                .and()
                //用户已确认 -> 工程师检测失败 -> 等待工程师接单
                .withExternal()
                .source(RepairOrderStatus.CONFIRMED).target(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE)
                .event(RepairOrderEvent.WORKER_INSPECTION_FAILED)
                .and()
                //用户已确认 -> 工程师检测成功 -> 申请材料
                .withExternal()
                .source(RepairOrderStatus.CONFIRMED).target(RepairOrderStatus.APPLICATION_MATERIALS)
                .event(RepairOrderEvent.WORKER_INSPECTION_SUCCESS)
                .and()
                //申请材料 -> 申请材料成功 -> 维修
                .withExternal()
                .source(RepairOrderStatus.APPLICATION_MATERIALS)
                .target(RepairOrderStatus.REPAIR)
                .event(RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS)
                .and()
                //申请材料 -> 申请材料失败 -> 等待材料
                .withExternal()
                .source(RepairOrderStatus.APPLICATION_MATERIALS)
                .target(RepairOrderStatus.WAITING_MATERIALS)
                .event(RepairOrderEvent.APPLICATION_MATERIALS_FAILED)
                .and()
//                //等待材料 -> 用户取消 -> 维修失败
//                .withExternal()
//                .source(RepairOrderStatus.WAITING_MATERIALS)
//                .target(RepairOrderStatus.REPAIR_FAILED)
//                .event(RepairOrderEvent.USER_CANCEL_ORDER)
//                .and()
                //等待材料 -> 等待材料成功 -> 维修
                .withExternal()
                .source(RepairOrderStatus.WAITING_MATERIALS)
                .target(RepairOrderStatus.REPAIR)
                .event(RepairOrderEvent.APPLICATION_MATERIALS_SUCCESS)
                .and()
                //维修 -> 维修成功 -> 复检
                .withExternal()
                .source(RepairOrderStatus.REPAIR)
                .target(RepairOrderStatus.RETEST)
                .event(RepairOrderEvent.REPAIR_SUCCESS)
                .and()
                //维修 -> 维修失败 -> 维修失败状态
                .withExternal()
                .source(RepairOrderStatus.REPAIR)
                .target(RepairOrderStatus.REPAIR_FAILED)
                .event(RepairOrderEvent.REPAIR_FAILED)
                .and()
                //复检 -> 复检成功 -> 待支付
                .withExternal()
                .source(RepairOrderStatus.RETEST)
                .target(RepairOrderStatus.WAITING_PAY)
                .event(RepairOrderEvent.RETEST_SUCCESS)
                .and()
                //复检 -> 复检失败 -> 申请材料
                .withExternal()
                .source(RepairOrderStatus.RETEST)
                .target(RepairOrderStatus.WAITING_MATERIALS)
                .event(RepairOrderEvent.RETEST_FAILED)
                .and()
                //复检 -> 复检超出次数 -> 维修失败
                .withExternal()
                .source(RepairOrderStatus.RETEST)
                .target(RepairOrderStatus.REPAIR_FAILED)
                .event(RepairOrderEvent.RETEST_NUMBER_OF_EXCEEDANCES)
                .and()
                //待支付 -> 支付 -> 已支付
                .withExternal()
                .source(RepairOrderStatus.WAITING_PAY)
                .target(RepairOrderStatus.PAYED)
                .event(RepairOrderEvent.USER_PAY)
                .and()
                //已支付 -> 设备归还 -> 已完成
                .withExternal()
                .source(RepairOrderStatus.PAYED)
                .target(RepairOrderStatus.DONE)
                .event(RepairOrderEvent.DEVICE_RETURN)
                .and()
                //维修失败 -> 设备归还 -> 已完成
                .withExternal()
                .source(RepairOrderStatus.REPAIR_FAILED)
                .target(RepairOrderStatus.DONE)
                .event(RepairOrderEvent.DEVICE_RETURN)
        ;
    }

}
