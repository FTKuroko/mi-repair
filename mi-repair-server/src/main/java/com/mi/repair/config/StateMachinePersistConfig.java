package com.mi.repair.config;

import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.persist.RepositoryStateMachinePersist;
import org.springframework.statemachine.redis.RedisStateMachineContextRepository;
import org.springframework.statemachine.redis.RedisStateMachinePersister;

@Configuration
public class StateMachinePersistConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;


    /**
     * 注入RedisStateMachinePersister对象
     *
     * @return
     */
    @Bean(name = "orderRedisPersister")
    public RedisStateMachinePersister<RepairOrderStatus, RepairOrderEvent> redisPersister() {
        return new RedisStateMachinePersister<>(redisPersist());
    }

    /**
     * 通过redisConnectionFactory创建StateMachinePersist
     *
     * @return
     */
    public StateMachinePersist<RepairOrderStatus, RepairOrderEvent,String> redisPersist() {
        RedisStateMachineContextRepository<RepairOrderStatus, RepairOrderEvent> repository = new RedisStateMachineContextRepository<>(redisConnectionFactory);
        return new RepositoryStateMachinePersist<>(repository);
    }

}
