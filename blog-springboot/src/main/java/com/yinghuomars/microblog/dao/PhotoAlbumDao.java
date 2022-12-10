package com.yinghuomars.microblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinghuomars.microblog.dto.PhotoAlbumBackDTO;
import com.yinghuomars.microblog.entity.PhotoAlbum;
import com.yinghuomars.microblog.vo.ConditionVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 相册映射器
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/04
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface PhotoAlbumDao extends BaseMapper<PhotoAlbum> {

    /**
     * 查询后台相册列表
     *
     * @param current   页码
     * @param size      大小
     * @param condition 条件
     * @return {@link List < PhotoAlbumBackDTO >} 相册列表
     */
    List<PhotoAlbumBackDTO> listPhotoAlbumBacks(@Param("current") Long current, @Param("size") Long size, @Param("condition") ConditionVO condition);

}

