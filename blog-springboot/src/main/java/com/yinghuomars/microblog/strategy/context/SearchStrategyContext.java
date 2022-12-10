package com.yinghuomars.microblog.strategy.context;

import com.yinghuomars.microblog.dto.ArticleSearchDTO;
import com.yinghuomars.microblog.enums.SearchModeEnum;
import com.yinghuomars.microblog.strategy.SearchStrategy;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * 搜索策略上下文
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/07/27
 * @version 1.0.0
 * @since 1.0.0
 */
@RequiredArgsConstructor
@Service
public class SearchStrategyContext {
    /**
     * 搜索模式
     */
    private final SearchStrategyProperties properties;

    private final Map<String, SearchStrategy> searchStrategyMap;

    /**
     * 执行搜索策略
     *
     * @param keywords 关键字
     * @return {@link List<ArticleSearchDTO>} 搜索文章
     */
    public List<ArticleSearchDTO> executeSearchStrategy(String keywords) {
        return searchStrategyMap.get(properties.getMode().getStrategy()).searchArticle(keywords);
    }

    @Data
    @Configuration
    @ConfigurationProperties(prefix = "yinghuomars.search")
    public static class SearchStrategyProperties {

        /**
         * 搜索模式
         */
        private SearchModeEnum mode;

    }

}
