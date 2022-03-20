package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.ChatLog;
import com.kk.api.entity.Consult;
import com.kk.api.service.ChatLogService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author kk
 * @date 2022/03/17
 */
@RestController
@RequestMapping("/chat/log")
public class ChatLogController {
    @Resource
    private ChatLogService chatLogService;

    @PostMapping
    public Result add(@RequestBody ChatLog chatLog) {
        chatLogService.save(chatLog);
        return ResultGenerator.genOkResult();
    }

    @DeleteMapping("/{id}")
    public Result delete(@PathVariable Long id) {
        chatLogService.deleteById(id);
        return ResultGenerator.genOkResult();
    }

    @PatchMapping
    public Result update(@RequestBody ChatLog chatLog) {
        chatLogService.update(chatLog);
        return ResultGenerator.genOkResult();
    }

    @GetMapping("/{id}")
    public Result detail(@PathVariable Long id) {
        ChatLog chatLog = chatLogService.getById(id);
        return ResultGenerator.genOkResult(chatLog);
    }

    @GetMapping
    public Result list(@RequestParam(defaultValue = "0") Integer page,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);
        List<ChatLog> list = chatLogService.listAll();
        PageInfo<ChatLog> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("/listByConsultId")
    public Result listByUid(@RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "0") Long cid,
                       @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);

        Condition condition = new Condition(ChatLog.class);
        condition.createCriteria().andEqualTo("consultId", cid);
        condition.orderBy("date").desc();
        List<ChatLog> list = chatLogService.listByCondition(condition);
        PageInfo<ChatLog> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }
}
