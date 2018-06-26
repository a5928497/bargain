package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepo extends JpaRepository<User,Integer>{

    //验证用户名是否存在
    @Query("select u.username from User u where u.username = :username")
    public String vaildateUsername(@Param("username") String username);

    @Query("select new User(username,password) from User u where u.username = :username")
    public User login(@Param("username") String username);

    @Query("select new User (id,username,role) from User u where u.username = :username")
    public User findByUsername(@Param("username") String username);
}
