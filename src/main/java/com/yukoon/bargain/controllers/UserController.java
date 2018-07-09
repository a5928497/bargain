package com.yukoon.bargain.controllers;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.Role;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.GameInfoService;
import com.yukoon.bargain.services.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Map;

@Controller
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private GameInfoService gameInfoService;

    private final static Integer PAGE_SIZE = 10;

    @GetMapping("/toregister")
    public String toregister() {
        return "test/signup.html";
    }

    //后台分页查询所有注册用户
    @GetMapping("/allusers")
    public String findAll(Map<String,Object> map,
                          @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo<1) {
            pageNo = 1;
        }
        Page page = userService.findPageableAll(pageNo,PAGE_SIZE);
        map.put("page",page);
        return "backend/all_user_list";
    }
    //后台分页查询某一注册用户
    @PostMapping("/searchuser")
    public String searchUser(String username,Map<String,Object> map,
                             @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = userService.searchUserByUsername(pageNo,PAGE_SIZE,username);
        map.put("page",page);
        return "backend/all_user_list";
    }

    //后台分页查询某一活动下所有参与的用户
    @GetMapping("/users/{act_id}")
    public String listAllUsers(@PathVariable("act_id")Integer act_id,
                               @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo,
                               Map<String,Object> map) {
    	if (pageNo <1) {
    		pageNo = 1;
		}
        Page page = gameInfoService.getPageableByActid(act_id,pageNo,PAGE_SIZE);
        map.put("page",page);
        map.put("act_id",act_id);
        return "backend/user_list";
    }

    //后台分页搜索某一活动下的用户
    @PostMapping("/finduser")
    public String findUser(String username,Integer act_id,Map<String,Object> map,
                           @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = gameInfoService.searchUser(act_id,pageNo,PAGE_SIZE,username);
        map.put("page",page);
        map.put("act_id",act_id);
        return "backend/user_list";
    }

    //后台分页查询某一活动下全部完成砍价得奖的用户
    @GetMapping("/awards/{act_id}")
    public String findWinners(@PathVariable("act_id")Integer act_id,Map<String,Object> map,
                              @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = gameInfoService.getWinnersByActid(pageNo,PAGE_SIZE,act_id);
        map.put("page",page);
        map.put("act_id",act_id);
        return "backend/winner_list";
    }
    //后台分页查询某一活动下未有兑换券完成砍价得奖的用户
    @GetMapping("/ucawards/{act_id}")
    public String findUncashWinners(@PathVariable("act_id")Integer act_id,Map<String,Object> map,
                              @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = gameInfoService.getUncashWinnersByActid(pageNo,PAGE_SIZE,act_id);
        map.put("page",page);
        map.put("act_id",act_id);
        return "backend/winner_list";
    }
    //后台分页查询某一活动下已有兑换券的完成砍价得奖的用户
    @GetMapping("/cawards/{act_id}")
    public String findcashWinners(@PathVariable("act_id")Integer act_id,Map<String,Object> map,
                              @RequestParam(value = "pageNo",required = false,defaultValue = "1")Integer pageNo) {
        if (pageNo <1) {
            pageNo = 1;
        }
        Page page = gameInfoService.getCashWinnersByActid(pageNo,PAGE_SIZE,act_id);
        map.put("page",page);
        map.put("act_id",act_id);
        return "backend/winner_list";
    }

    @PostMapping("/register")
    public String register(User user, RedirectAttributes attributes, ModelMap modelMap) {
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
