package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.services.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Map;

@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //后台查询所有活动
    @GetMapping("/acts")
    public String listAll(Map<String,Object> map) {
        map.put("acts",activityService.findAll());
        return "backend/act_list";
    }

    //后台前往添加活动
    @GetMapping("/act")
    public String toAdd() {
        return "backend/act_input";
    }

    //后台添加活动
    @PostMapping("/act")
    public String Add(Activity activity) {
        activity.setAct_status(0);
        activityService.saveAct(activity);
        return "redirect:/acts";
    }
}
