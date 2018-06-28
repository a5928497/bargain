package com.yukoon.bargain.controllers;

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

    @ResponseBody
    @PostMapping("/joinIn")
    public boolean joinIn(Integer act_id,String username) {
        boolean result = gameService.joinIn(userService.findIdByUsername(username),act_id);
        return result;
    }
}
