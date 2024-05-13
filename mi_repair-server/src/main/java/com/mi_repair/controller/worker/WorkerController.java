package com.mi_repair.controller.worker;

import com.mi_repair.entity.User;
import com.mi_repair.entity.Worker;
import com.mi_repair.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 17:07
 */
@RestController
@RequestMapping("/work")
@Slf4j
public class WorkerController {

    @PostMapping("/login")
    public Result<Worker> login(@RequestBody Worker worker){
        log.info("工程师登录:{}", worker);

        return Result.success(worker);
    }
}
