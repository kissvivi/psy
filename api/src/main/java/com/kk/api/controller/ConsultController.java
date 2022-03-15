package com.kk.api.controller;

import com.kk.api.core.response.Result;
import com.kk.api.core.response.ResultGenerator;
import com.kk.api.entity.Consult;
import com.kk.api.service.ConsultService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Condition;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    @GetMapping("listBySid")
    public Result listBySid(@RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "0") Long sid,
                            @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);

        Condition condition = new Condition(Consult.class);
        condition.createCriteria().andEqualTo("sid", sid);
        List<Consult> list = consultService.listByCondition(condition);

        PageInfo<Consult> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @GetMapping("listByDid")
    public Result listByDid(@RequestParam(defaultValue = "0") Integer page,
                            @RequestParam(defaultValue = "0") Long did,
                            @RequestParam(defaultValue = "0") Integer size) {
        PageHelper.startPage(page, size);

        Condition condition = new Condition(Consult.class);
        condition.createCriteria().andEqualTo("did", did);
        List<Consult> list = consultService.listByCondition(condition);

        PageInfo<Consult> pageInfo = PageInfo.of(list);
        return ResultGenerator.genOkResult(pageInfo);
    }

    @PutMapping("status")
    public Result update(@RequestParam(defaultValue = "0") Integer status,
                         @RequestParam(defaultValue = "0") Long id) {
        consultService.updateStatus(status, id);
        return ResultGenerator.genOkResult();
    }

    /**
     * 定制分析下载模板文档
     *
     * @param response
     * @return
     */
    @RequestMapping("/upload/downLoadTemplateExcel")
    public Result downLoadTemplateExcel(HttpServletResponse response) {
        // 创建输入输出流
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String url = null;
        url = "D:/upload/template/test.xlsx";
        //url = "/usr/java/test.xlsx";

        String downLoadPath = url;
        String fileName = "template.xlsx";//生成的文件名
        File file2 = new File(downLoadPath);//要下载的文件对象
        if (!file2.exists()) {//如果目录不存在,创建目录
            file2.mkdirs();
        }
        long fileLength = file2.length();// 获取文件长度
        try {
            //Content-Disposition: attachment; filename="filename.xls"
            //第一个参数是attachment（意味着消息体应该被下载到本地；大多数浏览器会呈现一个“保存为”的对话框，
            // 将filename的值预填为下载后的文件名，假如它存在的话）
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            //Content-Type 实体头部用于指示资源的MIME类型 media type
            response.setHeader("Content-Type", "application/json");
            //Content-Length, HTTP消息长度, 用十进制数字表示的八位字节的数目
            response.setHeader("Content-Length", String.valueOf(fileLength));
            // 创建输入输出流实例
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            // 创建字节缓冲大小
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();// 关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();// 关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return ResultGenerator.genOkResult();
    }

    //单文件上传的功能
    @RequestMapping(value = "/upload/{id}")
    public Result upload(@PathVariable Long id,@RequestParam MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResultGenerator.genFailedResult("文件为空");
            }
            String fileName = file.getOriginalFilename();//获取文件名
            String suffixName = fileName.substring(fileName.lastIndexOf("."));//切割文件名
            System.out.println("上传的文件名为：" + fileName + " 后缀名为" + suffixName);
            // 设置文件存储路径(D盘),你可以存放在你想要指定的路径里面。
            String filePath = "D:/upload";
            //文件存放路径=filePath+/+ fileName
            String path = "/" + today()+suffixName;
            File dest = new File(new File(filePath).getAbsolutePath()+path);


            // 检测是否存在目录
            if (!dest.getParentFile().exists()) {
                dest.getParentFile().mkdirs();// 新建文件夹
            }

            byte [] bytes = file.getBytes();
            OutputStream out = new FileOutputStream(dest);
            out.write(bytes);

            Consult consult = new Consult();
            consult.setReport_url(path);
            consult.setId(id);
            consultService.update(consult);

            //file.transferTo(dest);// 文件写入到上述的存放路径path
            return ResultGenerator.genOkResult();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultGenerator.genFailedResult("上传文件出错");
    }


    @RequestMapping("/upload/{id}/download")
    public Result downLoadExcel(@PathVariable Long id,HttpServletResponse response) {

        Consult consult = consultService.getById(id);
        String report_url = consult.getReport_url();

        // 创建输入输出流
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        String url = null;
        url = "D:/upload";
        //url = "/usr/java/test.xlsx";

        url += report_url;

        String downLoadPath = url;
        String fileName = report_url;//生成的文件名
        File file2 = new File(downLoadPath);//要下载的文件对象
        if (!file2.exists()) {//如果目录不存在,创建目录
            file2.mkdirs();
        }
        long fileLength = file2.length();// 获取文件长度
        try {
            //Content-Disposition: attachment; filename="filename.xls"
            //第一个参数是attachment（意味着消息体应该被下载到本地；大多数浏览器会呈现一个“保存为”的对话框，
            // 将filename的值预填为下载后的文件名，假如它存在的话）
            response.setHeader("Content-disposition", "attachment; filename=" + new String(fileName.getBytes("utf-8"), "ISO8859-1"));
            //Content-Type 实体头部用于指示资源的MIME类型 media type
            response.setHeader("Content-Type", "application/json");
            //Content-Length, HTTP消息长度, 用十进制数字表示的八位字节的数目
            response.setHeader("Content-Length", String.valueOf(fileLength));
            // 创建输入输出流实例
            bis = new BufferedInputStream(new FileInputStream(downLoadPath));
            bos = new BufferedOutputStream(response.getOutputStream());
            // 创建字节缓冲大小
            byte[] buff = new byte[2048];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bis != null)
                try {
                    bis.close();// 关闭输入流
                } catch (IOException e) {
                    e.printStackTrace();
                }
            if (bos != null)
                try {
                    bos.close();// 关闭输出流
                } catch (IOException e) {
                    e.printStackTrace();
                }
        }
        return ResultGenerator.genOkResult();
    }

    /** 获取当前日期与时间 **/
    public String today() {
        Date today = new Date(System.currentTimeMillis());  // 1000 * 60 * 60 * 24
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        return simpleDateFormat.format(today);
    }

}
