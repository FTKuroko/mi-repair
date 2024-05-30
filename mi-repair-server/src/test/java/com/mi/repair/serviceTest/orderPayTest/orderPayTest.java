package com.mi.repair.serviceTest.orderPayTest;

import com.mi.repair.entity.OrderPay;
import com.mi.repair.service.OrderPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class orderPayTest {

    @Autowired
    private OrderPayService orderPayService;
    @Test
    public void test() {
        String outTradeNo = "3";
        Long id = Long.valueOf(outTradeNo);
        OrderPay orderPay = orderPayService.selectPayOrderById(id);
        System.out.println(orderPay);
    }
}
