package com.kk.api.service;

import com.kk.api.core.service.Service;
import com.kk.api.entity.AccountRole;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
public interface AccountRoleService extends Service<AccountRole> {
  /**
   * 更新用户角色
   *
   * @param accountRole 用户角色
   */
  void updateRoleIdByAccountId(AccountRole accountRole);
}
