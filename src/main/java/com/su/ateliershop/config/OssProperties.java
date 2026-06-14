package com.su.ateliershop.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "insighthub.oss")
public class OssProperties {
    private boolean enabled = true;
    private String endpoint;
    private String accessKeyId;
    private String accessKeySecret;
    private String bucketName;
}
