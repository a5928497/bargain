package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameInfoRepo extends JpaRepository<GameInfo,Integer>{

	@Query("select g from GameInfo g where g.activity.id = :act_id and g.user.id = :user_id")
	public GameInfo findGameInfoByActIdAndUserId(@Param("act_id") Integer act_id,@Param("user_id")Integer user_id);
}
