package com.mi.repair.service;

import com.mi.repair.dto.WorkerLoginDTO;
import com.mi.repair.dto.WorkerRegDTO;
import com.mi.repair.vo.WorkerLoginVO;

public interface WorkerService {

    /**
     * 用户登录
     * @param dto
     */
    WorkerLoginVO login(WorkerLoginDTO dto);

    /**
     * 用户注册
     * @param dto
     * @return
     */
    WorkerLoginVO register(WorkerRegDTO dto);
}
