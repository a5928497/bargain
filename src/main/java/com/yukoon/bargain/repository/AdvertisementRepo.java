package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AdvertisementRepo extends JpaRepository<Advertisement,Integer> {

    @Query("select a from Advertisement a where a.activity.id = :id")
    public List<Advertisement> findAllByActId(@Param("id")Integer id);

    @Query("select * from Advertisement a where a.id = :id")
    public Advertisement findById(@Param("id")Integer id);
}
