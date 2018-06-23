package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.ActivityService;
import com.yukoon.bargain.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;
    @Autowired
    private ActivityService activityService;

    //后台查看某一活动下所有礼品
    @GetMapping("/rewards/{act_id}")
    public String listAll(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("rewards",rewardService.findAllByActid(act_id));
        map.put("act_id",act_id);
        map.put("act_status",activityService.getActstatusByActid(act_id));
        return "backend/reward_list";
    }

    //后台前往向某一活动添加礼品
    @GetMapping("/rewardToAdd/{act_id}")
    public String toAdd(@PathVariable("act_id")Integer act_id,Map<String,Object> map) {
        map.put("act_id",act_id);
        return "backend/reward_input";
    }
}
