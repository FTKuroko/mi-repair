package com.mi.repair.mapper;

import com.github.pagehelper.Page;
import com.mi.repair.dto.OrderPayPageQueryDTO;
import com.mi.repair.entity.OrderPay;
import org.apache.ibatis.annotations.Mapper;

/**
 * auth  罗慧
 */
@Mapper
public interface OrderPayMapper {

    Page<OrderPay> pageQueryByUserId(OrderPayPageQueryDTO pageQueryDTO);
}
