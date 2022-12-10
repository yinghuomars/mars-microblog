package com.yinghuomars.microblog.config;

/**
 * oss上传策略
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a>2022/12/9
 * @version 1.0.0
 * @since 1.0.0
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "yinghuomars.upload.oss")
public class OssUploadProperties {

    /**
     * oss域名
     */
    private String url;

    /**
     * 端点
     */
    private String endpoint;

    /**
     * 访问密钥Id
     */
    private String accessKeyId;

    /**
     * 访问密钥Secret
     */
    private String accessKeySecret;

    /**
     * bucket名称
     */
    private String bucketName;

}