package com.ttu.rbt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ttu.rbt.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	User findByMailId(String mailId);
	
	User findByFullName(String fullName);
	
	User findByUuid(String uuid);
	
	User findByRestPasswordToken(String restPasswordToken);
	
}