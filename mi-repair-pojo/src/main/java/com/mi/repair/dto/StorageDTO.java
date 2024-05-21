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
public class StorageDTO implements Serializable {

    private Long id;

    private Integer type;

    private String name;

    private BigDecimal price;

    private Integer amount;
}
