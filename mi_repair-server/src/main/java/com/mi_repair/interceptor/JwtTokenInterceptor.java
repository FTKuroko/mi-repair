package com.mi_repair.interceptor;

import com.mi_repair.constant.JwtClaimsConstant;
import com.mi_repair.context.BaseContext;
import com.mi_repair.properties.JwtProperties;
import com.mi_repair.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/17 10:37
 */
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtProperties jwtProperties;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 判断当前拦截到的是Controller方法还是其他资源
        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        // 1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getUserTokenName());
        // 2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getUserSecretKey(), token);
            Long userId = Long.valueOf(claims.get(JwtClaimsConstant.USER_ID).toString());
            // 将用户id存储到ThreadLocal中
            BaseContext.setCurrentId(userId);
            // 3、通过放行
            return true;
        }catch (Exception e){
            response.setStatus(401);
            return false;
        }
    }
}
