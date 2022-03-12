package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
import com.kk.api.entity.Doctor;
import com.kk.api.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface DoctorMapper extends MyMapper<Doctor> {

    /*根据uid获取医生信息*/
    @Select("select * from t_doctor where uid = #{uid}")
    Doctor getDoctorByUid(@Param("uid")Long uid);
}