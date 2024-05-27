package com.mi.repair.dto;

import lombok.Data;
/**
 * @author 罗慧
 */
@Data
public class MaterialReqPageQueryDTO {
    private Long id;

    private String materialName;

    private Integer status;
    private String statusInfo;

    private Integer page;

    private Integer pageSize;
}
