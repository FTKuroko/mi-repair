package com.mi_repair.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 21:42
 */
@Data
public class UserRegDTO implements Serializable {

    private Long id;

    private String name;
    private String userName;

    private String password;

    private String phone;

    private String addr;
}
