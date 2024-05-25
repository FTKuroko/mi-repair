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

import java.util.List;

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
    public Result<List<RepairMaterialsVO>> applyMaterials(@RequestBody List<RepairMaterialsDTO> list){
        log.info("工程师申请维修材料:{}", list);
        List<RepairMaterialsVO> materialsVO = orderRepairService.applyMaterials(list);
        return Result.success(materialsVO);
    }
}
