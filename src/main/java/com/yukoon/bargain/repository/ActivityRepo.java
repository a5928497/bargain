package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ActivityRepo extends JpaRepository<Activity,Integer>{

}
