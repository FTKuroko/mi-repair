package com.mi.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author 罗慧
 * @description
 * @date 2024/5/29 14:38
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String userName;
    private Integer status;
    private String statusInfo;
    private Integer type;
    private LocalDateTime createTime;
}
