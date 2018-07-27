package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.AccessToken;
import com.yukoon.bargain.services.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;

@Controller
public class WeChatController {
    @Autowired
    private WeChatService weChatService;

    @ResponseBody
    @GetMapping("/testToken")
    public String getAccessToken() {
        return weChatService.getTicket().toString();
    }
}
