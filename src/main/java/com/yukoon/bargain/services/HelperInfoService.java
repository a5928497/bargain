package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.HelperInfo;
import com.yukoon.bargain.repository.HelperInfoRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public List<HelperInfo> getHelpers(Integer gameInfoId) {
        return helperInfoRepo.findAllByGameInfo(gameInfoId);
    }
}
