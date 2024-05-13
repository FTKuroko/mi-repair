package com.mi_repair.controller.user;

import com.mi_repair.dto.OrderRepairSubmitDTO;
import com.mi_repair.entity.OrderRepair;
import com.mi_repair.result.Result;
import com.mi_repair.service.OrderRepairService;
import com.mi_repair.vo.OrderRepairSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/13 15:06
 */
@RestController
@RequestMapping("/user/orderRepair")
@Slf4j
@Api(tags = "用户维修单管理接口")
public class UserOrderRepairController {

    @Autowired
    private OrderRepairService orderRepairService;

    @PostMapping("/submit")
    @ApiOperation("用户下单")
    public Result<OrderRepairSubmitVO> submit(@RequestBody OrderRepairSubmitDTO orderRepairSubmitDTO){
        log.info("用户下单信息:{}", orderRepairSubmitDTO);
        OrderRepairSubmitVO orderRepairSubmitVO = orderRepairService.submitOrderRepair(orderRepairSubmitDTO);
        return Result.success(orderRepairSubmitVO);
    }
}
