package com.yinghuomars.microblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinghuomars.microblog.dto.TagBackDTO;
import com.yinghuomars.microblog.entity.Tag;
import com.yinghuomars.microblog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 标签
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/01
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface TagDao extends BaseMapper<Tag> {

    /**
     * 查询后台标签列表
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List<TagBackDTO>} 标签列表
     */
    List<TagBackDTO> listTagBackDTO(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);

    /**
     * 根据文章id查询标签名
     *
     * @param articleId 文章id
     * @return {@link List<String>} 标签名列表
     */
    List<String> listTagNameByArticleId(Integer articleId);

}
