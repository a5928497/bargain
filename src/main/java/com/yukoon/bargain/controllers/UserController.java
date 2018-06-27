package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Role;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/toregister")
    public String toregister() {
        return "test/signup.html";
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes attributes) {
        if (userService.addUser(user)) {
            attributes.addFlashAttribute("usr_msg","注册成功！");
            //注册成功后自动登录
            Subject currentUser  = SecurityUtils.getSubject();
            if(!currentUser.isAuthenticated()) {
                //把用户名密码封装为Token对象
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(user.getUsername(),user.getPassword());
                //设置token的rememberme
                usernamePasswordToken.setRememberMe(true);
                try {
                    //执行登录
                    currentUser.login(usernamePasswordToken);
                }catch (AuthenticationException ae){
                    System.out.println("登陆失败:"+ae.toString());
                    //若从后台登录，则回后台登录界面
                    return "redirect:/backend";
                }
            }
        }else {
            attributes.addFlashAttribute("usr_msg","用户已存在，请直接登录！");
        }
        return "redirect:/toregister";
    }
}
