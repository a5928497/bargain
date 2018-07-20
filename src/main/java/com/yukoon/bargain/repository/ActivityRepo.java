package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface ActivityRepo extends JpaRepository<Activity,Integer>{

    @Query("select a.act_status from Activity a where a.id = :id")
    public Integer getActstatusById(@Param("id")Integer id);

    @Query("select a.userList from Activity a where a.id = :act_id")
    public Set<User> findAllUsersByActid(@Param("act_id")Integer act_id);

    @Query("select a.people_chain from Activity a where a.id = :id")
    public Integer getPeopleChainById(@Param("id")Integer id);
}
