package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.WeChatConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.persistence.Id;
import java.util.List;

public interface WeChatConfigRepo extends JpaRepository<WeChatConfig,Integer>{

    @Query("select w from WeChatConfig w where w.act_id = :act_id")
    public List<WeChatConfig> findByAct_id(@Param("act_id")Integer act_id);
}
