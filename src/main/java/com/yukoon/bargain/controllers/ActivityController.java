package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.services.ActivityService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class ActivityController {
    @Autowired
    private ActivityService activityService;

    //获取Activity对象
    @ModelAttribute
    public void getActivity(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            Activity activity = activityService.findById(id);
            map.put("activity",activity);
        }
    }

    //后台查询所有活动
    @RequiresRoles("admin")
    @GetMapping("/acts")
    public String listAll(Map<String,Object> map) {
        map.put("acts",activityService.findAll());
        return "backend/act_list";
    }

    //后台前往添加活动
    @RequiresRoles("admin")
    @GetMapping("/act")
    public String toAdd() {
        return "backend/act_input";
    }

    //后台前往编辑活动
    @RequiresRoles("admin")
    @GetMapping("/act/{id}")
    public String toEdit(Map<String,Object> map, @PathVariable("id")Integer id) {
        map.put("activity",activityService.findById(id));
        return "backend/act_input";
    }
    //后台开启活动
    @RequiresRoles("admin")
    @GetMapping("/actopen/{id}")
    public String open(@PathVariable("id")Integer id) {
        Activity activity = activityService.findById(id);
        if (activity!=null) {
            activity.setAct_status(1);
            activityService.saveAct(activity);
        }
        return "redirect:/acts";
    }

    //后台关闭活动
    @GetMapping("/actclose/{id}")
    public String close(@PathVariable("id")Integer id) {
        Activity activity = activityService.findById(id);
        if (activity!=null) {
            activity.setAct_status(2);
            activityService.saveAct(activity);
        }
        return "redirect:/acts";
    }

    //后台编辑活动
    @RequiresRoles("admin")
    @PutMapping("/act")
    public String edit(Activity activity) {
        activityService.saveAct(activity);
        return "redirect:/acts";
    }

    //后台添加活动
    @RequiresRoles("admin")
    @PostMapping("/act")
    public String add(Activity activity) {
        activity.setAct_status(0);
        activityService.saveAct(activity);
        return "redirect:/acts";
    }

    //前台前往活动详情页面
    @GetMapping("/actInfo/{act_id}")
    public String toActInfo(@PathVariable("act_id")Integer act_id,Map<String,Object> map) {
        map.put("act_id",act_id);
        map.put("activity",activityService.findById(act_id));
        return "public/activity_info";
    }

    //后台前往活动二维码页面
    @RequiresRoles("admin")
    @GetMapping("/QR/{act_id}")
    public String toQRCode(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        String url = "/actInfo/"+act_id;
        map.put("url",url);
        return "backend/act_QR_code";
    }
}
