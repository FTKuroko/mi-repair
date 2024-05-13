package com.mi_repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:38
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String password;

    private String phone;

    private String addr;
}
