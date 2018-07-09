package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.HelperInfo;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.*;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private HelperInfoService helperInfoService;
    @Autowired
    private GameInfoService gameInfoService;


    /*
    * @return true
    * 一切正常
    * @return null
    * 加入活动失败
    * @return false
    * 用户已经加入过记录了
     */
    @PostMapping("/joinIn")
    public String joinIn(Integer act_id, Map<String,Object> map, String url) {
        Boolean result = null;
        //先验证用户是否登录
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            //若用户已登录
            String username = (String) currentUser.getPrincipal();
            Integer user_id = userService.findIdByUsername(username);
            result = gameService.joinIn(user_id,act_id);
            if (result) {
                //如果加入成功，检查客户是否已经加入游戏并新开了记录，防止正常流程外的错误发生
                GameInfo gameInfo = gameService.findByActIdAndUserId(act_id,user_id);
                if (gameInfo != null) {
                    //若用户已经新开记录，前往详情页面
                    map.put("gameInfo",gameInfo);
                    result = false;
                }
            }else {
                //若加入失败，result为空，返回当前页面
                return "redirect:" + url;
            }
        }else {
            //若用户未登录，返回登录页面
            return "redirect:/login";
        }
        //若加入成功且未开记录，前往奖品选择页面
        List<Reward> rewards = rewardService.findAllByActid(act_id);
        map.put("act_id",act_id);
        map.put("rewards",rewards);
        return "test/option";
    }

    @PostMapping("/newRrcord")
    public String newRecord(GameInfo gameInfo,RedirectAttributes attributes) {
        //先验证用户是否登录
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            //若用户已登录
            String username = (String) currentUser.getPrincipal();
            User user = userService.findByUsername(username);
            GameInfo gi_temp = gameService.findByActIdAndUserId(gameInfo.getActivity().getId(),user.getId());
            if (gi_temp == null) {
            	//若用户未开记录，则新开记录并作第一次砍价后到活动详情
				gameInfo.setUser(user);
				gameInfo.setReward(rewardService.findById(gameInfo.getReward().getId()));
				//新开记录
				gameService.newRecord(gameInfo);
				//第一次砍价
                gi_temp = gameService.findByActIdAndUserId(gameInfo.getActivity().getId(),user.getId());
                HelperInfo hi = new HelperInfo();
                hi.setGameInfo(gi_temp).setHelper(user);
                String msg = gameService.bargain(hi);
                //跳转到活动详情
                attributes.addFlashAttribute("msg",msg);
                return "redirect:/game/"+ gi_temp.getId();
			}else {
				//若用户已开记录，则直接到活动详情
                return "redirect:/game/" + gi_temp.getId();
			}
        }else {
        	//若用户未登陆，则跳转到登陆页面
            return "redirect:/login";
		}
    }

    @GetMapping("/bargain/{gameInfoId}")
    public String bargain(@PathVariable("gameInfoId")Integer gameInfoId,RedirectAttributes attributes) {
        //检查用户是否登录
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            //若用户已登录，获取当前用户吧并进行砍价操作
            String username = (String) currentUser.getPrincipal();
            GameInfo gi = gameService.findById(gameInfoId);
            User user = userService.findByUsername(username);
            HelperInfo hi = new HelperInfo();
            hi.setGameInfo(gi).setHelper(user);
            String msg = gameService.bargain(hi);
            attributes.addFlashAttribute("msg",msg);
            //砍价完成后返回当前活动页面
            return "redirect:/game/"+gameInfoId;
        }else {
            //若用户未登录
            return "redirect:/login";
        }
    }

    //前台跳转到抽奖页面
    @GetMapping("/game/{gameInfoId}")
    public String gameInfo(@PathVariable("gameInfoId")Integer gameInfoId, Map<String,Object> map,String msg) {
        if (msg != null) {
            map.put("msg",msg);
        }
        map.put("gameInfo",gameService.findById(gameInfoId));
        map.put("helpers",helperInfoService.getHelpers(gameInfoId));
        return "test/details";
    }


}
