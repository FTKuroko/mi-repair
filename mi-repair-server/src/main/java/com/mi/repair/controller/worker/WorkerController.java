package com.mi.repair.controller.worker;

import com.mi.repair.dto.WorkerLoginDTO;
import com.mi.repair.vo.WorkerLoginVO;
import com.mi.repair.dto.WorkerRegDTO;
import com.mi.repair.result.Result;
import com.mi.repair.service.WorkerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 17:07
 */
@RestController
@RequestMapping("/worker")
@Slf4j
public class WorkerController {

    @Autowired
    private WorkerService workerService;

    @PostMapping("/login")
    public Result<WorkerLoginVO> login(@RequestBody WorkerLoginDTO worker){
        log.info("工程师登录:{}", worker);
        WorkerLoginVO login = workerService.login(worker);
        return Result.success(login);
    }

    @PostMapping("/reg")
    public Result<WorkerLoginVO> register(@RequestBody WorkerRegDTO dto){
        log.info("用户登录:{}", dto);
        WorkerLoginVO user = workerService.register(dto);
        return Result.success(user);
    }
}
