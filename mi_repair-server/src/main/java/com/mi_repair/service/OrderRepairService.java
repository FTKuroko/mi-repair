package com.mi_repair.service;

import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.vo.OrderRepairSubmitVO;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 15:07
 */
public interface OrderRepairService {
    /**
     * 用户下单
     * @param orderRepairSubmitDTO  维修单信息
     * @return
     */
    OrderRepairSubmitVO submitOrderRepair(OrderRepairSubmitDTO orderRepairSubmitDTO);
}
