package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.GameInfoService;
import com.yukoon.bargain.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;
import java.util.Map;

@Controller
public class GameInfoController {
	@Autowired
	private GameInfoService gameInfoService;
	@Autowired
	private UserService userService;

	@GetMapping("/myrecords")
	public String myRecords(Map<String,Object> map) {
		Subject subject = SecurityUtils.getSubject();
		if ( subject.isAuthenticated() || subject.isRemembered()) {
			String username = (String) subject.getPrincipal();
			User u = userService.findByUsername(username);
			List<GameInfo> gameInfos = gameInfoService.getAllGameInfoOfUser(u.getId());
			map.put("gameInfos",gameInfos);
		}else {
			return "redirect:/login";
		}
		return "public/user_info";
	}
}
