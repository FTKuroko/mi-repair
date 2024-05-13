package com.mi_repair.mapper;

import com.mi_repair.dto.UserLoginDTO;
import com.mi_repair.dto.UserRegDTO;
import com.mi_repair.entity.User;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 14:09
 */
@Mapper
public interface UserMapper {
    /**
     * 用户登录
     * @param user
     * @return
     */
    User login(UserLoginDTO user);

    /**
     * 用户注册
     * @param dto
     * @return
     */
    int register(UserRegDTO dto);
}
