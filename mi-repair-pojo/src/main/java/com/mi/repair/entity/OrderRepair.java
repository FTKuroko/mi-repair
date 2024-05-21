package com.mi.repair.entity;

import com.mi.repair.enums.RepairOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:52
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRepair implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String userName;

    private String userPhone;

    private String userAddr;

    private String goodsInfo;

    private String sn;

    private String desc;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    private RepairOrderStatus repairOrderStatus;
}
