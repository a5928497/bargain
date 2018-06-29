package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;

    /*
    * @return true
    * 一切正常
    * @return null
    * 加入活动失败
    * @return false
    * 用户已经加入过记录了
     */
    @ResponseBody
    @PostMapping("/joinIn")
    public Boolean joinIn(Integer act_id,String username) {
        Integer user_id = userService.findIdByUsername(username);
        Boolean result = gameService.joinIn(user_id,act_id);
        if (result) {
            //如果加入成功，检查客户是否已经加入游戏并新开了记录，防止正常流程外的错误发生
            GameInfo gameInfo = gameService.findByActIdAndUserId(act_id,user_id);
            if (gameInfo != null) {
                //若用户已经新开记录
                result = false;
            }
        }else {
            //若加入失败，result为空
            result = null;
        }
        return result;
    }

}
