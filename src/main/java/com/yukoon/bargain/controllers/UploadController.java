package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.UploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
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
}
