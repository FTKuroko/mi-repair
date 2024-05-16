package com.mi_repair.service;

import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.dto.UserOrderPageQueryDTO;
import com.mi_repair.result.PageResult;
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

    /**
     * 用户确认维修单
     * @param orderId
     * @return
     */
    int confirm(Long orderId);

    /**
     * 用户维修单分页查询
     * @param userOrderPageQueryDTO
     * @return
     */
    PageResult pageQuery(UserOrderPageQueryDTO userOrderPageQueryDTO);

    /**
     * 用户取消维修单
     * @param orderId
     * @return
     */
    int delete(Long orderId);
}
