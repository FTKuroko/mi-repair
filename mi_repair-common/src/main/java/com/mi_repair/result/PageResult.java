package com.mi_repair.result;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/15 10:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageResult implements Serializable {
    // 查询到的总记录数
    private long total;
    // 当前页数据集合
    private List records;
}
