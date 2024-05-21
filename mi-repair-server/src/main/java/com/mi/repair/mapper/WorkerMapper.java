package com.mi.repair.mapper;

import com.mi.repair.entity.Worker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkerMapper {
    /**
     * 用户登录
     * @param worker
     * @return
     */
    Worker login(Worker worker);

    /**
     * 用户注册
     * @param worker
     * @return
     */
    int register(Worker worker);
}
