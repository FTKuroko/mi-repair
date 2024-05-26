package com.mi.repair.controller.worker;

import com.mi.repair.dto.MaterialReqPageQueryDTO;
import com.mi.repair.dto.WorkerOrderPageQueryDTO;
import com.mi.repair.result.PageResult;
import com.mi.repair.result.Result;
import com.mi.repair.service.MaterialReqService;
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
@RequestMapping("/worker/material")
@Api(tags = "通用接口")
@Slf4j
public class MaterialReqController {
    @Autowired
    MaterialReqService materialReqService;

    @PostMapping("/page")
    @ApiOperation("工程师查询申请材料表")
    public Result<PageResult> page(@RequestBody MaterialReqPageQueryDTO queryDTO){
        log.info("工程师查询订单:{}", queryDTO);
        PageResult result = materialReqService.pageQuery(queryDTO);
        return Result.success(result);
    }
}
