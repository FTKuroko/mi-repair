package com.mi_repair.mapper;

import com.github.pagehelper.Page;
import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.dto.UserOrderPageQueryDTO;
import com.mi_repair.entity.OrderRepair;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 15:08
 */
@Mapper
public interface OrderRepairMapper {

    /**
     * 用户下维修单
     * @param orderRepair  维修单信息
     * @return  返回维修订单id
     */
    public long submit(OrderRepair orderRepair);

    /**
     * 用户确认订单
     * @param OrderId
     * @return
     */
    public int confirm(Long OrderId);

    /**
     * 用户维修订单查询
     * @param userOrderPageQueryDTO
     * @return
     */
    Page<OrderRepair> pageQueryByUserId(UserOrderPageQueryDTO userOrderPageQueryDTO);

    /**
     * 根据维修单id查找维修单
     * @param id
     * @return
     */
    OrderRepair selectById(Long id);

    int delete(Long orderId);
}
