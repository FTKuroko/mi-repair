package com.mi.repair.vo;

import com.mi.repair.enums.RepairOrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/21 19:28
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRepairVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;

    private String userName;

    private String userPhone;

    private String userAddr;

    private String goodsInfo;
    private String sn;
    private String desc;

    private int status;
    private String statusInfo;      // 将工单状态详细信息返回，不再是返回状态码
    private LocalDateTime createTime;
}
