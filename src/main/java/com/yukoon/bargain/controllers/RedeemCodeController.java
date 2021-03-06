package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Iterator;
import java.util.Map;

@Controller
public class RedeemCodeController {
	private static final Integer PAGE_SIZE = 10;
    @Autowired
    private RedeemCodeService redeemCodeService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private ActivityService activityService;

    @ModelAttribute
    public void getCode(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map) {
        if (id != null) {
            RedeemCode redeemCode = redeemCodeService.findById(id);
            map.put("redeemCode",redeemCode);
        }
    }

    //后台查询某一礼品下的全部兑换券
    @RequiresRoles("admin")
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
        map.put("singal","/code/"+reward_id);
        return "backend/redeem_codes_list";
    }

    //后台查询某一礼品下已发放的兑换券
    @RequiresRoles("admin")
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
        map.put("singal","/usedcode/"+reward_id);
        return "backend/redeem_codes_list";
    }

    //后台查询某一礼品下未发放的兑换券
    @RequiresRoles("admin")
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
        map.put("singal","/unusedcode/"+reward_id);
        return "backend/redeem_codes_list";
    }

    //后台前往添加某一礼品下的兑换券
    @RequiresRoles("admin")
    @GetMapping("/addcode/{reward_id}")
    public String toAddCode(Map<String,Object> map,@PathVariable("reward_id")Integer reward_id) {
        map.put("reward",rewardService.findById(reward_id));
        map.put("reward_Id",reward_id);
        return "backend/redeem_codes_input";
    }

    //后台前往编辑某一礼品下的兑换券
    @RequiresRoles("admin")
    @GetMapping("/editcode/{code_id}")
    public String toEditCode(Map<String,Object> map,@PathVariable("code_id")Integer code_id) {
        RedeemCode redeemCode = redeemCodeService.findById(code_id);
        map.put("code",redeemCode);
        return "backend/redeem_codes_input";
    }

    //后台添加某一礼品下的兑换券
    @RequiresRoles("admin")
    @PostMapping("/code")
    public String addCode(RedeemCode redeemCode) {
        redeemCodeService.saveSingleRedeemCode(redeemCode);
        return "redirect:/code/"+ redeemCode.getReward().getId();
    }

    //后台修改某一礼品下的兑换券
    @RequiresRoles("admin")
    @PutMapping("/code")
    public String editCode(RedeemCode redeemCode) {
        redeemCodeService.saveSingleRedeemCode(redeemCode);
        return "redirect:/code/"+ redeemCode.getReward().getId();
    }

    //后台删除某一礼品下的兑换券
    @RequiresRoles("admin")
    @DeleteMapping("/code/{code_id}")
    public String deleteCode(@PathVariable("code_id")Integer code_id,Integer reward_id) {
        redeemCodeService.deleteRedeemCode(code_id);
        return "redirect:/code/"+ reward_id;
    }

    //后台对某一活动下所有得奖者发放兑换券
    @RequiresRoles("admin")
    @GetMapping("/pushcode/{act_id}")
    public String pushCode(@PathVariable("act_id")Integer act_id, RedirectAttributes attributes) {
        Map<Integer,Integer> result = redeemCodeService.batchCheckAndCash(act_id);
        if (result.size() != 0) {
            Iterator<Map.Entry<Integer,Integer>> it = result.entrySet().iterator();
            String msg = "有"+result.size()+"种礼品因兑换券数量不足没有发放，请补足兑换券后再进行发放。详情如下：\n";
            while (it.hasNext()) {
                Map.Entry<Integer,Integer> entry = it.next();
                msg = msg + "【礼品ID：" + entry.getKey() + "，缺少数量：" + entry.getValue() + "】\n";
            }
            attributes.addFlashAttribute("msg",msg);
        }
        return "redirect:/awards/"+act_id;
    }

    //前台查询某一用户某一活动下的中奖信息
    @ResponseBody
    @GetMapping("/getcodeinfo/{gameinfoId}")
    public String getCodeInfo(@PathVariable("gameinfoId")Integer gameinfoId) {
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        GameInfo gi = gameService.findById(gameinfoId);
        String result = "您的查询信息有误，请更正后重新查询！";
        if (null != gi && null != username && gi.getPriceLeft() <= 0) {
            //检查登录信息与查询是否一致，防止盗查
            RedeemCode redeemCode = redeemCodeService.findByWinnerAndAct(gi.getUser().getId(),gi.getActivity().getId());
            User user = userService.findByUsername(username);
//             && null != user && redeemCode.getWinner().getId() == user.getId()
            if (null != redeemCode){
                //获取该条记录奖品的兑奖信息，若礼品有独立的兑奖信息，则获取该礼品的兑奖信息
                result = rewardService.findById(gi.getReward().getId()).getTips();
                if (null == result || "".equals(result)) {
                    //若该礼品没有独立的兑奖信息，则获取该活动的通用兑奖信息
                    result = activityService.findById(gi.getActivity().getId()).getCashingInfo();
                }
                result = "您的兑换码为：" + redeemCode.getCode() + "，兑奖方式：" +result;
            }else {
                result = "恭喜您已获奖，活动结束将有专人跟你联系，烦请等待！";
            }
        }
        return result;
    }
}
