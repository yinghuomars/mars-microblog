package com.yinghuomars.microblog.strategy.context;

import com.yinghuomars.microblog.enums.UploadModeEnum;
import com.yinghuomars.microblog.strategy.UploadStrategy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * 上传策略上下文
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/28
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
@RequiredArgsConstructor
public class UploadStrategyContext {

    private final UploadStrategyProperties properties;

    private final Map<String, UploadStrategy> uploadStrategyMap;

    /**
     * 上传文件
     *
     * @param file 文件
     * @param path 路径
     * @return {@link String} 文件地址
     */
    public String executeUploadStrategy(MultipartFile file, String path) {
        return uploadStrategyMap.get(properties.getMode().getStrategy()).uploadFile(file, path);
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "yinghuomars.upload")
    public static class UploadStrategyProperties {

        /**
         * 上传模式
         */
        private UploadModeEnum mode;

    }

}
