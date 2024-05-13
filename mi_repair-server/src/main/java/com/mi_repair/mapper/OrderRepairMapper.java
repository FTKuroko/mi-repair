package com.mi_repair.mapper;

import com.mi_repair.dto.OrderRepairSubmitDTO;
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
     * @param orderRepairSubmitDTO  维修单信息
     * @return
     */
    public int submit(OrderRepairSubmitDTO orderRepairSubmitDTO);
}
