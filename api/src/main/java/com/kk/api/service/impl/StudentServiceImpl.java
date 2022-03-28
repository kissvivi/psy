package com.kk.api.service.impl;

import com.kk.api.mapper.StudentMapper;
import com.kk.api.entity.Student;
import com.kk.api.service.StudentService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author kk
* @date 2022/03/28
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class StudentServiceImpl extends AbstractService<Student> implements StudentService {
@Resource
private StudentMapper tStudentMapper;

    @Override
    public Student getStudentByUid(Long uid) {
        return tStudentMapper.getStudentByUid(uid);
    }

}
