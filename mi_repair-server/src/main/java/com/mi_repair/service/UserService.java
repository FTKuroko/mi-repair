package com.mi_repair.service;

import com.mi_repair.dto.UserLoginDTO;
import com.mi_repair.dto.UserRegDTO;
import com.mi_repair.entity.User;
import com.mi_repair.vo.UserLoginVO;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 14:09
 */
public interface UserService {
    /**
     * 用户登录
     * @param userLoginDTO
     */
    UserLoginVO login(UserLoginDTO userLoginDTO);

    /**
     * 用户注册
     * @param dto
     * @return
     */
    UserLoginVO register(UserRegDTO dto);
}
