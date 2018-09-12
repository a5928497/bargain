package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface AddressRepo extends JpaRepository<Address,Integer> {

	@Query("select a from Address a where a.gameInfo.id = :gameinfo_id")
	public List<Address> findByGameInfoId(@Param("gameinfo_id")Integer gameinfo_id);
}
