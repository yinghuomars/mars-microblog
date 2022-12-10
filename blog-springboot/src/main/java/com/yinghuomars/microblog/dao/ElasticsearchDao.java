package com.yinghuomars.microblog.dao;

import com.yinghuomars.microblog.dto.ArticleSearchDTO;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

/**
 * elasticsearch
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/10
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface ElasticsearchDao extends ElasticsearchRepository<ArticleSearchDTO, Integer> {
}
