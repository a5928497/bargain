package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.GameInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameInfoRepo extends JpaRepository<GameInfo,Integer>{
}
