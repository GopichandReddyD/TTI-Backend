package com.ttu.rbt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttu.rbt.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	
	Category findAllByName(String mainCategory);
	
	List<Category> findAllByParentId(int parent);

}