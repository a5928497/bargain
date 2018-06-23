package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<User,Integer>{
}
