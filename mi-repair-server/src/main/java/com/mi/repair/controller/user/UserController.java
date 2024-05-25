package com.mi.repair.controller.user;

import com.mi.repair.context.BaseContext;
import com.mi.repair.dto.UserLoginDTO;
import com.mi.repair.vo.UserLoginVO;
import com.mi.repair.vo.UserRegVO;
import com.mi.repair.dto.UserRegDTO;
import com.mi.repair.result.Result;
import com.mi.repair.service.UserService;
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
    public Result<UserRegVO> register(@RequestBody UserRegDTO dto){
        log.info("用户注册:{}", dto);
        UserRegVO user = userService.register(dto);
        return Result.success(user);
    }

    @PutMapping("/logout")
    public Result<String> logOut(){
        Long userId = BaseContext.getCurrentId();
        log.info("用户登出:{}",userId);
        BaseContext.removeCurrentId();
        return Result.success("用户登出");
    }

}
