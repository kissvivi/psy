package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.Doctor;
import com.kk.api.service.DoctorService;
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
@RequestMapping("/doctor")
public class DoctorController {
    @Resource
    private DoctorService doctorService;

    @PostMapping
    public Result add(@RequestBody Doctor doctor) {
        doctorService.save(doctor);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        doctorService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody Doctor doctor) {
        doctorService.update(doctor);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        Doctor doctor = doctorService.getById(id);
        return ResultGenerator.genOkResult(doctor);
    }

    @GetMapping("uid/{uid}")
    public Result detailByUid(@PathVariable Long uid) {
        Doctor doctor = doctorService.getDoctorByUid(uid);
        return ResultGenerator.genOkResult(doctor);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<Doctor> list = doctorService.listAll();
        PageInfo<Doctor> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
