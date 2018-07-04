package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface GameInfoRepo extends JpaRepository<GameInfo,Integer>{

	@Query("select g from GameInfo g where g.activity.id = :act_id and g.user.id = :user_id")
	public GameInfo findGameInfoByActIdAndUserId(@Param("act_id") Integer act_id,@Param("user_id")Integer user_id);

	public List<GameInfo> findGameInfoByActivity_Id(Integer act_id);

	@Query("select g from GameInfo g where g.user.username like :username and g.activity.id = :act_id")
	public List<GameInfo> searchGameInfoByUsernameAndActid(@Param("username")String username,@Param("act_id")Integer act_id);

	@Query("select g from GameInfo g where  g.activity.id = :act_id and g.priceLeft = 0.00")
	public List<GameInfo> whoswin(@Param("act_id")Integer act_id);
}
