package com.mi_repair.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 21:43
 */
@Data
public class WorkerLoginDTO implements Serializable {
    private String workerName;

    private String password;
}
