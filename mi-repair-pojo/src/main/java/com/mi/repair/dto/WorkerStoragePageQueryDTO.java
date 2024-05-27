package com.mi.repair.dto;

import lombok.Data;
/**
 * @author 罗慧
 */
@Data
public class WorkerStoragePageQueryDTO {
    private Long id;

    private String name;

    private Integer type;

    private Integer page;

    private Integer pageSize;
}
