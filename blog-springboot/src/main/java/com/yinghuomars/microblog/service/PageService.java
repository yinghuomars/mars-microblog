package com.yinghuomars.microblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinghuomars.microblog.entity.Page;
import com.yinghuomars.microblog.vo.PageVO;

import java.util.List;

/**
 * 页面服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/07
 * @version 1.0.0
 * @since 1.0.0
 */
public interface PageService extends IService<Page> {

    /**
     * 保存或更新页面
     *
     * @param pageVO 页面信息
     */
    void saveOrUpdatePage(PageVO pageVO);

    /**
     * 删除页面
     *
     * @param pageId 页面id
     */
    void deletePage(Integer pageId);

    /**
     * 获取页面列表
     *
     * @return {@link List<PageVO>} 页面列表
     */
    List<PageVO> listPages();

}
