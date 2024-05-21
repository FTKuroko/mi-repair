package com.mi.repair.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/21 14:09
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserRegVO {
    private long id;
    private String name;
    private String userName;
    private String phone;
    private String addr;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
}
