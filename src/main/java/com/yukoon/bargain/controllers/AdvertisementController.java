package com.yukoon.bargain.controllers;

import com.yukoon.bargain.config.PathConfig;
import com.yukoon.bargain.entities.Advertisement;
import com.yukoon.bargain.services.AdvertisementService;
import com.yukoon.bargain.utils.FileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
public class AdvertisementController {
    @Autowired
    private AdvertisementService advertisementService;
    @Autowired
    private PathConfig pathConfig;

    //后台查看某一活动下所有广告
    @GetMapping("/advs/{act_id}")
    public String allAdvs(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("advs",advertisementService.findAllByActId(act_id));
        return "backend/adv_list";
    }

    //后台前往上传广告信息
    @GetMapping("toaddadv/{act_id}")
    public String toAdd(@PathVariable("act_id")Integer act_id, Map<String,Object> map) {
        map.put("act_id",act_id);

        return "backend/adv_upload_input";
    }

    //后台上传广告信息
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
//    @GetMapping("/toeditadv/{adv_id}")
//    public String toEdit(@PathVariable("adv_id")Integer adv_id, Map<String,Object> map) {
//        Advertisement adv = advertisementService.findById(adv_id);
//        map.put("adv",adv);
//        map.put("act_id",adv.getActivity().getId());
//        return "backend/adv_upload_input";
//    }
}
