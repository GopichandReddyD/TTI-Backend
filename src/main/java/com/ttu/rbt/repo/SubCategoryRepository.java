package com.ttu.rbt.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttu.rbt.entity.SubCategory;

public interface SubCategoryRepository extends JpaRepository<SubCategory, Integer> {
	
	List<SubCategory> findAllByParentId(int parentId);


}