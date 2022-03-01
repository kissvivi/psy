package com.kk.api.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.api.service.PermissionService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Zoctan
 * @date 2018/06/09
 */
@RestController
@RequestMapping("/permission")
public class PermissionController {
  @Resource private PermissionService permissionService;

  @PreAuthorize("hasAuthority('role:list')")
  @GetMapping
  public Result listResourcePermission(
      @RequestParam(defaultValue = "0") final Integer page,
      @RequestParam(defaultValue = "0") final Integer size) {
    PageHelper.startPage(page, size);
    final List<com.kk.api.entity.Resource> list =
        this.permissionService.listResourceWithHandle();
    final PageInfo<com.kk.api.entity.Resource> pageInfo = new PageInfo<>(list);
    return ResultGenerator.genOkResult(pageInfo);
  }
}
