package com.yukoon.bargain.repository;

import com.yukoon.bargain.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PermissionRepo extends JpaRepository<Permission,Integer> {

	@Query("select p.permName from Permission p where p.role.id = :role_id")
	public List<String> findPermNameByRoleid(@Param("role_id")Integer role_id);
}
