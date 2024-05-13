package com.mi_repair.config;




import com.mi_repair.enums.RepairOrderEvent;
import com.mi_repair.enums.RepairOrderStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.config.EnableStateMachine;
import org.springframework.statemachine.config.EnumStateMachineConfigurerAdapter;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@EnableStateMachine
@Configuration
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
                //用户下单 -> 工程师接单 -> 用户确认
                .withExternal().source(RepairOrderStatus.WAITING_FOR_WORKER_ACCEPTANCE).target(RepairOrderStatus.WAITING_FOR_USER_CONFIRMATION)
                .event(RepairOrderEvent.WORKER_ACCEPT_ORDER)

        ;

    }

//    @Bean
//    public StateMachinePersister<RepairOrderStatus, RepairOrderEvent, RepairOrder> persister(){
//        return new DefaultStateMachinePersister<>(new StateMachinePersist<RepairOrderStatus, RepairOrderEvent, RepairOrder>() {
//            @Override
//            public void write(StateMachineContext<RepairOrderStatus, RepairOrderEvent> context, RepairOrder order) throws Exception {
//                //此处并没有进行持久化操作
//            }
//
//            @Override
//            public StateMachineContext<RepairOrderStatus, RepairOrderEvent> read(RepairOrder order) throws Exception {
//                //此处直接获取order中的状态，其实并没有进行持久化读取操作
//                return new DefaultStateMachineContext<>(order.getStatus(), null, null, null);
//            }
//        });
//    }

}
