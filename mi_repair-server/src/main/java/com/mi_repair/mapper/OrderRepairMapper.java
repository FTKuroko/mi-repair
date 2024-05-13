package com.mi_repair.mapper;

import com.mi_repair.dto.OrderRepairSubmitDTO;
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
     * @return
     */
    public int submit(OrderRepair orderRepair);
}
