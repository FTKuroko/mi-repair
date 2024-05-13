package com.mi_repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 13:48
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Worker implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String name;

    private String password;

    private String phone;
}
