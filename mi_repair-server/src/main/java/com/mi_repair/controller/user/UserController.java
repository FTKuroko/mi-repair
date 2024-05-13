package com.mi_repair.controller.user;

import com.mi_repair.entity.User;
import com.mi_repair.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:43
 */
@RestController
@Slf4j
@RequestMapping("/user")
@Api(tags = "用户端相关接口")
public class UserController {

    /**
     * 用户登录
     * @param user
     * @return
     */
    @PostMapping("/login")
    public Result<User> login(@RequestBody User user){
        log.info("用户登录:{}", user);

        return Result.success(user);
    }

    /**
     * 用户下单
     * @return
     */


    @GetMapping("/hello")
    public String hello(){
        log.info("hello");
        return "hello";
    }
}
