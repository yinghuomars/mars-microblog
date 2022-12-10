package com.yinghuomars.microblog.service.impl;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinghuomars.microblog.dao.UniqueViewDao;
import com.yinghuomars.microblog.dto.UniqueViewDTO;
import com.yinghuomars.microblog.entity.UniqueView;
import com.yinghuomars.microblog.service.RedisService;
import com.yinghuomars.microblog.service.UniqueViewService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static com.yinghuomars.microblog.constant.RedisPrefixConst.UNIQUE_VISITOR;
import static com.yinghuomars.microblog.constant.RedisPrefixConst.VISITOR_AREA;
import static com.yinghuomars.microblog.enums.ZoneEnum.SHANGHAI;

/**
 * 访问量统计服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/06
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class UniqueViewServiceImpl extends ServiceImpl<UniqueViewDao, UniqueView> implements UniqueViewService {
    private final RedisService redisService;
    private final UniqueViewDao uniqueViewDao;

    @Override
    public List<UniqueViewDTO> listUniqueViews() {
        DateTime startTime = DateUtil.beginOfDay(DateUtil.offsetDay(new Date(), -7));
        DateTime endTime = DateUtil.endOfDay(new Date());
        return uniqueViewDao.listUniqueViews(startTime, endTime);
    }

    @Scheduled(cron = " 0 0 0 * * ?", zone = "Asia/Shanghai")
    public void saveUniqueView() {
        // 获取每天用户量
        Long count = redisService.sSize(UNIQUE_VISITOR);
        // 获取昨天日期插入数据
        UniqueView uniqueView = UniqueView.builder()
                .createTime(LocalDateTimeUtil.offset(LocalDateTime.now(ZoneId.of(SHANGHAI.getZone())), -1, ChronoUnit.DAYS))
                .viewsCount(Optional.of(count.intValue()).orElse(0))
                .build();
        uniqueViewDao.insert(uniqueView);
    }

    @Scheduled(cron = " 0 1 0 * * ?", zone = "Asia/Shanghai")
    public void clear() {
        // 清空redis访客记录
        redisService.del(UNIQUE_VISITOR);
        // 清空redis游客区域统计
        redisService.del(VISITOR_AREA);
    }

}
