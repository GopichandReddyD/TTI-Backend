package com.ttu.rbt.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.ttu.rbt.entity.FileUpload;
import com.ttu.rbt.entity.User;

public interface FileRepository extends JpaRepository<FileUpload, Integer> {

	FileUpload findByName(String name);
	
}