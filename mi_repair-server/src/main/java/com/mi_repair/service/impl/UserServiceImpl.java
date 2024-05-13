package com.mi_repair.service.impl;

import com.mi_repair.dto.UserLoginDTO;
import com.mi_repair.dto.UserRegDTO;
import com.mi_repair.entity.User;
import com.mi_repair.mapper.UserMapper;
import com.mi_repair.service.UserService;
import com.mi_repair.vo.UserLoginVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 14:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        User login = userMapper.login(userLoginDTO);
        UserLoginVO vo = new UserLoginVO();
        if(login!=null) vo.setName(login.getName());
        return vo;
    }

    @Override
    public UserLoginVO register(UserRegDTO dto) {
        userMapper.register(dto);
        UserLoginVO vo = new UserLoginVO();
        vo.setName(dto.getName());
        return dto.getId()==null?null:vo;
    }
}
