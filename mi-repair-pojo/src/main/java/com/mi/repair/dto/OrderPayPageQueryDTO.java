package com.mi.repair.dto;

import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:49
 */
@Data
@ApiModel(description = "存储")
public class OrderPayPageQueryDTO implements Serializable {

    private Long id;
    private Long userId;
    private Integer status;
    private Integer page;
    private Integer pageSize;
}
