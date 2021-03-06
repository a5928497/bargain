package com.yukoon.bargain.controllers;

import com.yukoon.bargain.config.PathConfig;
import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.HelperInfo;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.services.*;
import com.yukoon.bargain.utils.MatrixToImageWriter;
import com.yukoon.bargain.utils.PictureUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.xml.transform.Source;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
public class GameController {

    @Autowired
    private GameService gameService;
    @Autowired
    private UserService userService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private HelperInfoService helperInfoService;
    @Autowired
    private GameInfoService gameInfoService;
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private PathConfig pathConfig;

    @PostMapping("/joinIn")
    public String joinIn(Integer act_id, Map<String,Object> map, String url) {
        Boolean result = null;
        //先验证用户是否登录和检查礼品数量
        Subject currentUser = SecurityUtils.getSubject();
        List<Reward> rewards = rewardService.findSurplusRewards(act_id);
        if ((currentUser.isAuthenticated() || currentUser.isRemembered()) && rewards.size() > 0 ) {
            //若用户已登录且该活动下礼品充足
            String username = (String) currentUser.getPrincipal();
            Integer user_id = userService.findIdByUsername(username);
            result = gameService.joinIn(user_id,act_id);
            if (result) {
                //如果加入成功，检查客户是否已经加入游戏并新开了记录，防止正常流程外的错误发生
                GameInfo gameInfo = gameService.findByActIdAndUserId(act_id,user_id);
                if (gameInfo != null) {
                    //若用户已经新开记录，前往详情页面
                    map.put("gameInfo",gameInfo);
                    return "redirect:/game/" + gameInfo.getId();
                }
            }else {
                //若加入失败，result为空，返回当前页面
                return "redirect:" + url;
            }
        }else if (rewards.size() <= 0){
            map.put("back_url",url);
            map.put("act_id",act_id);
            return "public/option";
        }else {
            //若用户未登录，返回登录页面
            return "redirect:/login?url="+url;
        }
        //若加入成功且未开记录，前往奖品选择页面
        map.put("back_url",url);
        map.put("act_id",act_id);
        map.put("rewards",rewards);
        return "public/option";
    }

