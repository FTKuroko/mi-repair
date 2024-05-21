package com.mi.repair.controller.worker;

import com.mi.repair.service.OrderRepairService;
import com.mi.repair.vo.RepairMaterialsVO;
import com.mi.repair.dto.RepairMaterialsDTO;
import com.mi.repair.result.Result;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/16 12:55
 */
@RestController
@Slf4j
@RequestMapping("/worker/repair")
public class WorkerRepairController {

    @Autowired
    OrderRepairService orderRepairService;

    @PostMapping("/materials")
    @ApiOperation("申请维修材料")
    public Result<RepairMaterialsVO> applyMaterials(@RequestBody RepairMaterialsDTO repairMaterialsDTO){
        log.info("工程师申请维修材料:{}", repairMaterialsDTO);
        RepairMaterialsVO materialsVO = orderRepairService.applyMaterials(repairMaterialsDTO);
        return Result.success(materialsVO);
    }
}
