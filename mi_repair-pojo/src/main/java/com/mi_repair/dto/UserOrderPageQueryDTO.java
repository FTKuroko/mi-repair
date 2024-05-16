package com.mi_repair.dto;

import lombok.Data;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/15 10:08
 */
@Data
public class UserOrderPageQueryDTO {
    private Long userId;
    private int page;
    private int pageSize;
}
