package com.mi.repair.serviceTest.orderPayTest;

import com.mi.repair.dto.OrderPayDTO;
import com.mi.repair.entity.MaterialReq;
import com.mi.repair.entity.OrderPay;
import com.mi.repair.entity.OrderRepair;
import com.mi.repair.enums.OrderPayStatus;
import com.mi.repair.mapper.MaterialReqMapper;
import com.mi.repair.mapper.OrderPayMapper;
import com.mi.repair.mapper.OrderRepairMapper;
import com.mi.repair.service.OrderPayService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class orderPayTest {

    @Autowired
    private OrderPayService orderPayService;
    @Autowired
    private OrderRepairMapper orderRepairMapper;
    @Autowired
    private MaterialReqMapper materialReqMapper;
    @Autowired
    private OrderPayMapper orderPayMapper;
    @Test
    public void test() {
        String outTradeNo = "3";
        Long id = Long.valueOf(outTradeNo);
        OrderPay orderPay = orderPayService.selectPayOrderById(id);
        System.out.println(orderPay);
    }

    @Test
    public void createPayOrder(){
        Long orderId = 22L;
        List<MaterialReq> materialReqs = materialReqMapper.selectByOrderId(orderId);
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        OrderPayDTO orderPayDTO = new OrderPayDTO();
        orderPayDTO.setOrderId(orderId);
        orderPayDTO.setUserId(orderRepair.getUserId());
        orderPayDTO.setWorkerId(orderRepair.getWorkerId());
        BigDecimal price = new BigDecimal(0);
        for(MaterialReq materialReq : materialReqs){
            price = price.add(materialReq.getPriceSum());
        }
        orderPayDTO.setPrice(price);
        orderPayDTO.setStatus(OrderPayStatus.NO.getCode());
        LocalDateTime time = LocalDateTime.now();
        OrderPay orderPay = new OrderPay();
        BeanUtils.copyProperties(orderPayDTO, orderPay);
        orderPay.setCreateTime(time);
        orderPay.setUpdateTime(time);
        orderPayMapper.insert(orderPay);
    }
}
