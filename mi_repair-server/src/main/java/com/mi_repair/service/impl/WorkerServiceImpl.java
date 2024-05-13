package com.mi_repair.service.impl;

import com.mi_repair.dto.WorkerLoginDTO;
import com.mi_repair.dto.WorkerRegDTO;
import com.mi_repair.entity.Worker;
import com.mi_repair.mapper.WorkerMapper;
import com.mi_repair.service.WorkerService;
import com.mi_repair.vo.WorkerLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 罗慧
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;
    @Override
    public WorkerLoginVO login(WorkerLoginDTO dto) {
        Worker login = workerMapper.login(dto);
        WorkerLoginVO vo = new WorkerLoginVO();
        if(login!=null) vo.setName(login.getName());
        return login==null?null:vo;
    }

    @Override
    public WorkerLoginVO register(WorkerRegDTO dto) {
        workerMapper.register(dto);
        WorkerLoginVO vo = new WorkerLoginVO();
        vo.setName(dto.getName());
        return dto.getId()==null?null:vo;
    }
}
