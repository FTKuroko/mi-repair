package com.mi_repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:49
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Storage {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer status;

    private String name;

    private BigDecimal price;

    private Integer amount;
}
