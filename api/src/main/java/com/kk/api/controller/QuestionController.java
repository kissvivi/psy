package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.dto.TestsDto;
import com.kk.api.entity.ChatLog;
import com.kk.api.entity.Question;
import com.kk.api.entity.Tests;
import com.kk.api.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.kk.api.service.TestsService;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author kk
 * @date 2022/03/16
 */
@RestController
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private QuestionService questionService;

    @Resource
    private TestsService testsService;

    @PostMapping
    public Result add(@RequestBody Question question) {
        questionService.save(question);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        questionService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Question question) {
        questionService.update(question);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Question question = questionService.getById(id);
        return ResultGenerator.genOkResult(question);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Question> list = questionService.listAll();
        PageInfo<Question> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("testsList")
    public Result TestsList() {

        Condition condition = new Condition(Question.class);
        condition.orderBy("createTime").desc();

        TestsDto testsDto = new TestsDto();
        testsDto.setQuestion(questionService.listByCondition(condition).get(0));

        char[] ids = testsDto.getQuestion().getTestIds().toCharArray();

        System.out.println(ids);

        List<Integer> intIds=new ArrayList<Integer>();
        for (char id:ids) {
            System.out.println(id);
            if(id != ','){
                intIds.add(Integer.parseInt(id+""));
            }
        }
        List<Tests> testsList = new ArrayList<>();
        for (int intId:intIds) {

            testsList.add(testsService.getById(intId));
        }
        testsDto.setTestsList(testsList);
        return ResultGenerator.genOkResult(testsDto);
    }
}
