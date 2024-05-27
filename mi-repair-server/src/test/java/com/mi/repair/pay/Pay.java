package com.mi.repair.pay;

import com.mi.repair.config.AlipayConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@SpringBootTest
public class Pay {
    @Autowired
    AlipayConfig alipayConfig;

    @Test
    public void test() {
        System.out.println(alipayConfig.appId);
        System.out.println(alipayConfig.aliPayPublicKey);
        System.out.println(alipayConfig.appPrivateKey);
        System.out.println(alipayConfig.notifyUrl);
        System.out.println(alipayConfig.returnUrl);
        System.out.println(alipayConfig.gatewayUrl);
    }
}
