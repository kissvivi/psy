package com.kk.api.service;

import com.kk.api.core.service.Service;
import com.kk.api.dto.RoleWithPermission;
import com.kk.api.dto.RoleWithResource;
import com.kk.api.entity.Role;

import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
public interface RoleService extends Service<Role> {

  /**
   * 新建角色
   *
   * @param roleWithPermission 带权限列表的角色
   */
  void save(RoleWithPermission roleWithPermission);

  /**
   * 更新角色
   *
   * @param roleWithPermission 带权限列表的角色
   */
  void update(RoleWithPermission roleWithPermission);

  /**
   * 获取所有角色以及对应的权限
   *
   * @return 角色可控资源列表
   */
  List<RoleWithResource> listRoleWithPermission();
}
