package com.mi_repair.service.impl;

import com.mi_repair.constant.JwtClaimsConstant;
import com.mi_repair.constant.MessageConstant;
import com.mi_repair.dto.UserLoginDTO;
import com.mi_repair.dto.UserRegDTO;
import com.mi_repair.entity.User;
import com.mi_repair.exception.AccountNotFoundException;
import com.mi_repair.mapper.UserMapper;
import com.mi_repair.properties.JwtProperties;
import com.mi_repair.service.UserService;
import com.mi_repair.utils.JwtUtil;
import com.mi_repair.vo.UserLoginVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .userName(login.getName())
                .phone(login.getPhone())
                .token(token)
                .build();
        return vo;
    }

    @Override
    public UserLoginVO register(UserRegDTO dto) {
        User user = new User();
        BeanUtils.copyProperties(dto, user);
        userMapper.register(user);
        UserLoginVO vo = new UserLoginVO();
        vo.setUserName(dto.getUserName());
        return dto.getId()==null?null:vo;
    }
}
