package com.mi.repair.controller.pay;

import com.mi.repair.dto.OrderPayDTO;
import com.mi.repair.utils.PayUtil;
import com.mi.repair.entity.AliPayPojo;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/aliPay")
public class AliPayController {

    @Autowired
    private PayUtil payUtil;

    @PostMapping("/pay")
    @ApiOperation("用户支付")
    public void pay(@RequestBody OrderPayDTO orderPayDTO, HttpServletResponse httpServletResponse) throws IOException {
        AliPayPojo aliPayPojo = new AliPayPojo();
        aliPayPojo.setWIDout_trade_no(String.valueOf(orderPayDTO.getId()));
        aliPayPojo.setWIDtotal_amount(String.valueOf(orderPayDTO.getPrice()));
        aliPayPojo.setWIDsubject("维修订单支付");
        aliPayPojo.setWIDbody(null);

        String result = payUtil.aliPay(aliPayPojo);

        httpServletResponse.setContentType("text/html;charset=" + PayUtil.CHARSET);
        httpServletResponse.getWriter().write(result);
        httpServletResponse.getWriter().flush();;
        httpServletResponse.getWriter().close();
    }


    @PostMapping("/notify")
    @ApiOperation("支付回调")
    public void notifyed() {
        //TODO : 支付回调方法


    }
}
