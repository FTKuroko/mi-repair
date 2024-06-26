package com.mi.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
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
public class StorageVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String typeInfo;
    private Integer type;

    private String name;

    private BigDecimal price;

    private Integer amount;
}
