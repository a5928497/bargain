package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity,Integer>{

    @Query("select a.act_status from Activity a where a.id = :id")
    public Integer getActstatusById(@Param("id")Integer id);
}
