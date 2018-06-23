package com.yukoon.bargain.services;

import com.yukoon.bargain.entities.Reward;
import com.yukoon.bargain.repository.RewardRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
public class RewardService {
    @Autowired
    private RewardRepo rewardRepo;

    @Transactional
    public List<Reward> findAllByActid(Integer act_id) {
        return rewardRepo.findAllByActid(act_id);
    }

    @Transactional
    public void saveReward(Reward reward) {
        rewardRepo.saveAndFlush(reward);
    }

    @Transactional
    public void deleteReward(Integer id) {
        rewardRepo.delete(id);
    }

    @javax.transaction.Transactional
    public Reward findById(Integer id) {
        return rewardRepo.findOne(id);
    }
}
