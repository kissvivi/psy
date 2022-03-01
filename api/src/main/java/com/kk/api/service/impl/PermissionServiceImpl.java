package com.kk.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kk.api.core.service.AbstractService;
import com.kk.api.mapper.PermissionMapper;
import com.kk.api.entity.Permission;
import com.kk.api.service.PermissionService;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class PermissionServiceImpl extends AbstractService<Permission>
    implements PermissionService {
  @Resource private PermissionMapper permissionMapper;

  @Override
  public List<com.kk.api.entity.Resource> listResourceWithHandle() {
    return this.permissionMapper.listResourceWithHandle();
  }

  @Override
  public List<com.kk.api.entity.Resource> listRoleWithResourceByRoleId(Long roleId) {
    return this.permissionMapper.listRoleWithResourceByRoleId(roleId);
  }
}
