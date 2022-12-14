package com.yinghuomars.microblog.consumer;

import com.alibaba.fastjson.JSON;
import com.yinghuomars.microblog.dao.ElasticsearchDao;
import com.yinghuomars.microblog.dto.ArticleSearchDTO;
import com.yinghuomars.microblog.dto.MaxwellDataDTO;
import com.yinghuomars.microblog.entity.Article;
import com.yinghuomars.microblog.util.BeanCopyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import static com.yinghuomars.microblog.constant.MqPrefixConst.MAXWELL_QUEUE;

/**
 * maxwell监听数据
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/02
 * @version 1.0.0
 * @since 1.0.0
 */
@Component
@RequiredArgsConstructor
@RabbitListener(queues = MAXWELL_QUEUE)
public class MaxWellConsumer {

    private final ElasticsearchDao elasticsearchDao;

    @RabbitHandler
    public void process(byte[] data) {
        // 获取监听信息
        MaxwellDataDTO maxwellDataDTO = JSON.parseObject(new String(data), MaxwellDataDTO.class);
        // 获取文章数据
        Article article = JSON.parseObject(JSON.toJSONString(maxwellDataDTO.getData()), Article.class);
        // 判断操作类型
        switch (maxwellDataDTO.getType()) {
            case "insert":
            case "bootstrap-insert":
            case "update":
                // 更新es文章
                elasticsearchDao.save(BeanCopyUtils.copyObject(article, ArticleSearchDTO.class));
                break;
            case "delete":
                // 删除文章
                elasticsearchDao.deleteById(article.getId());
                break;
            default:
                break;
        }
    }

}