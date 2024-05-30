package com.mi.repair.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.mi.repair.config.MinioConfig;
import com.mi.repair.dto.FileDTO;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.mapper.FileMapper;
import com.mi.repair.mapper.OrderRepairMapper;
import com.mi.repair.service.FileService;
import com.mi.repair.entity.File;
import com.mi.repair.enums.FileStatus;
import com.mi.repair.utils.StateMachineUtil;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 罗慧
 */
@Service
@Slf4j
public class FileServiceImpl implements FileService {

    @Value("${file.upload}")
    private  String UPLOAD_DIR;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private OrderRepairMapper orderRepairMapper;
    @Autowired
    private StateMachineUtil stateMachineUtil;
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private MinioClient minioClient;
    @Value("${mi-repair.minio.bucket.files}")
    private String bucket_files;

    @Override
    public void saveFileInfo(List<MultipartFile> files, Long orderId){
        String  uploadDir = UPLOAD_DIR + orderId;
        for (MultipartFile file : files) {
            // 验证文件类型
            try {
                String contentType = file.getContentType();
                if (contentType.startsWith("image/") || contentType.startsWith("video/")) {
                    // 生成唯一文件名
                    String name = IdUtil.simpleUUID();
                    String fileName = name +"."+ FileUtil.extName(file.getOriginalFilename());
                    //将文件和数据库连接
                    File entity = new File();
                    entity.setOrderId(orderId);
                    entity.setPath(fileName);
                    if(contentType.startsWith("image/")){
                        entity.setType(FileStatus.IMAGE.getCode());
                    }else{
                        entity.setType(FileStatus.VIDEO.getCode());
                    }
                    fileMapper.saveFileInfos(entity);
                    // 拼接完整文件路径
                    String filePath = uploadDir + java.io.File.separator + fileName;
                    // 创建文件
                    java.io.File dest = new java.io.File(filePath);
                    // 确保上传目录存在
                    java.io.File parent = dest.getParentFile();
                    FileUtil.mkdir(parent);
                    // 保存文件
                    FileUtil.writeFromStream(file.getInputStream(), dest);
                }
            }catch (IOException e) {
                log.debug("图片上传失败："+file.getName());
            }
        }
    }

    @Override
    public List<byte[]> getFile(FileDTO dto, HttpServletResponse response) throws IOException {
        ArrayList<byte[]> files = new ArrayList<>();
        List<File> images = fileMapper.getFiles(dto);
        //文件路径
        String filePath = UPLOAD_DIR + dto.getOrderId();
        //获取相对于的文件
        for (File f : images) {
            String fileName = f.getPath();
            Path path = Paths.get(filePath,fileName);
            java.io.File file = path.toFile();
            if (!file.exists()) {
                log.debug("文件："+fileName+"找不到了！");
                continue;
            }
            if(dto.getType() == FileStatus.IMAGE.getCode()){
                byte[] bytes =FileUtil.readBytes(file);
                files.add(bytes);
            }else{
                //如果是视频的话，先压缩，再上传
                String zipPath = filePath + java.io.File.separator + f.getId()+".zip";
                String srcPath = filePath + java.io.File.separator + fileName;
                java.io.File zipFile = new java.io.File(zipPath);
                // 检查ZIP文件是否存在
                if (!zipFile.exists()) {
                    // 使用Hutool创建ZIP文件
                    ZipUtil.zip(srcPath, zipPath);
                }
                // 设置响应头
                FileUtil.writeToStream(zipFile,response.getOutputStream());
            }
        }
        return files;
    }

    /**
     * 使用 minio 实现图片批量上传
     * @param files
     */
    @Override
    public String batchUpload(MultipartFile[] files, Long orderId) {
        StringBuilder sb = new StringBuilder();
        try{
            for(MultipartFile file : files){
                upload(file, orderId);
            }
            //
            stateMachineUtil.saveAndSendEvent(orderId, RepairOrderEvent.UPLOAD_PICTURES);
            // 上传成功之后，修改order状态
            orderRepairMapper.updateStatusById(orderId, RepairOrderStatus.APPLICATION_MATERIALS.getCode());
        }catch (Exception e){
            log.error("文件上传异常:" + e.getMessage());
        }
        return sb.toString();
    }

    /**
     * 使用 minio 上传单张图片
     * @param file
     * @return
     */
    public String upload(MultipartFile file, Long orderId){
        try {
            // 判断桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket_files).build());
            if(!bucketExists){
                // 创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket_files).build());
            }
            log.info("文件开始上传:{}", file);
            // 获取文件的真实名称
            String originalFilename = file.getOriginalFilename();
            // 文件扩展名
            String extendName = originalFilename.substring(originalFilename.lastIndexOf("."));
            // 构建文件上传相关信息
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket_files)
                    .object(originalFilename)
                    .stream(file.getInputStream(), file.getSize(), -1)
                    .contentType(file.getContentType())
                    .build();
            // 将文件上传到 minio 服务器
            minioClient.putObject(args);
            // 拼接文件上传地址，存入数据库
            String url = minioConfig.getEndpoint() + "/" + bucket_files + "/" + originalFilename;
            log.info("文件上传成功:{}， url:{}", originalFilename, url);
            // TODO: 将文件上传路径以及绑定的维修单 id 上传到数据库文件表中
            File f = new File();
            LocalDateTime now = LocalDateTime.now();
            f.setType(1);
            f.setPath(url);
            f.setCreateTime(now);
            f.setUpdateTime(now);
            f.setOrderId(orderId);
            fileMapper.saveFile(f);
        }catch (Exception e){
            log.error("文件上传异常:" + e.getMessage());
        }
        return null;
    }
}
