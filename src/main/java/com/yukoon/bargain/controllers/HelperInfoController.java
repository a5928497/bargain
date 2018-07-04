package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.HelperInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HelperInfoController {
    @Autowired
    private HelperInfoService helperInfoService;
    private final static Integer PAGE_SIZE = 10;

    @GetMapping("/helpers/{gameInfo_Id}")
    public String getHelpers(@PathVariable("gameInfo_Id")Integer gameInfo_Id,
                             Map<String,Object> map,
                             @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo =1;
        }
        map.put("page",helperInfoService.getPageableHelpers(pageNo,PAGE_SIZE,gameInfo_Id));
        return "backend/helper_list";
    }
}
