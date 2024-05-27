package com.mi.repair.controller.user;

import com.mi.repair.dto.OrderPayPageQueryDTO;
import com.mi.repair.result.PageResult;
import com.mi.repair.result.Result;
import com.mi.repair.service.OrderPayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 罗慧
 */
@RestController
@Slf4j
@RequestMapping("/user/pay")
@Api(tags = "支付流水相关")
public class OrderPayController {
    @Autowired
    private OrderPayService orderPayService;

    @PostMapping("/page")
    @ApiOperation("用户查询支付流水")
    public Result<PageResult> pageQuery(@RequestBody OrderPayPageQueryDTO pageQueryDTO){
        log.info("用户订单分页查询:{}", pageQueryDTO);
        PageResult result = orderPayService.pageQuery(pageQueryDTO);
        return Result.success(result);
    }
}
