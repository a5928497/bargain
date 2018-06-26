package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface GameInfoRepo extends JpaRepository<GameInfo,Integer>{

//    @Query(value = "select user_id from user_activity where user_id = :user_id and act_id = :act_id")
//    public Integer isJoined(@Param("user_id")Integer user_id,@Param("act_id")Integer act_id);
}
