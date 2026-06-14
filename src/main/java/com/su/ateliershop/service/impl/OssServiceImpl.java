package com.su.ateliershop.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.su.ateliershop.common.BusinessException;
import com.su.ateliershop.config.OssProperties;
import com.su.ateliershop.service.OssService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {

    private final OssProperties ossProperties;

    public OssServiceImpl(OssProperties ossProperties) {
        this.ossProperties = ossProperties;
    }

    @Override
    public String upload(MultipartFile file) {
        if (!ossProperties.isEnabled()) {
            throw new BusinessException("OSS 未启用");
        }
        if (file == null || file.isEmpty()) {
            throw new BusinessException("请选择要上传的文件");
        }

        String originalName = file.getOriginalFilename();
        String ext = "";
        if (originalName != null && originalName.contains(".")) {
            ext = originalName.substring(originalName.lastIndexOf('.'));
        }
        String objectName = "goods/" + UUID.randomUUID().toString().replace("-", "") + ext;

        OSS ossClient = new OSSClientBuilder().build(
                ossProperties.getEndpoint(),
                ossProperties.getAccessKeyId(),
                ossProperties.getAccessKeySecret()
        );

        try (InputStream inputStream = file.getInputStream()) {
            ossClient.putObject(ossProperties.getBucketName(), objectName, inputStream);
        } catch (Exception e) {
            throw new BusinessException("上传失败: " + e.getMessage(), 500);
        } finally {
            ossClient.shutdown();
        }

        String endpoint = ossProperties.getEndpoint().replace("https://", "").replace("http://", "");
        return "https://" + ossProperties.getBucketName() + "." + endpoint + "/" + objectName;
    }
}
