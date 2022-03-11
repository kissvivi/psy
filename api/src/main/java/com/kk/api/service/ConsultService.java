package com.kk.api.service;

import com.kk.api.entity.Consult;
import com.kk.api.core.service.Service;
import org.apache.ibatis.annotations.Update;

/**
* @author kk
* @date 2022/03/08
*/
public interface ConsultService extends Service<Consult> {

    /*更新状态*/
    void updateStatus(int status,long id);

}
