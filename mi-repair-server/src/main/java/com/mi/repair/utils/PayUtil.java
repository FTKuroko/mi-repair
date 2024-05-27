package com.mi.repair.utils;

import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.mi.repair.config.AlipayConfig;
import com.mi.repair.entity.AliPayPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 沙箱支付工具类
 */
@Component
public class PayUtil {
    public static final String FORMAT =  "JSON";
    public static final String CHARSET =  "UTF-8";
    public static final String SIGN_TYPE =  "RSA2";

    private static AlipayConfig alipayConfig;
    @Autowired
    public void setAlipayConfig(AlipayConfig alipayConfig) {
        this.alipayConfig = alipayConfig;
    }


    public String aliPay(AliPayPojo aliPayPojo) {
        //获得初始化的AlipayClient
        /** 获得初始化的AlipayClient **/
        AlipayClient alipayClient = new DefaultAlipayClient(alipayConfig.gatewayUrl,
                alipayConfig.appId,
                alipayConfig.appPrivateKey,
                FORMAT,
                CHARSET,
                alipayConfig.aliPayPublicKey,
                SIGN_TYPE);

        //设置请求参数
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(alipayConfig.returnUrl);
        alipayRequest.setNotifyUrl(alipayConfig.notifyUrl);

        //商户订单号，商户网站订单系统中唯一订单号，必填
        String out_trade_no = aliPayPojo.getWIDout_trade_no();
        //付款金额，必填
        String total_amount = aliPayPojo.getWIDtotal_amount();
        //订单名称，必填
        String subject = aliPayPojo.getWIDsubject();
        //商品描述，可空
        String body = aliPayPojo.getWIDbody();

        alipayRequest.setBizContent("{\"out_trade_no\":\"" + aliPayPojo.getWIDout_trade_no() + "\","
                + "\"total_amount\":\"" + aliPayPojo.getWIDtotal_amount() + "\","
                + "\"subject\":\"" + aliPayPojo.getWIDsubject() + "\","
                + "\"body\":\"" + aliPayPojo.getWIDbody() + "\","
                + "\"product_code\":\"FAST_INSTANT_TRADE_PAY\"}");
        //请求
        String result = null;
        try {
            result = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
