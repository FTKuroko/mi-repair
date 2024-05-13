package com.mi_repair.mapper;

import com.mi_repair.dto.WorkerLoginDTO;
import com.mi_repair.dto.WorkerRegDTO;
import com.mi_repair.entity.Worker;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface WorkerMapper {
    /**
     * 用户登录
     * @param dto
     * @return
     */
    Worker login(WorkerLoginDTO dto);

    /**
     * 用户注册
     * @param dto
     * @return
     */
    int register(WorkerRegDTO dto);
}
