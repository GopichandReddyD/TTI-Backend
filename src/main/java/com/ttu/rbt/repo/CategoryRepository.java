package com.ttu.rbt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttu.rbt.entity.Categories;

public interface CategoryRepository extends JpaRepository<Categories, Integer> {

}