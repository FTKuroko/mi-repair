package com.mi_repair.controller.worker;

import com.mi_repair.dto.RepairMaterialsDTO;
import com.mi_repair.result.Result;
import com.mi_repair.service.OrderRepairService;
import com.mi_repair.vo.RepairMaterialsVO;
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
