package com.mi.repair.service;

import com.mi.repair.dto.OrderPayPageQueryDTO;
import com.mi.repair.result.PageResult;

/**
 * @author 罗慧
 * @description
 * @date 2024/5/13 15:07
 */
public interface OrderPayService {

    PageResult pageQuery(OrderPayPageQueryDTO pageQueryDTO);
}
