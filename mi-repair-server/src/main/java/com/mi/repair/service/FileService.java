package com.mi.repair.service;

import com.mi.repair.dto.FileDTO;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface FileService {
    /**
     * 上传文件
     * @param files
     * @param orderId
     */
    void saveFileInfo(List<MultipartFile> files, Long orderId) ;
    /**
     * 获取图片
     * @param dto
     * @return
     */
    List<byte[]> getFile(FileDTO dto, HttpServletResponse response) throws IOException;

    String batchUpload(MultipartFile[] files);

    String upload(MultipartFile file);
}
