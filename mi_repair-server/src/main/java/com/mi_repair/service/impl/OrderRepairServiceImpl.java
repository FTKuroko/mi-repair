package com.mi_repair.service.impl;

import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.mapper.OrderRepairMapper;
import com.mi_repair.service.OrderRepairService;
import com.mi_repair.vo.OrderRepairSubmitVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 15:08
 */
@Service
public class OrderRepairServiceImpl implements OrderRepairService {
    @Autowired
    private OrderRepairMapper orderRepairMapper;

    @Override
    public OrderRepairSubmitVO submitOrderRepair(OrderRepairSubmitDTO orderRepairSubmitDTO) {
        orderRepairMapper.submit(orderRepairSubmitDTO);
        return null;
    }
}
