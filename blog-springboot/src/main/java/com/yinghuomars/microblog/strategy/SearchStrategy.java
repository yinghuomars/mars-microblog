package com.yinghuomars.microblog.strategy;

import com.yinghuomars.microblog.dto.ArticleSearchDTO;

import java.util.List;

/**
 * 搜索策略
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/27
 * @version 1.0.0
 * @since 1.0.0
 */
public interface SearchStrategy {

    /**
     * 搜索文章
     *
     * @param keywords 关键字
     * @return {@link List<ArticleSearchDTO>} 文章列表
     */
    List<ArticleSearchDTO> searchArticle(String keywords);

}
