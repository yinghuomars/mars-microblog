package com.yinghuomars.microblog.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yinghuomars.microblog.dao.UserRoleDao;
import com.yinghuomars.microblog.entity.UserRole;
import com.yinghuomars.microblog.service.UserRoleService;
import org.springframework.stereotype.Service;

/**
 * 用户角色服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/10
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class UserRoleServiceImpl extends ServiceImpl<UserRoleDao, UserRole> implements UserRoleService {

}
