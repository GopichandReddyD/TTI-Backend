package com.ttu.rbt.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ttu.rbt.entity.FileUpload;

public interface FileRepository extends JpaRepository<FileUpload, Integer> {

	FileUpload findByName(String name);
	
	FileUpload findByTitle(String title);

}