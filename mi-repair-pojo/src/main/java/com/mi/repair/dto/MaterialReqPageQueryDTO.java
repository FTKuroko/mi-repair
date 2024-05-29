package com.mi.repair.dto;

import lombok.Data;
/**
 * @author 罗慧
 */
@Data
public class MaterialReqPageQueryDTO {
    private Long workerId;

    private String materialName;

    private Integer status;

    private Integer page;

    private Integer pageSize;
}
