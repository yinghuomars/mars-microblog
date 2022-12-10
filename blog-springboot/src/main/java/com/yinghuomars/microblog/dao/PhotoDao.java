package com.yinghuomars.microblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinghuomars.microblog.entity.Photo;
import org.springframework.stereotype.Repository;

/**
 * 照片映射器
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/04
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface PhotoDao extends BaseMapper<Photo> {

}

