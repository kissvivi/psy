package com.kk.api.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.kk.api.core.service.AbstractService;
import com.kk.api.mapper.RolePermissionMapper;
import com.kk.api.entity.RolePermission;
import com.kk.api.service.RolePermissionService;

import javax.annotation.Resource;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class RolePermissionServiceImpl extends AbstractService<RolePermission>
    implements RolePermissionService {
  @Resource private RolePermissionMapper rolePermissionMapper;
}
