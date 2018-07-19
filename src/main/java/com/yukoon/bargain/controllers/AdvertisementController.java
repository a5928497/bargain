package com.yukoon.bargain.controllers;

import com.yukoon.bargain.services.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Map;

@Controller
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;

    //查看某一活动下所有广告
    @GetMapping("/advs/{act_id}")
    public String allAdvs(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("advs",advertisementService.findAllByActId(act_id));
        return "backend/adv_list";
    }
}
