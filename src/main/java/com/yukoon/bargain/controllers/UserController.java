package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Role;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.UserService;
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
        user.setRole(new Role().setId(1));
        if (userService.addUser(user)) {
            attributes.addFlashAttribute("usr_msg","注册成功！");
        }else {
            attributes.addFlashAttribute("usr_msg","用户已存在，请直接登录！");
        }
        return "redirect:/toregister";
    }
}
