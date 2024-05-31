package com.mi.repair.controller.pay;

import com.alibaba.fastjson.JSONObject;
import com.mi.repair.dto.OrderPayDTO;
import com.mi.repair.entity.OrderPay;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.mapper.OrderRepairMapper;
import com.mi.repair.service.OrderPayService;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.utils.HttpClientUtil;
import com.mi.repair.utils.PayUtil;
import com.mi.repair.entity.AliPayPojo;
import com.mi.repair.utils.StateMachineUtil;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/aliPay")
public class AliPayController {

    @Autowired
    private StateMachineUtil stateMachineUtil;
    @Autowired
    private PayUtil payUtil;
    @Autowired
    private OrderPayService orderPayService;

    @Autowired
    private OrderRepairMapper orderRepairMapper;




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
        httpServletResponse.getWriter().flush();
        httpServletResponse.getWriter().close();

        String outTradeNo = aliPayPojo.getWIDout_trade_no();
        String tradeStatus = "TRADE_SUCCESS";
        String subject = aliPayPojo.getWIDsubject();
//        //支付宝流水
        String tradeNo = UUID.randomUUID().toString();
        log.info("trade_status>>" + tradeStatus + ">>>>trade_no" + tradeNo + ">>>out_trade_no" + outTradeNo);
        if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
            //支付操作成功
            OrderPay orderPay = orderPayService.selectPayOrderById(Long.valueOf(outTradeNo));
            Long orderId = orderPay.getOrderId();
            stateMachineUtil.saveAndSendEvent(orderId, RepairOrderEvent.USER_PAY);
            orderRepairMapper.updateStatusById(orderId, RepairOrderStatus.PAYED.getCode());
            //TODO: 数据库是否添加支付宝流水Id
            orderPayService.updateByUserPay(Long.valueOf(outTradeNo));
        }
    }

//    @GetMapping("/payTest")
//    @ApiOperation("支付测试")
//    public void payTest(HttpServletResponse httpServletResponse) throws IOException {
//        AliPayPojo aliPayPojo = new AliPayPojo();
//        aliPayPojo.setWIDout_trade_no(String.valueOf(3));
//        aliPayPojo.setWIDtotal_amount(String.valueOf(200));
//        aliPayPojo.setWIDsubject("维修订单支付");
//        aliPayPojo.setWIDbody(null);
//        String result = payUtil.aliPay(aliPayPojo);
//
//        httpServletResponse.setContentType("text/html;charset=" + PayUtil.CHARSET);
//        httpServletResponse.getWriter().write(result);
//        httpServletResponse.getWriter().flush();;
//        httpServletResponse.getWriter().close();
//    }


    @PostMapping("/aliPay")
    @ApiOperation("用户支付")
    public void aliPay(@RequestBody OrderPayDTO orderPayDTO, HttpServletResponse httpServletResponse) throws IOException {
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
    public void notify(HttpServletRequest request) {
        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i] : valueStr + values[i] + ",";
            }
            params.put(name, valueStr);
        }
        requestParams.get("trade_status");
        //订单号
        String outTradeNo = request.getParameter("out_trade_no");
        String tradeStatus = request.getParameter("trade_status");
        String subject = request.getParameter("subject");
        String sellerId = request.getParameter("seller_id");
        //支付宝流水
        String tradeNo = request.getParameter("trade_no");
        log.info("trade_status>>" + tradeStatus + ">>>>trade_no" + tradeNo + ">>>out_trade_no" + outTradeNo);
        if ("TRADE_FINISHED".equals(tradeStatus) || "TRADE_SUCCESS".equals(tradeStatus)) {
            //支付操作成功
            OrderPay orderPay = orderPayService.selectPayOrderById(Long.valueOf(outTradeNo));
            Long orderId = orderPay.getOrderId();
            stateMachineUtil.saveAndSendEvent(orderId, RepairOrderEvent.USER_PAY);
            orderRepairMapper.updateStatusById(orderId, RepairOrderStatus.PAYED.getCode());
            //TODO: 数据库是否添加支付宝流水Id
            orderPayService.updateByUserPay(Long.valueOf(outTradeNo));
        }
    }
}
