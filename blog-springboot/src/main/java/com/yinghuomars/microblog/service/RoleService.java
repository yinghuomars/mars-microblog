package com.yinghuomars.microblog.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yinghuomars.microblog.dto.RoleDTO;
import com.yinghuomars.microblog.dto.UserRoleDTO;
import com.yinghuomars.microblog.entity.Role;
import com.yinghuomars.microblog.vo.ConditionVO;
import com.yinghuomars.microblog.vo.PageResult;
import com.yinghuomars.microblog.vo.RoleVO;

import java.util.List;

/**
 * 角色服务
 *
 * @author <a href="https://www.yinghuomars.com">YHMARS</a> 2021/08/10
 * @version 1.0.0
 * @since 1.0.0
 */
public interface RoleService extends IService<Role> {

    /**
     * 获取用户角色选项
     *
     * @return 角色
     */
    List<UserRoleDTO> listUserRoles();

    /**
     * 查询角色列表
     *
     * @param conditionVO 条件
     * @return 角色列表
     */
    PageResult<RoleDTO> listRoles(ConditionVO conditionVO);

    /**
     * 保存或更新角色
     *
     * @param roleVO 角色
     */
    void saveOrUpdateRole(RoleVO roleVO);

    /**
     * 删除角色
     *
     * @param roleIdList 角色id列表
     */
    void deleteRoles(List<Integer> roleIdList);

}
