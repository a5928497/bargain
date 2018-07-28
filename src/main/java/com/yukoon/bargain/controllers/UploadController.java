package com.yukoon.bargain.controllers;

import com.yukoon.bargain.config.PathConfig;
import com.yukoon.bargain.services.RewardService;
import com.yukoon.bargain.services.UploadService;
import com.yukoon.bargain.utils.FileUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

@Controller
public class UploadController {
    @Autowired
    private UploadService uploadService;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private PathConfig pathConfig;

    //后台前往礼品图片上传
    @RequiresRoles("admin")
    @GetMapping("/touploadrewardimg/{reward_id}")
    public String toUploadRewardImg(@PathVariable("reward_id")Integer reward_id,
                                    Map<String,Object> map,String uploadMsg) {
        if (uploadMsg != null) {
            map.put("uploadMsg",uploadMsg);
        }
        map.put("act_id",rewardService.findById(reward_id).getActivity().getId());
        map.put("reward_id",reward_id);
        return "backend/reward_img_upload";
    }

    //后台礼品图片上传
    @RequiresRoles("admin")
    @PostMapping("/rewardimgupload")
    public String upload(@RequestParam("pic")MultipartFile pic, HttpServletRequest request
            , Integer reward_id,RedirectAttributes attributes){
        String filePath = pathConfig.getRewardImgsPath();
        String fileName = pic.getOriginalFilename();
        String uploadMsg = "图片上传成功!";
        if (!FileUtil.isImg(fileName)){
            uploadMsg = "该文件不是图片格式,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            return "redirect:/touploadrewardimg/"+reward_id;
        }
        //重命名文件
        fileName = "reward"+reward_id+".png";
        try {
            //上传图片
            FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
            //压缩图片
            FileUtil.resizeImg(filePath+fileName,150,150);
        }catch (Exception e) {
            uploadMsg = "图片上传出现错误,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            return "redirect:/touploadrewardimg/"+reward_id;
        }
        attributes.addFlashAttribute("uploadMsg",uploadMsg);
        return "redirect:/touploadrewardimg/"+reward_id;
    }

    //后台前往兑换券批量上传
    @RequiresRoles("admin")
    @GetMapping("/touploadcode/{act_id}")
    public String toUploadExcel(@PathVariable("act_id") Integer act_id, Map<String,Object> map, HttpServletRequest request) {
        Map<String,?> map1 = RequestContextUtils.getInputFlashMap(request);
        String uploadMsg = null;
        if (map1 != null) {
            uploadMsg = map1.get("uploadMsg").toString();
        }
        if (uploadMsg !=null) {
            map.put("uploadMsg",uploadMsg);
        }
        map.put("act_id",act_id);
        return "backend/code_excel_upload";
    }

    //后台进行兑换码Excel上传
    @RequiresRoles("admin")
    @PostMapping("/uploadcode")
    public String uploadExcel(@RequestParam("excel")MultipartFile excel, Integer act_id, RedirectAttributes attributes) {
        List<String> repeatNames;
        try {
            InputStream in = excel.getInputStream();
            repeatNames = uploadService.importCodeExcel(in,excel,act_id);
            in.close();
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("失败");
            attributes.addFlashAttribute("uploadMsg","上传失败，请重试！");
            return "redirect:/touploadcode/"+act_id;
        }
        System.out.println("成功");
        if (repeatNames.size() != 0 ){
            String uploadMsg ="上传成功！并去除了EXCEL表中重复的:\n";
            for (String s :repeatNames) {
                uploadMsg = uploadMsg + s + ",\n";
            }
            uploadMsg = uploadMsg + "一共"+ repeatNames.size() + "个兑换码";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
        }else {
            attributes.addFlashAttribute("uploadMsg","上传成功！");
        }
        return "redirect:/touploadcode/"+act_id;
    }

    //后台前往分享缩略图上传
    @RequiresRoles("admin")
    @GetMapping("/touploadshareimg/{act_id}")
    public String toUploadShareImg(@PathVariable("act_id")Integer act_id,
                                    Map<String,Object> map,String uploadMsg) {
        if (uploadMsg != null) {
            map.put("uploadMsg",uploadMsg);
        }
        map.put("act_id",act_id);
        return "backend/share_img_upload";
    }

    //后台分享缩略图上传
    @RequiresRoles("admin")
    @PostMapping("/shareimgupload")
    public String uploadShareImg(@RequestParam("pic")MultipartFile pic, HttpServletRequest request
            , Integer act_id,RedirectAttributes attributes){
        String filePath = pathConfig.getShareImgPath();
        String fileName = pic.getOriginalFilename();
        String uploadMsg = "图片上传成功!";
        if (!FileUtil.isImg(fileName)){
            uploadMsg = "该文件不是图片格式,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            return "redirect:/touploadshareimg/"+act_id;
        }
        //重命名文件
        fileName = "share"+act_id+".png";
        try {
            //上传图片
            FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
            //压缩图片
            FileUtil.resizeImg(filePath+fileName,150,150);
        }catch (Exception e) {
            uploadMsg = "图片上传出现错误,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            return "redirect:/touploadshareimg/"+act_id;
        }
        attributes.addFlashAttribute("uploadMsg",uploadMsg);
        return "redirect:/touploadshareimg/"+act_id;
    }
}
