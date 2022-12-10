package com.yinghuomars.microblog.annotation;

import java.lang.annotation.*;

/**
 * redis接口限流
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2022/03/23
 * @version 1.0.0
 * @since 1.0.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AccessLimit {

    /**
     * 单位时间（秒）
     *
     * @return int
     */
    int seconds();

    /**
     * 单位时间最大请求次数
     *
     * @return int
     */
    int maxCount();

}
