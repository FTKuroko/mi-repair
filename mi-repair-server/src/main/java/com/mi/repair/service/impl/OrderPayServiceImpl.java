package com.mi.repair.service.impl;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.mi.repair.context.BaseContext;
import com.mi.repair.dto.OrderPayPageQueryDTO;
import com.mi.repair.entity.OrderPay;
import com.mi.repair.enums.OrderPayStatus;
import com.mi.repair.mapper.OrderPayMapper;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.OrderPayService;
import com.mi.repair.vo.OrderPayVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 罗慧
 */
@Service
public class OrderPayServiceImpl implements OrderPayService {
    @Autowired
    private OrderPayMapper orderPayMapper;
    @Override
    public PageResult pageQuery(OrderPayPageQueryDTO pageQueryDTO) {
        // 用户只能查询自己的支付账单
        Long id = BaseContext.getCurrentId();
        pageQueryDTO.setUserId(id);
        PageHelper.startPage(pageQueryDTO.getPage(), pageQueryDTO.getPageSize());
        Page<OrderPay> page = orderPayMapper.pageQueryByUserId(pageQueryDTO);
        long total = page.getTotal();
        List<OrderPay> result = page.getResult();
        List<OrderPayVO> pageInfo = new ArrayList<>(result.size());
        for(OrderPay entity : result){
            OrderPayVO orderPayVO = new OrderPayVO();
            BeanUtils.copyProperties(entity, orderPayVO);
            for(OrderPayStatus status : OrderPayStatus.values()){
                if(entity.getStatus().equals(status.getCode())){
                    orderPayVO.setStatusInfo(status.getName());
                }
            }
            pageInfo.add(orderPayVO);
        }
        return new PageResult(total, pageInfo);
    }

    public OrderPay selectPayOrderById(Long id) {
        return orderPayMapper.selectPayOrderById(id);
    }

    @Override
    public int updateByUserPay(Long id) {
        return orderPayMapper.updateByUserPay(id);
    }
}
