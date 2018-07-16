package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.UserService;
import com.yukoon.bargain.utils.EncodeUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class LoginController {

	@Autowired
	private UserService userService;

	//跳转至后台登陆页
	@GetMapping("/backend")
	public String toBackend() {
		return "backend/backend";
	}

	//跳转至登录首页
	@GetMapping("/login")
	public String toLogin(String url, Map<String,Object> map, HttpServletRequest request) {
		if (url != null) {
			map.put("url",url);
		}
		String requestUrl = WebUtils.getSavedRequest(request).getRequestUrl();
		if (requestUrl != null && !requestUrl.equals("")) {
			map.put("url",requestUrl);
		}
		return "public/login";
	}

	@PostMapping("/login")
	public String login(User user,String flag,String url) {
		//获得subject
		Subject currentUser = SecurityUtils.getSubject();
		if(!currentUser.isAuthenticated()){
			//把用户名密码封装为Token对象
			String username = user.getUsername();
			UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, EncodeUtil.encodePassword(user.getPassword(),username));
			//设置token的rememberme
			usernamePasswordToken.setRememberMe(true);
			try {
				//执行登录
				currentUser.login(usernamePasswordToken);
			}catch (AuthenticationException ae){
				System.out.println("登陆失败:"+ae.toString());
				if (flag.equals("bg")) {
					//若是从后台登录，返回backend登录
					return "redirect:/backend";
				}else {
					//若是从前台登录，返回前台登录
					return "redirect:/login";
				}
			}
		}
		user = userService.findByUsername(user.getUsername());
		if (user.getRole().getId() == 2 ) {
			return "redirect:/acts";
		}
		System.out.println();
		return "redirect:"+url;
	}
}
