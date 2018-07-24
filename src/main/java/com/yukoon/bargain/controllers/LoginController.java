package com.yukoon.bargain.controllers;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.UserService;
import com.yukoon.bargain.utils.EncodeUtil;
import com.yukoon.bargain.utils.KeyUtil;
import com.yukoon.bargain.utils.SmsUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Key;
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
	public String toLogin(@RequestParam(value = "url",required = false,defaultValue = "/myrecords")String url, Map<String,Object> map) {
		System.out.println("url:"+url);
		if (url != null) {
			map.put("url",url);
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
				if (null != flag && flag.equals("bg")) {
					//若是从后台登录，返回backend登录
					return "redirect:/backend";
				}else {
					//若是从前台登录，返回前台登录
					return "redirect:/login";
				}
			}
		}
		user = userService.findByUsername(user.getUsername());
		if (user.getRole().getRoleName().equals("admin") ) {
			return "redirect:/acts";
		}
		return "redirect:"+url;
	}

	@ResponseBody
	@PostMapping("/vaildatecode")
	public boolean getVaildateCode(String phone_number) {
		User user = userService.findDetailsByUsername(phone_number);
		String key = KeyUtil.getKey(4);
		if (null != user) {
			//若已注册手机号
			try {
				//发送验证码
				SendSmsResponse sendSmsResponse = SmsUtil.sendSms(phone_number, key);
				System.out.println(sendSmsResponse.getCode());
				if (sendSmsResponse.getCode().equals("OK")) {
					//若发送成功，修改密码
					user.setPassword(EncodeUtil.encodePassword(key,phone_number));
					userService.saveUser(user);
				}
			}catch (Exception e) {
				return false;
			}
		}else {
			//若手机号未注册，则发送验证码后添加用户
			try {
				//发送验证码
				SendSmsResponse sendSmsResponse = SmsUtil.sendSms(phone_number, key);
				System.out.println(sendSmsResponse.getCode());
				if (sendSmsResponse.getCode().equals("OK")) {
					user = new User().setUsername(phone_number).setPassword(key);
					userService.addUser(user);
				}
			}catch (Exception e) {
				return false;
			}
		}
		return true;
	}

}
