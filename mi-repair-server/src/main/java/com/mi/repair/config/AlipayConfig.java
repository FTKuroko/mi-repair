package com.mi.repair.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径
 */
@Configuration
public class AlipayConfig {
    //APPID
    @Value("${aliPay.appId}")
    public String appId;
    //应用私钥
    @Value("${aliPay.appPrivateKey}")
    public String appPrivateKey;
    //支付宝公钥
    @Value("${aliPay.aliPayPublicKey}")
    public String aliPayPublicKey;
    //支付完成回调地址
    @Value("${aliPay.notifyUrl}")
    public String notifyUrl;
    //支付完成跳转页面
    @Value("${aliPay.returnUrl}")
    public String returnUrl;
    //支付宝网关
    @Value("${aliPay.gatewayUrl}")
    public String gatewayUrl;
}

