package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Activity;
import com.yukoon.bargain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface UserRepo extends JpaRepository<User,Integer>{

    //验证用户名是否存在
    @Query("select u.username from User u where u.username = :username")
    public String vaildateUsername(@Param("username") String username);

    @Query("select new User(username,password) from User u where u.username = :username")
    public User login(@Param("username") String username);

    @Query("select new User (id,username,role) from User u where u.username = :username")
    public User findByUsername(@Param("username") String username);

    @Query("select u from  User u where u.username = :username")
    public User findAllDetailsByUsername(@Param("username")String username);

    @Query("select u.actList from User u where u.username = :username")
    public Set<Activity> findActsByUsername(@Param("username") String username);

    @Query("select u.id from User u where u.username = :username")
    public Integer findIdByUsername(@Param("username")String username);

    @Query("select u from User u where u.username like :username")
    public List<User> seachUserByUsername(@Param("username")String username);


}
