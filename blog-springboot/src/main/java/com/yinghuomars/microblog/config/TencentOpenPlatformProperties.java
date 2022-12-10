package com.yinghuomars.microblog.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * qq配置属性
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/06/14
 * @version 1.0.0
 * @since 1.0.0
 **/
@Data
@Configuration
@ConfigurationProperties(prefix = "yinghuomars.platform.qq")
public class TencentOpenPlatformProperties {

    /**
     * QQ appId
     */
    private String appId;

    /**
     * 校验token地址
     */
    private String checkTokenUrl;

    /**
     * QQ用户信息地址
     */
    private String userInfoUrl;

}
