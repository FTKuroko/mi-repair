package com.mi_repair.controller.user;

import com.mi_repair.dto.UserLoginDTO;
import com.mi_repair.dto.UserRegDTO;
import com.mi_repair.entity.User;
import com.mi_repair.result.Result;
import com.mi_repair.service.UserService;
import com.mi_repair.vo.UserLoginVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private UserService userService;
    /**
     * 用户登录
     * @param dto
     * @return
     */
    @PostMapping("/login")
    public Result<UserLoginVO> login(@RequestBody UserLoginDTO dto){
        log.info("用户登录:{}", dto);
        UserLoginVO user = userService.login(dto);
        return Result.success(user);
    }

    @PostMapping("/reg")
    public Result<UserLoginVO> register(@RequestBody UserRegDTO dto){
        log.info("用户登录:{}", dto);
        UserLoginVO user = userService.register(dto);
        return Result.success(user);
    }

}
