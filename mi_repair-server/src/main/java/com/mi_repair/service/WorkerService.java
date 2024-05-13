package com.mi_repair.service;

import com.mi_repair.dto.UserLoginDTO;
import com.mi_repair.dto.UserRegDTO;
import com.mi_repair.dto.WorkerLoginDTO;
import com.mi_repair.dto.WorkerRegDTO;
import com.mi_repair.vo.UserLoginVO;
import com.mi_repair.vo.WorkerLoginVO;

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
