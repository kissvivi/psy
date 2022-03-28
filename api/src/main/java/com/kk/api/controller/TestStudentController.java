package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.Student;
import com.kk.api.entity.TestStudent;
import com.kk.api.service.TestStudentService;
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
@RequestMapping("/test/student")
public class TestStudentController {
@Resource
private TestStudentService testStudentService;

@PostMapping
public Result add(@RequestBody TestStudent testStudent) {
testStudentService.save(testStudent);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
testStudentService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody TestStudent testStudent) {
testStudentService.update(testStudent);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
TestStudent testStudent = testStudentService.getById(id);
return ResultGenerator.genOkResult(testStudent);
}



@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<TestStudent> list = testStudentService.listAll();
PageInfo<TestStudent> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
