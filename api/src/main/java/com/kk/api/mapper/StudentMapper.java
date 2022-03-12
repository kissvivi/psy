package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
import com.kk.api.entity.Student;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface StudentMapper extends MyMapper<Student> {

    /*根据uid获取学生信息*/
    @Select("select * from t_student where uid = #{uid}")
    Student getStudentByUid(@Param("uid")Long uid);
}