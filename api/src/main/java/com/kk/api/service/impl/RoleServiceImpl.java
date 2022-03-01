package com.kk.api.service.impl;

import com.kk.api.mapper.PermissionMapper;
import com.kk.api.mapper.RoleMapper;
import com.kk.api.service.RoleService;
import com.kk.api.core.service.AbstractService;
import com.kk.api.dto.RoleWithPermission;
import com.kk.api.dto.RoleWithResource;
import com.kk.api.entity.Role;
import com.kk.api.entity.RolePermission;
import com.kk.api.mapper.RolePermissionMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RoleServiceImpl extends AbstractService<Role> implements RoleService {
  @Resource private RoleMapper roleMapper;
  @Resource private PermissionMapper permissionMapper;
  @Resource private RolePermissionMapper rolePermissionMapper;

  @Override
  public List<RoleWithResource> listRoleWithPermission() {
    // 由于mybatis在嵌套查询时和pagehelper有冲突
    // 暂时用for循环代替
    final List<RoleWithResource> roles = this.roleMapper.listRoles();
    roles.forEach(
        role -> {
          final List<com.kk.api.entity.Resource> resources =
              this.permissionMapper.listRoleWithResourceByRoleId(role.getId());
          role.setResourceList(resources);
        });
    return roles;
  }

  @Override
  public void save(final RoleWithPermission role) {
    this.roleMapper.insert(role);
    this.rolePermissionMapper.saveRolePermission(role.getId(), role.getPermissionIdList());
  }

  @Override
  public void update(final RoleWithPermission role) {
    // 删掉所有权限，再添加回去
    final Condition condition = new Condition(RolePermission.class);
    condition.createCriteria().andCondition("role_id = ", role.getId());
    this.rolePermissionMapper.deleteByCondition(condition);
    this.rolePermissionMapper.saveRolePermission(role.getId(), role.getPermissionIdList());
    this.roleMapper.updateTimeById(role.getId());
  }
}
