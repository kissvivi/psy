package com.kk.api.service.impl;

import com.kk.api.mapper.ConsultMapper;
import com.kk.api.entity.Consult;
import com.kk.api.service.ConsultService;
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
public class ConsultServiceImpl extends AbstractService<Consult> implements ConsultService {
@Resource
private ConsultMapper tConsultMapper;

    @Override
    public void updateStatus(int status, long id) {
        tConsultMapper.updateStatus(status,id);
    }
}
