package com.yinghuomars.microblog.util;

import com.yinghuomars.microblog.dto.UserDetailDTO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * 用户工具类
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/10
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
public class UserUtils {

    /**
     * 获取当前登录用户
     *
     * @return 用户登录信息
     */
    public static UserDetailDTO getLoginUser() {
        return (UserDetailDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

}
