package com.mi.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author 罗慧
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialReqVO {
    private Long id;
    private LocalDateTime createTime;
    private Long orderId;
    private String materialName;
    private Integer materialAmount;
    private Integer status;
    private String statusInfo;
    private Double priceSum;//总价格
}
