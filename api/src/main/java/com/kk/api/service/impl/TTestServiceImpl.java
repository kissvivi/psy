package com.kk.api.service.impl;

import com.kk.api.mapper.TTestMapper;
import com.kk.api.entity.TTest;
import com.kk.api.service.TTestService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author kk
* @date 2022/03/08
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class TTestServiceImpl extends AbstractService<TTest> implements TTestService {
@Resource
private TTestMapper tTestMapper;

}
