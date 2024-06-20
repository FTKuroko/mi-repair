package com.mi.repair.service.impl;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ZipUtil;
import com.alibaba.fastjson.JSON;
import com.mi.repair.config.MinioConfig;
import com.mi.repair.context.BaseContext;
import com.mi.repair.dto.FileDTO;
import com.mi.repair.entity.MaterialReq;
import com.mi.repair.entity.OrderRepair;
import com.mi.repair.entity.Schedule;
import com.mi.repair.enums.RepairOrderEvent;
import com.mi.repair.enums.RepairOrderStatus;
import com.mi.repair.mapper.FileMapper;
import com.mi.repair.mapper.MaterialReqMapper;
import com.mi.repair.mapper.OrderRepairMapper;
import com.mi.repair.service.FileService;
import com.mi.repair.entity.File;
import com.mi.repair.enums.FileStatus;
import com.mi.repair.service.OrderRepairService;
import com.mi.repair.service.ScheduleService;
import com.mi.repair.utils.FileCompressionUtil;
import com.mi.repair.utils.StateMachineUtil;
import com.mi.repair.webSocket.WebSocketServer;
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
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private OrderRepairService orderRepairService;
    @Autowired
    private ScheduleService scheduleService;
    @Autowired
    private StateMachineUtil stateMachineUtil;
    @Autowired
    private MinioConfig minioConfig;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private MinioClient minioClient;
    @Value("${mi-repair.minio.bucket.files}")
    private String bucket_files;
    @Value("${mi-repair.minio.bucket.videofiles}")
    private String bucket_videofiles;
    // 视频文件最大大小
    private static final long MAX_VIDE0_SIZE = 10 * 1024 * 1024;
    // 图片文件最大大小
    private static final long MAX_FILE_SIZE = 2 * 1024 * 1024;
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
        orderRepairMapper.updateStatusById(orderId, RepairOrderStatus.APPLICATION_MATERIALS.getCode());
        scheduleService.insertSchedule(orderId, RepairOrderStatus.APPLICATION_MATERIALS.getCode(), 1);
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

    @Override
    public List<File> preview(Long orderId) {
        List<File> files = fileMapper.preview(orderId);
        return files;
    }

    @Override
    public String uploadVideo(MultipartFile file, Long orderId) {

        try{
            // 判断桶是否存在
            boolean bucketExists = minioClient.bucketExists(BucketExistsArgs.builder().bucket(bucket_videofiles).build());
            if(!bucketExists){
                // 创建一个新的桶
                minioClient.makeBucket(MakeBucketArgs.builder().bucket(bucket_videofiles).build());
            }
            log.info("视频文件开始上传:{}", file);
//            // TODO: 暂时缺少对文件大小的判断,大于10MB的文件以压缩包的形式上传
//            java.io.File convertMultiPartToFile = convertMultiPartToFile(file);
//            if(convertMultiPartToFile.length() > MAX_VIDE0_SIZE){
//                convertMultiPartToFile = FileCompressionUtil.compressFile(convertMultiPartToFile, MAX_VIDE0_SIZE);
//            }
            // 获取文件的真实名称
            //String originalFilename = convertMultiPartToFile.getName();
            String originalFilename = file.getOriginalFilename();
            // 文件扩展名
            //InputStream is = new FileInputStream(convertMultiPartToFile);
            InputStream is = file.getInputStream();
            String contentType = Files.probeContentType(Paths.get(originalFilename));
            if(contentType == null){
                contentType = "application/octet-stream";
            }
            // 构建文件上传相关信息
            PutObjectArgs args = PutObjectArgs.builder()
                    .bucket(bucket_videofiles)
                    .object(originalFilename)
                    .stream(is, is.available(), -1)
                    .contentType(contentType)
                    .build();
            // 将文件上传到 minio 服务器
            minioClient.putObject(args);
            // 拼接文件上传地址，存入数据库
            String url = minioConfig.getEndpoint() + "/" + bucket_videofiles + "/" + originalFilename;
            log.info("视频文件上传成功:{}， url:{}", originalFilename, url);

            File f = new File();
            LocalDateTime now = LocalDateTime.now();
            f.setType(2);
            f.setPath(url);
            f.setCreateTime(now);
            f.setUpdateTime(now);
            f.setOrderId(orderId);
            fileMapper.saveFile(f);
        }catch (Exception e){
            e.getMessage();
        }
        OrderRepair orderRepair = orderRepairMapper.selectById(orderId);
        Map map = new HashMap();
        map.put("type", 1);
        map.put("orderId", orderId);
        map.put("sn", orderRepair.getSn());
        map.put("message", "等待用户支付订单,sn编号:" + orderRepair.getSn());
        LocalDateTime messageDate = LocalDateTime.now();
        map.put("date", messageDate);
        Long currentId = BaseContext.getCurrentId();
        webSocketServer.sendToClient(currentId+"",JSON.toJSONString(map));
        orderRepairService.createPayOrder(orderId);
        stateMachineUtil.saveAndSendEvent(orderId,RepairOrderEvent.RETEST_SUCCESS);
        orderRepairMapper.updateStatusById(orderId, RepairOrderStatus.WAITING_PAY.getCode());
        Schedule schedule = scheduleService.getScheduleByOrderIdAndStatus(orderId, RepairOrderStatus.WAITING_PAY.getCode());
        if(schedule == null){
            scheduleService.insertSchedule(orderId, RepairOrderStatus.WAITING_PAY.getCode(), 1);
        }
        return null;
    }

    private java.io.File convertMultiPartToFile(MultipartFile file) throws IOException {
        java.io.File convFile = new java.io.File(System.getProperty("java.io.tmpdir") + "/" + file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convFile)) {
            fos.write(file.getBytes());
        }
        return convFile;
    }
}
