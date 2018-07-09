package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.GameInfo;
import com.yukoon.bargain.entities.Page;
import com.yukoon.bargain.entities.RedeemCode;
import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.repository.GameInfoRepo;
import com.yukoon.bargain.repository.RedeemCodeRepo;
import com.yukoon.bargain.utils.PageableUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class RedeemCodeService {
    @Autowired
    private RedeemCodeRepo redeemCodeRepo;
    @Autowired
    private RewardService rewardService;
    @Autowired
    private GameInfoRepo gameInfoRepo;

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

    //验证某一活动下所有礼品对应的兑换码是否足够发放给所有中奖的客户
    // 若不足够，返回不足礼品的id和数量
    // 若足够，进行批量兑换
    @Transactional
    public Map<Integer,Integer> batchCheckAndCash(Integer act_id) {
        boolean flag = false;
        List<Reward> rewards = rewardService.findAllByActid(act_id);
        Map<Integer,Integer> map = new HashMap<>();
        for (Reward reward : rewards) {
            //获得该礼品对应的未被使用的兑换码数量
            List<RedeemCode> list = redeemCodeRepo.findCodeByRewardId(reward.getId());
            List<RedeemCode> codes_new  = new ArrayList<>();
            for (RedeemCode rc:list) {
                if (rc.getWinner() == null) {
                    codes_new.add(rc);
                }
            }
            Integer code_size = codes_new.size();
            //获得该礼品对应的中奖者数量
            List<GameInfo> winners = gameInfoRepo.whoswin(act_id);
            List<GameInfo> winners_new = new ArrayList<>();
            for (GameInfo gi : winners) {
                if (gi.getReward().getId()==reward.getId() && gi.getRedeemCode() == null) {
                    winners_new.add(gi);
                }
            }
            Integer winners_size = winners_new.size();
            flag = code_size >= winners_size;
            //若兑换券小于中奖用户，不进行兑换，加入返回信息
            if (!flag) {
                map.put(reward.getId(),winners_size-code_size);
            }else {
                //若兑换券大于等于中奖用户，足够兑换，则进行兑换
                for (int i = 0;i<winners_size;i++) {
                    GameInfo gi_temp = winners_new.get(i);
                    RedeemCode rc_temp = codes_new.get(i);
                    gi_temp.setRedeemCode(rc_temp);
                    rc_temp.setWinner(gi_temp.getUser()).setAwardDate(new Date());
                }
                gameInfoRepo.save(winners_new);
                redeemCodeRepo.save(codes_new);
            }
        }
            return map;
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
