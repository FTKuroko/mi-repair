package com.mi_repair.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/14 12:38
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RepairMaterialsVO {
    private Long id;
    private LocalDateTime createTime;
}
