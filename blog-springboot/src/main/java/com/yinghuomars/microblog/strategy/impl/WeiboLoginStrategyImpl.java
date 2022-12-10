package com.yinghuomars.microblog.strategy.impl;

import com.alibaba.fastjson.JSON;
import com.yinghuomars.microblog.config.WeiboOpenPlatformProperties;
import com.yinghuomars.microblog.dto.SocialTokenDTO;
import com.yinghuomars.microblog.dto.SocialUserInfoDTO;
import com.yinghuomars.microblog.dto.WeiboTokenDTO;
import com.yinghuomars.microblog.dto.WeiboUserInfoDTO;
import com.yinghuomars.microblog.enums.LoginTypeEnum;
import com.yinghuomars.microblog.exception.BizException;
import com.yinghuomars.microblog.vo.WeiboLoginVO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.yinghuomars.microblog.constant.SocialLoginConst.*;
import static com.yinghuomars.microblog.enums.StatusCodeEnum.WEIBO_LOGIN_ERROR;

/**
 * 微博登录策略实现
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/28
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service("weiboLoginStrategyImpl")
public class WeiboLoginStrategyImpl extends AbstractSocialLoginStrategyImpl {
    private final WeiboOpenPlatformProperties weiboOpenPlatformProperties;
    private final RestTemplate restTemplate;

    @Override
    public SocialTokenDTO getSocialToken(String data) {
        WeiboLoginVO weiBoLoginVO = JSON.parseObject(data, WeiboLoginVO.class);
        // 获取微博token信息
        WeiboTokenDTO weiboToken = getWeiboToken(weiBoLoginVO);
        // 返回token信息
        return SocialTokenDTO.builder()
                .openId(weiboToken.getUid())
                .accessToken(weiboToken.getAccess_token())
                .loginType(LoginTypeEnum.WEIBO.getType())
                .build();
    }

    @Override
    public SocialUserInfoDTO getSocialUserInfo(SocialTokenDTO socialTokenDTO) {
        // 定义请求参数
        Map<String, String> data = new HashMap<>(2);
        data.put(UID, socialTokenDTO.getOpenId());
        data.put(ACCESS_TOKEN, socialTokenDTO.getAccessToken());
        // 获取微博用户信息
        WeiboUserInfoDTO weiboUserInfoDTO = restTemplate.getForObject(weiboOpenPlatformProperties.getUserInfoUrl(), WeiboUserInfoDTO.class, data);
        // 返回用户信息
        return SocialUserInfoDTO.builder()
                .nickname(Objects.requireNonNull(weiboUserInfoDTO).getScreen_name())
                .avatar(weiboUserInfoDTO.getAvatar_hd())
                .build();
    }

    /**
     * 获取微博token信息
     *
     * @param weiBoLoginVO 微博登录信息
     * @return {@link WeiboTokenDTO} 微博token
     */
    private WeiboTokenDTO getWeiboToken(WeiboLoginVO weiBoLoginVO) {
        // 根据code换取微博uid和accessToken
        MultiValueMap<String, String> weiboData = new LinkedMultiValueMap<>();
        // 定义微博token请求参数
        weiboData.add(CLIENT_ID, weiboOpenPlatformProperties.getAppId());
        weiboData.add(CLIENT_SECRET, weiboOpenPlatformProperties.getAppSecret());
        weiboData.add(GRANT_TYPE, weiboOpenPlatformProperties.getGrantType());
        weiboData.add(REDIRECT_URI, weiboOpenPlatformProperties.getRedirectUrl());
        weiboData.add(CODE, weiBoLoginVO.getCode());
        HttpEntity<MultiValueMap<String, String>> requestEntity = new HttpEntity<>(weiboData, null);
        try {
            return restTemplate.exchange(weiboOpenPlatformProperties.getAccessTokenUrl(), HttpMethod.POST, requestEntity, WeiboTokenDTO.class).getBody();
        } catch (Exception e) {
            throw new BizException(WEIBO_LOGIN_ERROR);
        }
    }

}
