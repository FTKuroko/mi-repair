package com.mi.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 15:11
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRepairSubmitVO {
    // 维修单 id
    private Long id;

    // 下单时间
    private LocalDateTime orderTime;
}
