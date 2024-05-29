package com.mi.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private Long userId;
    private Long workerId;
    private BigDecimal price;
    private Integer status;
    private String statusInfo;
    private LocalDateTime createTime;
}
