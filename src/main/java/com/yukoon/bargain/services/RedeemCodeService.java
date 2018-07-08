package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.repository.RedeemCodeRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RedeemCodeService {
    @Autowired
    private RedeemCodeRepo redeemCodeRepo;

    @Transactional
    public void addSingleRedeemCode(RedeemCode redeemCode) {
        redeemCodeRepo.saveAndFlush(redeemCode);
    }

    @Transactional
    public Page findCodeByRewardId(Integer pageNo,Integer pageSize,Integer reward_id) {
        Page page = PageableUtil.page(pageNo,pageSize,redeemCodeRepo.findCodeByRewardId(reward_id));
        return page;
    }

    @Transactional
    public RedeemCode findById(Integer id ){
        return redeemCodeRepo.findOne(id);
    }
}
