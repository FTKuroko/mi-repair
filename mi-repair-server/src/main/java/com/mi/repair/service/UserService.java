package com.mi.repair.service;

import com.mi.repair.dto.UserLoginDTO;
import com.mi.repair.vo.UserLoginVO;
import com.mi.repair.vo.UserRegVO;
import com.mi.repair.dto.UserRegDTO;

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
    UserRegVO register(UserRegDTO dto);
}