    @PostMapping("/newRrcord")
    public String newRecord(GameInfo gameInfo,RedirectAttributes attributes) {
        //先验证用户是否登录
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            //若用户已登录
            String username = (String) currentUser.getPrincipal();
            User user = userService.findByUsername(username);
            GameInfo gi_temp = gameService.findByActIdAndUserId(gameInfo.getActivity().getId(),user.getId());
            if (null == gi_temp) {
            	//若用户未开记录，则新开记录并作第一次砍价后到活动详情
				gameInfo.setUser(user);
				gameInfo.setReward(rewardService.findById(gameInfo.getReward().getId()));
				//新开记录
                gi_temp = gameService.newRecord(gameInfo);
				//第一次砍价
                if (null != gi_temp) {
                    //新开记录成功
                    HelperInfo hi = new HelperInfo();
                    hi.setGameInfo(gi_temp).setHelper(user);
                    String msg = gameService.bargain(hi);
                    //跳转到活动详情
                    attributes.addFlashAttribute("msg",msg);
                    return "redirect:/game/"+ gi_temp.getId();
                }else {
                 throw new RuntimeException("新开记录失败，请重试！");
                }
			}else {
				//若用户已开记录，则直接到活动详情
                return "redirect:/game/" + gi_temp.getId();
			}
        }else {
        	//若用户未登陆，则跳转到登陆页面
            return "redirect:/login";
		}
    }

    @GetMapping("/bargain/{gameInfoId}")
    public String bargain(@PathVariable("gameInfoId")Integer gameInfoId,RedirectAttributes attributes) {
        //检查用户是否登录
        Subject currentUser = SecurityUtils.getSubject();
        if (currentUser.isAuthenticated() || currentUser.isRemembered()) {
            //若用户已登录，获取当前用户名并进行砍价操作
            String username = (String) currentUser.getPrincipal();
            GameInfo gi = gameService.findById(gameInfoId);
            User user = userService.findByUsername(username);
            Reward reward = rewardService.findById(gi.getReward().getId());
            if (null != gi && null != user && null != reward && reward.getSurplus() >0) {
                //以上三项均不为空且礼品数量大于0
                HelperInfo hi = new HelperInfo();
                hi.setGameInfo(gi).setHelper(user);
                String msg = gameService.bargain(hi);
                attributes.addFlashAttribute("msg",msg);
            }else if (reward.getSurplus() <= 0) {
                attributes.addFlashAttribute("msg","很遗憾，商品已被抢光了，请下次努力！");
            }else {
                attributes.addFlashAttribute("msg","查询不到相应信息，请重试！");
            }
            //砍价完成后返回当前活动页面
            return "redirect:/game/"+gameInfoId;
        }else {
            //若用户未登录
            return "redirect:/login?url=/game/"+gameInfoId;
        }
    }

    /*
    * 前台跳转到抽奖页面
    * status:
    * 0 商品没剩余
    * 1 商品有剩余且砍价未结束
    * 2 砍价已结束
     */
    @GetMapping("/game/{gameInfoId}")
    public String gameInfoWithAllHelpers(@PathVariable("gameInfoId")Integer gameInfoId, Map<String,Object> map,String msg) {
        if (msg != null) {
            map.put("msg",msg);
        }
        List<HelperInfo> helpers = helperInfoService.getHelpers(gameInfoId);
        GameInfo gameInfo = gameService.findById(gameInfoId);
        if (null != gameInfo) {
            Reward reward = rewardService.findById(gameInfo.getReward().getId());
            if (null != reward && gameInfo.getPriceLeft() > 0) {
                //若砍价未结束，检查商品是否有剩余
                if (reward.getSurplus() <= 0 ) {
                    //商品不足，则为0
                    map.put("status",0);
                }else {
                    //商品充足，则为1
                    map.put("status",1);
                    map.put("reward",reward);
                }
            }else {
                //已经砍到商品，则为2
                map.put("status",2);
            }
            map.put("gameInfo",gameInfo);
            map.put("helpers",helpers);
            map.put("gameInfoId",gameInfoId);
            map.put("size",helperInfoService.getAllHelpers(gameInfoId).size());
            map.put("advs",advertisementService.findAllByActId(gameInfo.getActivity().getId()));
        }else {
            throw new RuntimeException("没有找到该活动，请确认后再重试！");
        }
        return "public/details";
    }

    //前台跳转到抽奖页面并显示全部帮砍者
    @GetMapping("/allhelpers/{gameInfoId}")
    public String gameInfo(@PathVariable("gameInfoId")Integer gameInfoId, Map<String,Object> map,String msg) {
        if (msg != null) {
            map.put("msg",msg);
        }
        List<HelperInfo> helpers = helperInfoService.getAllHelpers(gameInfoId);
        map.put("gameInfo",gameService.findById(gameInfoId));
        map.put("helpers",helpers);
        map.put("size",-1);
        return "public/details";
    }

    //前台生成并显示二维码分享图片
    @GetMapping("/share")
    public String share(@RequestParam("url")String url,@RequestParam("gameInfo_id")Integer gameInfo_id,
                        Map<String,Object> map) {
        System.out.println(url);
        GameInfo gameInfo = gameService.findById(gameInfo_id);
        String QRFloder = pathConfig.getShareQRImgPath();
        String QRPath = QRFloder + "QR"+gameInfo_id + ".jpg";
        String rewardPath = pathConfig.getRewardImgsPath() +"reward"+ gameInfo.getReward().getId() + ".png";
        System.out.println(rewardPath);
        String targetPath = QRFloder + "share_basic.jpg";
        try {
            MatrixToImageWriter.writeQR(url,174,"jpg",QRPath);
            PictureUtil.addImageWeatermark(targetPath,QRPath,QRPath,49,493,1);
            PictureUtil.addImageWeatermark(QRPath,rewardPath,QRPath,126,125,1);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(url);
        map.put("gameInfo_id",gameInfo_id);
        return "public/share";
    }


}
