package com.yinghuomars.microblog.strategy.context;

import com.yinghuomars.microblog.dto.UserInfoDTO;
import com.yinghuomars.microblog.enums.LoginTypeEnum;
import com.yinghuomars.microblog.strategy.SocialLoginStrategy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 第三方登录策略上下文
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/28
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class SocialLoginStrategyContext {

    private final Map<String, SocialLoginStrategy> socialLoginStrategyMap;

    /**
     * 执行第三方登录策略
     *
     * @param data          数据
     * @param loginTypeEnum 登录枚举类型
     * @return {@link UserInfoDTO} 用户信息
     */
    public UserInfoDTO executeLoginStrategy(String data, LoginTypeEnum loginTypeEnum) {
        return socialLoginStrategyMap.get(loginTypeEnum.getStrategy()).login(data);
    }

}
