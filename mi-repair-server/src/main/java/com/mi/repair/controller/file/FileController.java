package com.mi.repair.controller.file;

import com.mi.repair.dto.FileDTO;
import com.mi.repair.service.FileService;
import com.mi.repair.result.Result;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @author 罗慧
 */
@RestController
@Slf4j
@RequestMapping("/file")
@Api(tags = "文件操作")
public class FileController {

    @Autowired
    private FileService fileService;

    @GetMapping("/get")
    public Result<List<byte[]>> getFileByOrderId(@RequestBody FileDTO dto, HttpServletResponse response){
        try {
            List<byte[]> files = fileService.getFile(dto,response);
            return Result.success(files);
        }catch (Exception e){
            return Result.error(null);
        }
    }

    @PostMapping("/upload")
    public Result<String> uploadFiles(@RequestParam("file") List<MultipartFile> files,@RequestParam("orderId") Long orderId) {
        if (files.isEmpty() || orderId ==null) {
            return Result.error("文件不能为空");
        }
        try {
            fileService.saveFileInfo(files,orderId);
        }catch (Exception e){
            return Result.error("上传失败");
        }
        return Result.success("上传成功");
    }

    /**
     * 图片文件批量上传至 minio
     * @param files
     * @return
     * @throws Exception
     */
    @PostMapping("/batchUpload")
    public Result<String> batchUpload(@RequestParam(name = "files") MultipartFile[] files) throws Exception{
        fileService.batchUpload(files);
        return Result.success("文件上传成功");
    }

    /**、
     * 单张图片上传至 minio
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    public Result<String> upload(@RequestParam(name = "file") MultipartFile file){
        fileService.upload(file);
        return Result.success("文件上传成功");
    }

}
