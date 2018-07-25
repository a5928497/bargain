package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.RedeemCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RedeemCodeRepo extends JpaRepository<RedeemCode,Integer> {

    @Query("select r from RedeemCode r where r.reward.id = :reward_id")
    public List<RedeemCode> findCodeByRewardId(@Param("reward_id")Integer reward_id);

    @Query("select r from RedeemCode r where r.code = :code")
    public List<RedeemCode> findCodeByCode(@Param("code")String code);

    @Query("select r from RedeemCode r where r.winner.id = :winner_id and r.activity.id = :act_id")
    public RedeemCode findByWinnerAndAct(@Param("winner_id")Integer winner_id,@Param("act_id")Integer act_id);
}
