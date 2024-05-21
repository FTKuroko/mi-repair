package com.mi.repair.config;




import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;
/**
 * @author 李晴
 * @description 状态机配置类
 */
@EnableStateMachine
@Configuration
@Slf4j
public class RepairOrderStateMachineConfig extends EnumStateMachineConfigurerAdapter<RepairOrderStatus, RepairOrderEvent> {


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
                //等待用户确认 -> 用户确认 -> 用户已确认
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION).target(RepairOrderStatus.CONFIRMED)
                .event(RepairOrderEvent.USER_CONFIRM_ORDER)
                .and()
                //等待工程师接单 -> 用户取消 -> 订单已取消
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE).target(RepairOrderStatus.CANCEL)
                .event(RepairOrderEvent.USER_CANCEL_ORDER)
                .and()
                //等待用户确认 -> 用户取消 -> 订单已取消
                .withExternal()
                .source(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION).target(RepairOrderStatus.CANCEL)
                .event(RepairOrderEvent.USER_CANCEL_ORDER)
                .and()
        ;

    }

}
