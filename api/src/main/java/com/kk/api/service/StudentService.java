package com.kk.api.service;

import com.kk.api.entity.Student;
import com.kk.api.core.service.Service;

/**
* @author kk
* @date 2022/03/28
*/
public interface StudentService extends Service<Student> {

    Student getStudentByUid(Long uid);
}
