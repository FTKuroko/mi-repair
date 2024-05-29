package com.mi.repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 罗慧
 * @description
 * @date 2024/5/29 14:33
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private Long orderId;
    private Long userId;

    private Integer status;
    private Integer type;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;
}
