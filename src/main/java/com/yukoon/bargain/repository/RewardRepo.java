package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface RewardRepo extends JpaRepository<Reward,Integer>{

	@Query("select r from Reward r where act_id = :act_id")
	public List<Reward> findAllByActid(@Param("act_id") Integer act_id);

	@Query("select r from Reward r where act_id = :act_id and surplus > 0")
	public List<Reward> findAllSurplusRewardsByActid(@Param("act_id")Integer act_id);
}
