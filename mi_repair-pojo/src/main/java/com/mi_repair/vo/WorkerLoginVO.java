package com.mi_repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 21:43
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkerLoginVO implements Serializable {
    private long id;
    private String workerName;
    private String phone;
    private String token;
}
