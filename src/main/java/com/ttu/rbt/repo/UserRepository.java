package com.ttu.rbt.repo;

import org.springframework.data.repository.CrudRepository;

import com.ttu.rbt.entity.User;

public interface UserRepository extends CrudRepository<User, Integer> {

	User findByMailId(String mailId);
	
	User findByName(String name);
}