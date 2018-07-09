package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.services.RedeemCodeService;
import com.yukoon.bargain.services.RewardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Id;
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

    //后台查询某一礼品下的全部兑换券
    @GetMapping("/code/{reward_id}")
    public String findCodeByRewardId(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map,
									 @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = redeemCodeService.findCodeByRewardId(pageNo,PAGE_SIZE,reward_id);
        map.put("act_id",rewardService.findById(reward_id).getActivity().getId());
        map.put("reward_id",reward_id);
        map.put("page",page);
        return "backend/redeem_codes_list";
    }

    //后台查询某一礼品下已发放的兑换券
    @GetMapping("/usedcode/{reward_id}")
    public String findUsedCodeByRewardId(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map,
                                         @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = redeemCodeService.findUsedCodeByRewardId(pageNo,PAGE_SIZE,reward_id);
        map.put("act_id",rewardService.findById(reward_id).getActivity().getId());
        map.put("page",page);
        map.put("reward_id",reward_id);
        return "backend/redeem_codes_list";
    }

    //后台查询某一礼品下未发放的兑换券
    @GetMapping("/unusedcode/{reward_id}")
    public String findUnusedCodeByRewardId(@PathVariable("reward_id")Integer reward_id, Map<String,Object> map,
                                         @RequestParam(value = "pageNo",defaultValue = "1",required = false)Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = redeemCodeService.findUnusedCodeByRewardId(pageNo,PAGE_SIZE,reward_id);
        map.put("act_id",rewardService.findById(reward_id).getActivity().getId());
        map.put("page",page);
        map.put("reward_id",reward_id);
        return "backend/redeem_codes_list";
    }

    //后台前往添加某一礼品下的兑换券
    @GetMapping("/addcode/{reward_id}")
    public String toAddCode(Map<String,Object> map,@PathVariable("reward_id")Integer reward_id) {
        map.put("reward",rewardService.findById(reward_id));
        map.put("reward_Id",reward_id);
        return "backend/redeem_codes_input";
    }

    //后台前往编辑某一礼品下的兑换券
    @GetMapping("/editcode/{code_id}")
    public String toEditCode(Map<String,Object> map,@PathVariable("code_id")Integer code_id) {
        RedeemCode redeemCode = redeemCodeService.findById(code_id);
        map.put("code",redeemCode);
        return "backend/redeem_codes_input";
    }

    //后台添加某一礼品下的兑换券
    @PostMapping("/code")
    public String addCode(RedeemCode redeemCode) {
        redeemCodeService.saveSingleRedeemCode(redeemCode);
        return "redirect:/code/"+ redeemCode.getReward().getId();
    }

    //后台修改某一礼品下的兑换券
    @PutMapping("/code")
    public String editCode(RedeemCode redeemCode) {
        redeemCodeService.saveSingleRedeemCode(redeemCode);
        return "redirect:/code/"+ redeemCode.getReward().getId();
    }

    //后台删除某一礼品下的兑换券
    @DeleteMapping("/code/{code_id}")
    public String deleteCode(@PathVariable("code_id")Integer code_id,Integer reward_id) {
        redeemCodeService.deleteRedeemCode(code_id);
        return "redirect:/code/"+ reward_id;
    }
}
