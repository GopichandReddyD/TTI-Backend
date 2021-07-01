package com.ttu.rbt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttu.rbt.entity.MainCategory;

public interface MainCategoryRepository extends JpaRepository<MainCategory, Integer> {

	MainCategory findByName(String name);
	
	void deleteByName(String name);
	

}