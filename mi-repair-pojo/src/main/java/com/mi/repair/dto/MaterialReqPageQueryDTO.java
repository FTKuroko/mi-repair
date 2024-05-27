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
    private String statusInfo;

    private Integer page;

    private Integer pageSize;
}
