package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.repository.RedeemCodeRepo;
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
    public List<RedeemCode> findCodeByRewardId(Integer reward_id) {
        return redeemCodeRepo.findCodeByRewardId(reward_id);
    }

    @Transactional
    public RedeemCode findById(Integer id ){
        return redeemCodeRepo.findOne(id);
    }
}
