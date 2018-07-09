package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;

    //后台前往兑换券批量上传
    @GetMapping("/touploadcode/{act_id}")
    public String toUploadExcel(@PathVariable("act_id") Integer act_id, Map<String,Object> map, HttpServletRequest request) {
        Map<String,?> map1 = RequestContextUtils.getInputFlashMap(request);
        String uploadMsg = null;
        if (map1 != null) {
            uploadMsg = map1.get("uploadMsg").toString();
        }
        if (uploadMsg !=null) {
            map.put("uploadMsg",uploadMsg);
        }
        map.put("act_id",act_id);
        return "backend/code_excel_upload";
    }

    //后台进行兑换码Excel上传
    @PostMapping("/uploadcode")
    public String uploadExcel(@RequestParam("excel")MultipartFile excel, Integer act_id, RedirectAttributes attributes) {
        List<String> repeatNames;
        try {
            InputStream in = excel.getInputStream();
            repeatNames = uploadService.importCodeExcel(in,excel,act_id);
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            attributes.addFlashAttribute("uploadMsg","上传失败，请重试！");
            return "redirect:/touploadcode/"+act_id;
        }
        System.out.println("成功");
        if (repeatNames.size() != 0 ){
            String uploadMsg ="上传成功！并去除了EXCEL表中重复的:\n";
            for (String s :repeatNames) {
                uploadMsg = uploadMsg + s + ",\n";
            }
            uploadMsg = uploadMsg + "一共"+ repeatNames.size() + "个兑换码";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
        }else {
            attributes.addFlashAttribute("uploadMsg","上传成功！");
        }
        return "redirect:/touploadcode/"+act_id;
    }
}
