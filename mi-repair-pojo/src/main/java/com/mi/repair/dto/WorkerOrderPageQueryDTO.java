package com.mi.repair.dto;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/14 11:17
 */
@Data
public class WorkerOrderPageQueryDTO {
    private Long id;

    private Long workerId;

    private LocalDateTime startTime;

    private LocalDateTime endTime;

    private Integer status;

    private Integer page;

    private Integer pageSize;
}
