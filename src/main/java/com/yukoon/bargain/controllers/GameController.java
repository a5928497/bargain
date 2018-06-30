package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.RewardService;
import com.yukoon.bargain.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

    /*
    * @return true
    * 一切正常
    * @return null
    * 加入活动失败
    * @return false
    * 用户已经加入过记录了
     */
    @PostMapping("/joinIn")
    public String joinIn(Integer act_id, Map<String,Object> map) {
        Boolean result = null;
        //先验证用户是否登录
        Subject currentUser = SecurityUtils.getSubject();
        String username = (String) currentUser.getPrincipal();
        if (username != null) {
            //若用户已登录
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
                result = null;
            }
        }else {
            //若用户未登录，返回登录页面
        }
        //若加入成功且未开记录，前往奖品选择页面
        List<Reward> rewards = rewardService.findAllByActid(act_id);
        map.put("rewards",rewards);
        return "test/option";
    }

}
