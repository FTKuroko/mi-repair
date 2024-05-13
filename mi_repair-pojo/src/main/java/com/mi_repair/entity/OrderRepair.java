package com.mi_repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class OrderRepair {

    private static final long serialVersionUID = 1L;

    private Long id;

    private Long userId;

    private String userName;

    private String userNumber;

    private String userAddr;

    private String goodsInfo;

    private String sn;

    private String desc;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
