package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.HelperInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HelperInfoRepo extends JpaRepository<HelperInfo,Integer> {

    @Query("select h from HelperInfo h where h.gameInfo.id = :gameInfoId")
    public List<HelperInfo> findAllByGameInfo(@Param("gameInfoId") Integer gameInfoId);

    @Query("select h from HelperInfo h where h.gameInfo.id = :gameInfoId and h.helper.id = :helper_id")
    public HelperInfo hadBargain(@Param("gameInfoId")Integer gameInfoId,@Param("helper_id")Integer helper_id);
}
