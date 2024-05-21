package com.mi.repair.config;

import io.minio.MinioClient;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/20 8:58
 */
@Configuration
@Data
public class MinioConfig {
    // 服务地址
    @Value("${mi-repair.minio.endpoint}")
    private String endpoint;
    // 用户名
    @Value("${mi-repair.minio.accessKey}")
    private String accessKey;
    // 密码
    @Value("${mi-repair.minio.secretKey}")
    private String secretKey;

    @Bean
    public MinioClient minioClient(){
        return MinioClient.builder()
                .endpoint(endpoint)
                .credentials(accessKey, secretKey)
                .build();
    }
}
