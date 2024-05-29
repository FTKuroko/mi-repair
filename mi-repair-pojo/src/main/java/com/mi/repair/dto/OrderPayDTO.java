package com.mi.repair.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;


@Data
@ApiModel(description = "支付订单")
public class OrderPayDTO {
    private static final long serialVersionUID = 1L;

    private Long id;    //支付订单id

    private Long orderId; // 维修订单id

    private Long userId; // 用户 id

    private Long workerId; // 工程师 id

    private BigDecimal price; // 价格

    private Integer status; // 状态

}
