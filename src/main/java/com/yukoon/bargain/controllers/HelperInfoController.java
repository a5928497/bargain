package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.services.GameInfoService;
import com.yukoon.bargain.services.GameService;
import com.yukoon.bargain.services.HelperInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

@Controller
public class HelperInfoController {
    @Autowired
    private HelperInfoService helperInfoService;
    @Autowired
    private GameService gameService;

    private final static Integer PAGE_SIZE = 10;

    //后台查询某一客户下的帮助者
    @GetMapping("/helpers/{gameInfo_Id}")
    public String getHelpers(@PathVariable("gameInfo_Id")Integer gameInfo_Id,
                             Map<String,Object> map,
                             @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo =1;
        }
        map.put("gameInfo_Id",gameInfo_Id);
        map.put("page",helperInfoService.getPageableHelpers(pageNo,PAGE_SIZE,gameInfo_Id));
        map.put("act_id",gameService.findById(gameInfo_Id).getActivity().getId());
        return "backend/helper_list";
    }

    //后台查询帮助者
    @PostMapping("/findhelper")
    public String searchHelpers(String username,Integer gameinfo_id,Map<String,Object> map,
                                @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo =1;
        }
        Page page = helperInfoService.searchByUsername(pageNo,PAGE_SIZE,gameinfo_id,username);
        map.put("page",page);
        map.put("gameInfo_Id",gameinfo_id);
        map.put("act_id",gameService.findById(gameinfo_id).getActivity().getId());
        return "backend/helper_list";
    }
}
