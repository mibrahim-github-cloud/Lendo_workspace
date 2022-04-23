package com.genesyslab.user.api.data;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<UserEntity, Long>{

	UserEntity findByEmail(String email);
	
	UserEntity findByUserId(String userId);
	
	@Modifying
	@Transactional
    @Query("Update UserEntity u SET u.lastLogin=:lastLogin WHERE u.email=:email")
    public void updateLastLogin(@Param("lastLogin") String lastLogin, @Param("email") String email);
	
	
}
