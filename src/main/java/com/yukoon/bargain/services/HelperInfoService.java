package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.HelperInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.User;
import com.yukoon.bargain.repository.HelperInfoRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Service
public class HelperInfoService {
    @Autowired
    private HelperInfoRepo helperInfoRepo;

    @Transactional
    public boolean hadBargain(Integer gameInfoId,Integer helper_id) {
        return (helperInfoRepo.hadBargain(gameInfoId,helper_id) != null);
    }

    public List<HelperInfo> getAllHelpers(Integer gameInfoId) {
        List<HelperInfo> list = helperInfoRepo.findAllByGameInfo(gameInfoId);
        List<HelperInfo> maskedList = new ArrayList<>();
        //遮盖真实号码,保留四位尾号
        for (HelperInfo hi : list) {
            HelperInfo temp = new HelperInfo();
            String username = hi.getHelper().getUsername();
            String masked;
            if (username.length() <11) {
                masked = username.substring(0,username.length()-2) + "**";
            }else {
                masked = username.substring(0,3) + "****" + username.substring(7,11);
            }
            temp.setId(hi.getId()).setBarginPrice(hi.getBarginPrice()).setGameInfo(hi.getGameInfo())
                    .setHelper(new User().setUsername(masked));
            maskedList.add(temp);
        }
        //倒序排列
        Collections.sort(maskedList, new Comparator<HelperInfo>() {
            @Override
            public int compare(HelperInfo o1, HelperInfo o2) {
                return o2.getId().compareTo(o1.getId());
            }
        });
        return maskedList;
    }

    public List<HelperInfo> getHelpers(Integer gameInfoId) {
        List<HelperInfo> maskedList = new ArrayList<>();
        List<HelperInfo> helpers = helperInfoRepo.findAllByGameInfo(gameInfoId);
        //遮盖真实号码,保留四位尾号
        for (HelperInfo hi : helpers) {
            HelperInfo temp = new HelperInfo();
            String username = hi.getHelper().getUsername();
            String masked;
            if (username.length() <11) {
                masked = username.substring(0,username.length()-2) + "**";
            }else {
                masked = username.substring(0,3) + "****" + username.substring(7,11);
            }
            temp.setId(hi.getId()).setBarginPrice(hi.getBarginPrice()).setGameInfo(hi.getGameInfo())
                    .setHelper(new User().setUsername(masked));
            maskedList.add(temp);
        }
        List<HelperInfo> list = new ArrayList<>();
        if (maskedList.size() > 3) {
            for (int i = 1;i<4 ;i++) {
                list.add(maskedList.get(maskedList.size()-i));
            }
            return list;
        }else {
            return maskedList;
        }
    }

    @Transactional
    public Page getPageableHelpers(Integer pageNo,Integer pageSize,Integer gameInfoId) {
        return PageableUtil.page(pageNo,pageSize,helperInfoRepo.findAllByGameInfo(gameInfoId));
    }

    public Page searchByUsername(Integer pageNo,Integer pageSize,Integer gameInfoId,String username) {
        username = "%"+username+"%";
        return PageableUtil.page(pageNo,pageSize,helperInfoRepo.searchByGameInfoIdAndUsername(gameInfoId,username));
    }
}
