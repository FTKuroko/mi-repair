package com.mi.repair.controller.file;

import com.mi.repair.dto.FileDTO;
import com.mi.repair.entity.File;
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
    @PostMapping("/{orderId}/batchUpload")
    public Result<String> batchUpload(@PathVariable Long orderId,
                                      @RequestParam(name = "files") MultipartFile[] files) throws Exception{
        fileService.batchUpload(files, orderId);
        return Result.success("文件上传成功");
    }

    /**、
     * 单张图片上传至 minio
     * @param file
     * @return
     */
    @PostMapping("/uploadImage")
    public Result<String> upload(@RequestParam(name = "file") MultipartFile file,@RequestParam("orderId") Long orderId){
        fileService.upload(file, orderId);
        return Result.success("文件上传成功");
    }

    @GetMapping("/{orderId}/preview")
    public Result<String> preview(@PathVariable Long orderId){
        List<File> preview = fileService.preview(orderId);
        log.info("图片文件:{}", preview);
        return Result.success("文件预览成功");
    }

    @PostMapping("/{orderId}/uploadVideo")
    public Result<String> uploadVideo(@RequestParam(name = "file") MultipartFile file, @PathVariable Long orderId){
        fileService.uploadVideo(file, orderId);
        return Result.success("视频上传成功");
    }
}
