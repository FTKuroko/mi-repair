package com.mi_repair.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 14:03
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class File implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private Integer type;

    private Long orderId;

    private String path;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
