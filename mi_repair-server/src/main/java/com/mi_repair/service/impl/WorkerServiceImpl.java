package com.mi_repair.service.impl;

import com.mi_repair.dto.WorkerLoginDTO;
import com.mi_repair.dto.WorkerRegDTO;
import com.mi_repair.entity.Worker;
import com.mi_repair.mapper.WorkerMapper;
import com.mi_repair.service.WorkerService;
import com.mi_repair.vo.WorkerLoginVO;
import com.sun.corba.se.spi.orbutil.threadpool.Work;
import org.springframework.beans.BeanUtils;
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
        Worker worker = new Worker();
        BeanUtils.copyProperties(dto, worker);
        Worker login = workerMapper.login(worker);
        WorkerLoginVO vo = new WorkerLoginVO();
        if(login!=null) vo.setName(login.getName());
        return login==null?null:vo;
    }

    @Override
    public WorkerLoginVO register(WorkerRegDTO dto) {
        Worker worker = new Worker();
        BeanUtils.copyProperties(dto, worker);
        workerMapper.register(worker);
        WorkerLoginVO vo = new WorkerLoginVO();
        vo.setName(dto.getName());
        return dto.getId()==null?null:vo;
    }
}
