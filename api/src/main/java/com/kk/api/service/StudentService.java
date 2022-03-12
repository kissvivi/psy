package com.kk.api.service;

import com.kk.api.entity.Student;
import com.kk.api.core.service.Service;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kk
* @date 2022/03/08
*/
public interface StudentService extends Service<Student> {

    Student getStudentByUid(Long uid);

}
