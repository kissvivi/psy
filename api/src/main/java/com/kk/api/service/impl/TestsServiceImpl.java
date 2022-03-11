package com.kk.api.service.impl;

import com.kk.api.mapper.TestsMapper;
import com.kk.api.entity.Tests;
import com.kk.api.service.TestsService;
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
public class TestsServiceImpl extends AbstractService<Tests> implements TestsService {
@Resource
private TestsMapper tTestsMapper;

}
