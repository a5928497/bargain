package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.WeChatConfig;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.persistence.Id;

public interface WeChatConfigRepo extends JpaRepository<WeChatConfig,Integer>{
}
