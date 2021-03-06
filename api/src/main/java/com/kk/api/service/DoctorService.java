package com.kk.api.service;

import com.kk.api.entity.Doctor;
import com.kk.api.core.service.Service;
import com.kk.api.entity.Student;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
* @author kk
* @date 2022/03/08
*/
public interface DoctorService extends Service<Doctor> {

    Doctor getDoctorByUid(Long uid);
}
