package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.AccessToken;
import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.WeChatConfig;
import com.yukoon.bargain.services.ActivityService;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeChatController {
    @Autowired
    private WeChatService weChatService;
    @Autowired
    private GameService gameService;

    @ResponseBody
    @GetMapping("/getwechatconfig")
    public WeChatConfig getAccessToken(@RequestParam("url")String url,@RequestParam("gameInfo_id")Integer gameInfo_id) {
        WeChatConfig weChatConfig = weChatService.signature(url);
        Activity activity = gameService.findById(gameInfo_id).getActivity();
        weChatConfig.setTitle(activity.getTitle()).setDesc(activity.getDesc());
        return weChatConfig;
    }
}
