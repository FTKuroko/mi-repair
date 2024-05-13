package com.mi_repair.service.impl;

import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.entity.OrderRepair;
import com.mi_repair.mapper.OrderRepairMapper;
import com.mi_repair.service.OrderRepairService;
import com.mi_repair.vo.OrderRepairSubmitVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

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
        OrderRepair orderRepair = new OrderRepair();
        BeanUtils.copyProperties(orderRepairSubmitDTO, orderRepair);
        orderRepair.setStatus(0);
        orderRepair.setCreateTime(LocalDateTime.now());
        orderRepair.setUpdateTime(LocalDateTime.now());
        orderRepairMapper.submit(orderRepair);
        return null;
    }
}
