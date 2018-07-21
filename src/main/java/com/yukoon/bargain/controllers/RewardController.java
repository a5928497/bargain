package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.services.ActivityService;
import com.yukoon.bargain.services.RewardService;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Controller
public class RewardController {
    @Autowired
    private RewardService rewardService;
    @Autowired
    private ActivityService activityService;

    //获取Reward对象
    @ModelAttribute
    public void getReward(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            Reward reward = rewardService.findById(id);
            map.put("reward",reward);
        }
    }

    //后台查看某一活动下所有礼品
    @RequiresRoles("admin")
    @GetMapping("/rewards/{act_id}")
    public String listAll(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("rewards",rewardService.findAllByActid(act_id));
        map.put("act_id",act_id);
        map.put("act_status",activityService.getActstatusByActid(act_id));
        return "backend/reward_list";
    }

    //后台前往向某一活动添加礼品
    @RequiresRoles("admin")
    @GetMapping("/rewardToAdd/{act_id}")
    public String toAdd(@PathVariable("act_id")Integer act_id,Map<String,Object> map) {
        map.put("act_id",act_id);
        return "backend/reward_input";
    }

    //后台前往某一活动编辑礼品
    @RequiresRoles("admin")
    @GetMapping("/reward/{id}")
    public String toEdit(@PathVariable("id")Integer id,Map<String,Object> map) {
        map.put("reward",rewardService.findById(id));
        return "backend/reward_input";
    }

    //后台编辑某一活动下礼品
    @RequiresRoles("admin")
    @PutMapping("/reward")
    public String edit(Reward reward) {
        rewardService.saveReward(reward);
        return "redirect:/rewards/"+reward.getActivity().getId();
    }

    //后台向某一活动添加礼品
    @RequiresRoles("admin")
    @PostMapping("/reward")
    public String add(Reward reward){
        System.out.println(reward);
        rewardService.saveReward(reward);
        return "redirect:/rewards/"+reward.getActivity().getId();
    }

    //后台删除某一活动下的礼品
    @RequiresRoles("admin")
    @DeleteMapping("/reward/{id}")
    public String delete(@PathVariable("id")Integer id,Integer act_id,Map<String,Object> map) {
        map.put("rewards",rewardService.findAllByActid(act_id));
        rewardService.deleteReward(id);
        return "redirect:/rewards/"+act_id;
    }
}
