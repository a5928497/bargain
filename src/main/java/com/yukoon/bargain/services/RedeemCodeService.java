package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.repository.RedeemCodeRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class RedeemCodeService {
    @Autowired
    private RedeemCodeRepo redeemCodeRepo;

    @Transactional
    public void saveSingleRedeemCode(RedeemCode redeemCode) {
        redeemCodeRepo.saveAndFlush(redeemCode);
    }

    @Transactional
    public Page findCodeByRewardId(Integer pageNo,Integer pageSize,Integer reward_id) {
        Page page = PageableUtil.page(pageNo,pageSize,redeemCodeRepo.findCodeByRewardId(reward_id));
        return page;
    }

    @Transactional
    public Page findUsedCodeByRewardId(Integer pageNo,Integer pageSize,Integer reward_id) {
        List<RedeemCode> list = redeemCodeRepo.findCodeByRewardId(reward_id);
        List<RedeemCode> list_new  = new ArrayList<>();
        for (RedeemCode rc:list) {
            if (rc.getWinner() != null) {
                list_new.add(rc);
            }
        }
        return PageableUtil.page(pageNo,pageSize,list_new);
    }

    @Transactional
    public Page findUnusedCodeByRewardId(Integer pageNo,Integer pageSize,Integer reward_id) {
        List<RedeemCode> list = redeemCodeRepo.findCodeByRewardId(reward_id);
        List<RedeemCode> list_new  = new ArrayList<>();
        for (RedeemCode rc:list) {
            if (rc.getWinner() == null) {
                list_new.add(rc);
            }
        }
        return PageableUtil.page(pageNo,pageSize,list_new);
    }

    @Transactional
    public RedeemCode findById(Integer id ){
        return redeemCodeRepo.findOne(id);
    }

    @Transactional
    public void deleteRedeemCode(Integer id) {
        redeemCodeRepo.delete(id);
    }
}
