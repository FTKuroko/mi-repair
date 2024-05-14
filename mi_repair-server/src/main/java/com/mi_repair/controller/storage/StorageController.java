package com.mi_repair.controller.storage;

import com.mi_repair.Req.Request;
import com.mi_repair.dto.StorageDTO;
import com.mi_repair.entity.Storage;
import com.mi_repair.result.Result;
import com.mi_repair.service.StorageService;
import com.mi_repair.vo.StorageVO;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
     * @param request
     * @return
     */
    @GetMapping("/get")
    public Result<List<StorageVO>> getStorageList(@RequestBody Request request){
        try{
            List<StorageVO> list = storageService.getStorageList(request);
            return Result.success(list);
        }catch (Exception e){
            return Result.error("");
        }
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
