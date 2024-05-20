package com.mi_repair.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Kuroko
 * @description
 * @date 2024/5/17 10:38
 */
@Component
@ConfigurationProperties(prefix = "mi-repair.jwt")
@Data
public class JwtProperties {
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

    private String workerSecretKey;
    private long workerTtl;
    private String workerTokenName;
}
