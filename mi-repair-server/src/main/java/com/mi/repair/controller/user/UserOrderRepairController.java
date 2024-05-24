package com.mi.repair.controller.user;

import com.mi.repair.dto.OrderRepairSubmitDTO;
import com.mi.repair.dto.UserOrderPageQueryDTO;
import com.mi.repair.result.PageResult;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.result.Result;
import com.mi.repair.vo.OrderRepairSubmitVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/check")
    @ApiOperation("用户维修单确认")
    public Result<String> confirm(@RequestParam("orderId") Long orderId){
        log.info("用户确认维修单信息:维修单id{}", orderId);
        return orderRepairService.confirm(orderId) > 0 ? Result.success("用户已确认") :
                Result.error("用户确认失败");
    }

    @PostMapping("/page")
    @ApiOperation("用户查询订单")
    public Result<PageResult> pageQuery(@RequestBody UserOrderPageQueryDTO userOrderPageQueryDTO){
        log.info("用户订单分页查询:{}", userOrderPageQueryDTO);
        PageResult result = orderRepairService.pageQuery(userOrderPageQueryDTO);
        return Result.success(result);
    }

    @DeleteMapping("/cancel")
    @ApiOperation("用户取消维修单")
    public Result<String> delete(@RequestParam("orderId") Long orderId){
        log.info("用户取消维修单:{]", orderId);
        return orderRepairService.delete(orderId) > 0 ? Result.success("用户已取消维修单") :
                Result.error("用户取消维修单失败");
    }

}
