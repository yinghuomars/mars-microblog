package com.yinghuomars.microblog.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinghuomars.microblog.dao.PageDao;
import com.yinghuomars.microblog.entity.Page;
import com.yinghuomars.microblog.service.PageService;
import com.yinghuomars.microblog.service.RedisService;
import com.yinghuomars.microblog.util.BeanCopyUtils;
import com.yinghuomars.microblog.vo.PageVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.yinghuomars.microblog.constant.RedisPrefixConst.PAGE_COVER;

/**
 * 页面服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/07
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class PageServiceImpl extends ServiceImpl<PageDao, Page> implements PageService {
    private final RedisService redisService;
    private final PageDao pageDao;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void saveOrUpdatePage(PageVO pageVO) {
        Page page = BeanCopyUtils.copyObject(pageVO, Page.class);
        this.saveOrUpdate(page);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void deletePage(Integer pageId) {
        pageDao.deleteById(pageId);
        // 删除缓存
        redisService.del(PAGE_COVER);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public List<PageVO> listPages() {
        List<PageVO> pageVOList;
        // 查找缓存信息，不存在则从mysql读取，更新缓存
        Object pageList = redisService.get(PAGE_COVER);
        if (Objects.nonNull(pageList)) {
            pageVOList = JSON.parseObject(pageList.toString(), List.class);
        } else {
            pageVOList = BeanCopyUtils.copyList(pageDao.selectList(null), PageVO.class);
            redisService.set(PAGE_COVER, JSON.toJSONString(pageVOList), 3600 * 12);
        }
        return pageVOList;
    }

}

