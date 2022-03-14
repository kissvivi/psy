package com.kk.api.mapper;

import com.kk.api.core.mapper.MyMapper;
import com.kk.api.entity.Consult;
import org.apache.ibatis.annotations.Update;

public interface ConsultMapper extends MyMapper<Consult> {

    /*更新状态*/
    @Update("update t_consult set status= #{status} where id = #{id}")
    void updateStatus(int status,long id);



}