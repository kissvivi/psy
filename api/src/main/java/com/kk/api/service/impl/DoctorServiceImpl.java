package com.kk.api.service.impl;

import com.kk.api.mapper.DoctorMapper;
import com.kk.api.entity.Doctor;
import com.kk.api.service.DoctorService;
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
public class DoctorServiceImpl extends AbstractService<Doctor> implements DoctorService {
@Resource
private DoctorMapper tDoctorMapper;

}
