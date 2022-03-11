package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.Consult;
import com.kk.api.service.ConsultService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
* @author kk
* @date 2022/03/08
*/
@RestController
@RequestMapping("/consult")
public class ConsultController {
@Resource
private ConsultService consultService;

@PostMapping
public Result add(@RequestBody Consult consult) {
consultService.save(consult);
return ResultGenerator.genOkResult();
}

@DeleteMapping("/{id}")
public Result delete(@PathVariable Long id) {
consultService.deleteById(id);
return ResultGenerator.genOkResult();
}

@PatchMapping
public Result update(@RequestBody Consult consult) {
consultService.update(consult);
return ResultGenerator.genOkResult();
}

@GetMapping("/{id}")
public Result detail(@PathVariable Long id) {
Consult consult = consultService.getById(id);
return ResultGenerator.genOkResult(consult);
}

@GetMapping
public Result list(@RequestParam(defaultValue = "0") Integer page,
@RequestParam(defaultValue = "0") Integer size) {
PageHelper.startPage(page, size);
List<Consult> list = consultService.listAll();
PageInfo<Consult> pageInfo = PageInfo.of(list);
return ResultGenerator.genOkResult(pageInfo);
}
}
