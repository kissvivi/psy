package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.Question;
import com.kk.api.service.QuestionService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author kk
* @date 2022/03/15
*/
@RestController
@RequestMapping("/question")
public class QuestionController {
@Resource
private QuestionService questionService;

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
}
