package com.mi.repair.service.impl;

import com.mi.repair.constant.JwtClaimsConstant;
import com.mi.repair.constant.MessageConstant;
import com.mi.repair.dto.WorkerLoginDTO;
import com.mi.repair.dto.WorkerRegDTO;
import com.mi.repair.entity.Worker;
import com.mi.repair.exception.AccountNotFoundException;
import com.mi.repair.mapper.WorkerMapper;
import com.mi.repair.properties.JwtProperties;
import com.mi.repair.service.WorkerService;
import com.mi.repair.utils.JwtUtil;
import com.mi.repair.vo.WorkerLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 罗慧
 */
@Service
public class WorkerServiceImpl implements WorkerService {
    @Autowired
    private WorkerMapper workerMapper;
    @Autowired
    private JwtProperties jwtProperties;
    @Override
    public WorkerLoginVO login(WorkerLoginDTO dto) {
        Worker worker = new Worker();
        BeanUtils.copyProperties(dto, worker);
        Worker login = workerMapper.login(worker);
        if(login == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        if(!login.getPassword().equals(dto.getPassword())){
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.WORKER_ID, login.getId());
        String token = JwtUtil.createJWT(jwtProperties.getWorkerSecretKey(), jwtProperties.getWorkerTtl(), claims);
        WorkerLoginVO vo = WorkerLoginVO.builder()
                .id(login.getId())
                .workerName(login.getWorkerName())
                .phone(login.getPhone())
                .token(token)
                .build();
        vo.setRole(1);
        return vo;
    }

    @Override
    public WorkerLoginVO register(WorkerRegDTO dto) {
        Worker worker = new Worker();
        BeanUtils.copyProperties(dto, worker);
        workerMapper.register(worker);
        WorkerLoginVO vo = new WorkerLoginVO();
        vo.setWorkerName(dto.getName());
        return dto.getId()==null?null:vo;
    }
}
