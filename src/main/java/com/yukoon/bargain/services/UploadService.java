package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.repository.RedeemCodeRepo;
import com.yukoon.bargain.utils.ExcelUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class UploadService {

    @Autowired
    private RedeemCodeRepo redeemCodeRepo;

    public List<String> importCodeExcel(InputStream in, MultipartFile file, Integer act_id) throws Exception {
        List<List<Object>> listob = ExcelUtil.getUserstByExcel(in,file.getOriginalFilename());
        List<RedeemCode> redeemCodes  = new ArrayList<>();
        List<String> repeatCode  = new ArrayList<>();
        for (int i = 0;i<listob.size();i++) {
            boolean flag = false;
            List<Object> ob = listob.get(i);
            RedeemCode redeemCode = new RedeemCode();
            String code = ob.get(0).toString();
            Integer actId = Integer.valueOf(ob.get(1).toString());
            Integer RewardId = Integer.valueOf(ob.get(2).toString());
            redeemCode.setCode(code).setActivity(new Activity().setId(actId)).setReward(new Reward().setId(RewardId));
            //excel表去重
            for(RedeemCode temp:redeemCodes) {
                flag = temp.getCode().equals(redeemCode.getCode());
                if (flag == true) {
                    break;
                }
            }
            if (flag == false) {
                redeemCodes.add(redeemCode);
            }else {
                repeatCode.add(redeemCode.getCode());
            }
        }
        //查询数据库去重
        for (int i = 0;i<redeemCodes.size();) {
            RedeemCode temp = redeemCodes.get(i);
            if (redeemCodeRepo.findCodeByCode(temp.getCode()).size() != 0) {
                System.out.println("有重复");
                repeatCode.add(temp.getCode());
                redeemCodes.remove(temp);
            }else {
                i++;
            }
        }
        if (redeemCodes.size() !=0) {
            redeemCodeRepo.save(redeemCodes);
        }
        return repeatCode;
    }
}
