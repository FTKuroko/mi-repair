package com.mi_repair.mapper;

import com.mi_repair.entity.Worker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkerMapper {
    /**
     * 用户登录
     * @param dto
     * @return
     */
    Worker login(Worker worker);

    /**
     * 用户注册
     * @param dto
     * @return
     */
    int register(Worker worker);
}
