package com.kk.api.service.impl;

import com.kk.api.service.AccountRoleService;
import com.kk.api.core.service.AbstractService;
import com.kk.api.entity.AccountRole;
import com.kk.api.mapper.AccountRoleMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class AccountRoleServiceImpl extends AbstractService<AccountRole>
    implements AccountRoleService {
  @Resource private AccountRoleMapper accountRoleMapper;

  @Override
  public void updateRoleIdByAccountId(final AccountRole accountRole) {
    this.accountRoleMapper.updateRoleIdByAccountId(accountRole);
  }
}
