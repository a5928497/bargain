package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.services.RedeemCodeService;
import com.yukoon.bargain.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Controller
public class RedeemCodeController {
	private static final Integer PAGE_SIZE = 10;
    @Autowired
    private RedeemCodeService redeemCodeService;
    @Autowired
    private RewardService rewardService;

    @ModelAttribute
    public void getCode(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map) {
        if (id != null) {
            RedeemCode redeemCode = redeemCodeService.findById(id);
            map.put("redeemCode",redeemCode);
        }
    }

    @GetMapping("/code/{reward_id}")
    public String findCodeByRewardId(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map,
									 @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo) {
        Page page = redeemCodeService.findCodeByRewardId(pageNo,PAGE_SIZE,reward_id);
        map.put("act_id",rewardService.findById(reward_id).getActivity().getId());
        map.put("reward_id",reward_id);
        map.put("page",page);
        return "backend/redeem_codes_list";
    }

    @GetMapping("/addcode/{reward_id}")
    public String toaddCode(Map<String,Object> map,@PathVariable("reward_id")Integer reward_id) {
        map.put("reward",rewardService.findById(reward_id));
        map.put("reward_Id",reward_id);
        return "backend/redeem_codes_input";
    }

    @GetMapping("/editcode/{code_id}")

    @PostMapping("/code")
    public String addCode(RedeemCode redeemCode) {
		System.out.println(redeemCode);
        redeemCodeService.addSingleRedeemCode(redeemCode);
        return "redirect:/code/"+ redeemCode.getReward().getId();
    }
}
