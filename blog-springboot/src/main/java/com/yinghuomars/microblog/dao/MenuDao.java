package com.yinghuomars.microblog.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yinghuomars.microblog.entity.Menu;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜单
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/10
 * @version 1.0.0
 * @since 1.0.0
 */
@Repository
public interface MenuDao extends BaseMapper<Menu> {

    /**
     * 根据用户id查询菜单
     *
     * @param userInfoId 用户信息id
     * @return 菜单列表
     */
    List<Menu> listMenusByUserInfoId(Integer userInfoId);

}
