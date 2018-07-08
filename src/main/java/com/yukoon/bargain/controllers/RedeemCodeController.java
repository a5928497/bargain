package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.services.RedeemCodeService;
import com.yukoon.bargain.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Map;

@Controller
public class RedeemCodeController {

    @Autowired
    private RedeemCodeService redeemCodeService;
    @Autowired
    private RewardService rewardService;

    @GetMapping("/code/{reward_id}")
    public String findCodeByRewardId(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map) {
        List<RedeemCode> codes = redeemCodeService.findCodeByRewardId(reward_id);
        map.put("act_id",rewardService.findById(reward_id).getActivity().getId());
        map.put("codes",codes);
        map.put("size", codes.size());
        return "backend/redeem_codes_list";
    }

    @GetMapping("/addcode/{reward_id}")
    public String toaddCode(Map<String,Object> map,@PathVariable("reward_id")Integer reward_id) {
        map.put("reward_Id",reward_id);
        return "backend/redeem_codes_input";
    }
}
