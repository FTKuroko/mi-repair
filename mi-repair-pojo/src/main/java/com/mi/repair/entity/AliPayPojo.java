package com.mi.repair.entity;


import lombok.Data;

@Data
public class AliPayPojo {
    //商户订单号，商户网站订单系统中唯一订单号，必填
    private String WIDout_trade_no;
    //付款金额，必填
    private String WIDtotal_amount;
    //订单名称，必填
    private String WIDsubject;
    //商品描述，可空
    private String WIDbody;
}
