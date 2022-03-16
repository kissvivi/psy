package com.kk.api.service.impl;

import com.kk.api.mapper.QuestionMapper;
import com.kk.api.entity.Question;
import com.kk.api.service.QuestionService;
import com.kk.api.core.service.AbstractService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
* @author kk
* @date 2022/03/15
*/
@Service
@Transactional(rollbackFor = Exception.class)
public class QuestionServiceImpl extends AbstractService<Question> implements QuestionService {
@Resource
private QuestionMapper tQuestionMapper;

}
