package com.mi.repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:58
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MaterialReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Long orderId;

    private Long workerId;

    private String workerName;

    private Long materialId;

    private String materialName;

    private Integer materialAmount;

    private Integer status;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
