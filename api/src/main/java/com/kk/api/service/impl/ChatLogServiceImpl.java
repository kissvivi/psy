package com.kk.api.service.impl;

import com.kk.api.mapper.ChatLogMapper;
import com.kk.api.entity.ChatLog;
import com.kk.api.service.ChatLogService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author kk
* @date 2022/03/17
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class ChatLogServiceImpl extends AbstractService<ChatLog> implements ChatLogService {
@Resource
private ChatLogMapper tChatLogMapper;

}
