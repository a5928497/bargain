package com.yukoon.bargain.controllers;

import com.yukoon.bargain.config.PathConfig;
import com.yukoon.bargain.entities.Advertisement;
import com.yukoon.bargain.services.AdvertisementService;
import com.yukoon.bargain.utils.FileUtil;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Controller
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private PathConfig pathConfig;

    @ModelAttribute
    public void getAdv(@RequestParam(value = "id",required = false)Integer id,Map<String,Object> map) {
        //若为修改
        if (id !=null) {
            map.put("advertisement",advertisementService.findById(id));
        }
    }

    //后台查看某一活动下所有广告
    @RequiresRoles("admin")
    @GetMapping("/advs/{act_id}")
    public String allAdvs(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("advs",advertisementService.findAllByActId(act_id));
        map.put("act_id",act_id);
        return "backend/adv_list";
    }

    //后台前往上传广告信息
    @RequiresRoles("admin")
    @GetMapping("toaddadv/{act_id}")
    public String toAdd(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("act_id",act_id);
        return "backend/adv_upload_input";
    }

    //后台上传广告信息
    @RequiresRoles("admin")
    @PostMapping("/advs")
    public String add(Map<String,Object> map, Advertisement advertisement,RedirectAttributes attributes,
                      @RequestParam("pic")MultipartFile pic, HttpServletRequest request){
        String filePath = pathConfig.getAdvImgPath();
        String fileName = pic.getOriginalFilename();
        String uploadMsg = "图片上传成功!";
        if (!FileUtil.isImg(fileName)){
            uploadMsg = "该文件不是图片格式,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            return "redirect:/toaddadv/"+advertisement.getActivity().getId();
        }
        try {
            advertisement.setClicks(0);
            Advertisement adv = advertisementService.addAdv(advertisement);
            //重命名文件
            fileName = "adv"+adv.getId()+".png";
            //上传图片
            FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
            //压缩图片
            FileUtil.resizeImg(filePath+fileName,200,200);
        }catch (Exception e) {
            uploadMsg = "图片上传出现错误,请重新上传!";
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
            return "redirect:/toaddadv/"+advertisement.getActivity().getId();
        }
        attributes.addFlashAttribute("uploadMsg",uploadMsg);
        return "redirect:/toaddadv/"+advertisement.getActivity().getId();
    }

    //后台前往编辑广告信息
    @RequiresRoles("admin")
    @GetMapping("/toeditadv/{adv_id}")
    public String toEdit(@PathVariable("adv_id")Integer adv_id, Map<String,Object> map) {
        Advertisement adv = advertisementService.findById(adv_id);
        map.put("adv",adv);
        map.put("act_id",adv.getActivity().getId());
        return "backend/adv_upload_input";
    }

    //后台编辑广告信息
    @RequiresRoles("admin")
    @PutMapping("/advs")
    public String update(Map<String,Object> map, Advertisement advertisement,RedirectAttributes attributes,
                      @RequestParam(value = "pic",required = false)MultipartFile pic, HttpServletRequest request){
        if (!pic.getOriginalFilename().equals("")) {
            //若图片名不为空，即不需更新图片
            String filePath = pathConfig.getAdvImgPath();
            String fileName = pic.getOriginalFilename();
            String uploadMsg = "信息及图片上传成功!";
            if (!FileUtil.isImg(fileName)){
                uploadMsg = "该文件不是图片格式,请重新上传!";
                attributes.addFlashAttribute("uploadMsg",uploadMsg);
                return "redirect:/toeditadv/"+advertisement.getId();
            }
            try {
                Advertisement adv = advertisementService.addAdv(advertisement);
                //重命名文件
                fileName = "adv"+adv.getId()+".png";
                //上传图片
                FileUtil.uploadFile(pic.getBytes(),filePath,fileName);
                //压缩图片
                FileUtil.resizeImg(filePath+fileName,200,200);
            }catch (Exception e) {
                uploadMsg = "图片上传出现错误,请重新上传!";
                attributes.addFlashAttribute("uploadMsg",uploadMsg);
                return "redirect:/toeditadv/"+advertisement.getId();
            }
            attributes.addFlashAttribute("uploadMsg",uploadMsg);
        }else {
            //若图片名为空，则无需更新图片，只需更新信息即可
            try {
                advertisementService.addAdv(advertisement);
                attributes.addFlashAttribute("uploadMsg","信息更新成功！");
            }catch (Exception e) {
                attributes.addFlashAttribute("uploadMsg","更新数据失败，请重试！");
                return "redirect:/toeditadv/"+advertisement.getId();
            }
        }
        return "redirect:/toeditadv/"+advertisement.getId();
    }

    //后台删除广告信息
    @RequiresRoles("admin")
    @DeleteMapping("/adv/{adv_id}")
    public String delete(@PathVariable("adv_id")Integer adv_id,Integer act_id,Map<String,Object> map) {
        advertisementService.deleteById(adv_id);
        //清除对应广告图片
        String path = pathConfig.getAdvImgPath() + "adv" + adv_id + ".png";
        FileUtil.delete(path);
        return "redirect:/advs/"+act_id;
    }

    //前台跳转到广告页面并统计点击量
    @GetMapping("/toadv/{adv_id}")
    public String redirect2Adv(@PathVariable("adv_id")Integer adv_id) {
        Advertisement adv = advertisementService.findById(adv_id);
        adv.setClicks(adv.getClicks()+1);
        advertisementService.addAdv(adv);
        return "redirect:http://"+ adv.getAdv_link();
    }

}
