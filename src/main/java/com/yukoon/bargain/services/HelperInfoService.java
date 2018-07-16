package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.HelperInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.repository.HelperInfoRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HelperInfoService {
    @Autowired
    private HelperInfoRepo helperInfoRepo;

    @Transactional
    public boolean hadBargain(Integer gameInfoId,Integer helper_id) {
        return (helperInfoRepo.hadBargain(gameInfoId,helper_id) != null);
    }

    @Transactional
    public List<HelperInfo> getAllHelpers(Integer gameInfoId) {
        return helperInfoRepo.findAllByGameInfo(gameInfoId);
    }

    @Transactional
    public List<HelperInfo> getHelpers(Integer gameInfoId) {
        List<HelperInfo> list = new ArrayList<>();
        List<HelperInfo> helpers = helperInfoRepo.findAllByGameInfo(gameInfoId);
        if (helpers.size() > 3) {
            for (int i = 0;i<3 ;i++) {
                list.add(helpers.get(i));
            }
            return list;
        }else {
            return helpers;
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
