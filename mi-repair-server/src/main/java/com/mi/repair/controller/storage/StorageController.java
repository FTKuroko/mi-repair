package com.mi.repair.controller.storage;

import com.mi.repair.dto.StorageDTO;
import com.mi.repair.dto.WorkerStoragePageQueryDTO;
import com.mi.repair.result.PageResult;
import com.mi.repair.result.Result;
import com.mi.repair.service.StorageService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
/**
 * @author 罗慧
 */
@RestController
@Slf4j
@RequestMapping("/storage")
@Api(tags = "存储材料相关")
public class StorageController {

    @Autowired
    private StorageService storageService;

    /**
     * 查询库存
     * @param queryDTO
     * @return
     */
    @PostMapping("/page")
    @ApiOperation("工程师查询库存")
    public Result<PageResult> getStorageList(@RequestBody WorkerStoragePageQueryDTO queryDTO){
        log.info("工程师查询库存:{}", queryDTO);
        PageResult list = storageService.getStorageList(queryDTO);
        return Result.success(list);
    }

    /**
     * 添加库存
     * @param dto
     * @return
     */
    @PostMapping("/add")
    public Result<String> addStorage(@RequestBody StorageDTO dto){
        try{
            storageService.addStorage(dto);
            return Result.success("添加成功");
        }catch (Exception e){
            return Result.error("添加失败");
        }
    }

    /**
     * 减库存
     * @param dto
     * @return
     */
    @PostMapping("/sub")
    public Result<String> subStorage(@RequestBody StorageDTO dto){
        try{
            storageService.subStorage(dto);
            return Result.success("成功");
        }catch (Exception e){
            return Result.error("添加失败");
        }
    }

    @PostMapping("/del")
    public Result<String> delStorage(@RequestBody StorageDTO dto){
        try{
            storageService.delStorage(dto);
            return Result.success("成功");
        }catch (Exception e){
            return Result.error("删除失败");
        }
    }


}
