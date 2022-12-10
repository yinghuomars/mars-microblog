package com.yinghuomars.microblog.config;

/**
 * @author <a href="https://www.yinghuomars.com">YHMARS</a>2022/12/9
 * @version 1.0.0
 * @since 1.0.0
 */

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 本地上传策略
 */
@Data
@Configuration
@ConfigurationProperties(prefix = "yinghuomars.upload.local")
public class LocalUploadProperties {

    /**
     * 访问url
     */
    private String baseUrl;

    /**
     * 本地路径
     */
    private String basePath;

}