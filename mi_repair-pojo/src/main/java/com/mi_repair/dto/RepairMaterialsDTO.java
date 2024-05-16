package com.mi_repair.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/14 12:36
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairMaterialsDTO {
    private Long id;
    private Long orderId;
    private String workerName;
    private String materialName;
    private int materialAmount;
}
