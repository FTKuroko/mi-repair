package com.mi.repair.controller.pay;

import com.mi.repair.utils.PayUtil;
import com.mi.repair.entity.AliPayPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/aliPay")
public class AliPayController {

    @Autowired
    private PayUtil payUtil;

    @GetMapping("payTest")
    public void pay(HttpServletResponse httpServletResponse) throws IOException {
        AliPayPojo aliPayPojo = new AliPayPojo();
        //订单信息
        aliPayPojo.setWIDsubject("test");
        aliPayPojo.setWIDout_trade_no(String.valueOf(UUID.randomUUID()));
        aliPayPojo.setWIDtotal_amount("299");
        aliPayPojo.setWIDbody(null);

        String result = payUtil.aliPay(aliPayPojo);

        httpServletResponse.setContentType("text/html;charset=" + PayUtil.CHARSET);
        httpServletResponse.getWriter().write(result);
        httpServletResponse.getWriter().flush();;
        httpServletResponse.getWriter().close();
    }
}