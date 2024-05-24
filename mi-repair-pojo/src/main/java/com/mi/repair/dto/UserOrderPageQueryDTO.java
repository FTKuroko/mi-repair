package com.mi.repair.dto;

import lombok.Data;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/15 10:08
 */
@Data
public class UserOrderPageQueryDTO {
    private Long userId;
    private Long id;
    private Integer status;
    private Integer page;
    private Integer pageSize;
}
