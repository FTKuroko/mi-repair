package com.mi.repair.service.impl;

import com.mi.repair.constant.JwtClaimsConstant;
import com.mi.repair.constant.MessageConstant;
import com.mi.repair.dto.UserLoginDTO;
import com.mi.repair.exception.AccountNotFoundException;
import com.mi.repair.mapper.UserMapper;
import com.mi.repair.properties.JwtProperties;
import com.mi.repair.service.UserService;
import com.mi.repair.utils.JwtUtil;
import com.mi.repair.vo.UserLoginVO;
import com.mi.repair.vo.UserRegVO;
import com.mi.repair.dto.UserRegDTO;
import com.mi.repair.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 14:10
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public UserLoginVO login(UserLoginDTO userLoginDTO) {
        User user = new User();
        BeanUtils.copyProperties(userLoginDTO, user);
        User login = userMapper.login(user);
        // 账号不存在
        if(login == null){
            throw new AccountNotFoundException(MessageConstant.ACCOUNT_NOT_FOUND);
        }
        // 账号存在，密码比对
        if(!userLoginDTO.getPassword().equals(login.getPassword())){
            throw new AccountNotFoundException(MessageConstant.PASSWORD_ERROR);
        }
        // 登录成功，生成jwt令牌
        Map<String, Object> claims = new HashMap<>();
        claims.put(JwtClaimsConstant.USER_ID, login.getId());
        String token = JwtUtil.createJWT(
                jwtProperties.getUserSecretKey(),
                jwtProperties.getUserTtl(),
                claims);
        UserLoginVO vo = UserLoginVO.builder()
                .id(login.getId())
                .userName(login.getUserName())
                .phone(login.getPhone())
                .token(token)
                .build();
        return vo;
    }

    @Override
    public UserRegVO register(UserRegDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        User login = userMapper.login(user);
        if(login != null){
            throw new AccountNotFoundException(MessageConstant.ALREADY_EXISTS);
        }
        userMapper.register(user);
        LocalDateTime time = LocalDateTime.now();
        UserRegVO vo = UserRegVO.builder()
                .id(user.getId())
                .name(user.getName())
                .userName(user.getUserName())
                .phone(user.getPhone())
                .addr(user.getAddr())
                .createTime(time)
                .updateTime(time)
                .build();
        return vo;
    }
}
