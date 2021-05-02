package com.ttu.rbt.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ttu.rbt.entity.FileUpload;

public interface FileRepository extends JpaRepository<FileUpload, Integer> {

	FileUpload findByName(String name);

	FileUpload findByTitle(String title);

	FileUpload findByUuid(String uuid);

	Page<FileUpload> findByMainCategory(String mainCategory, Pageable paging);

	Page<FileUpload> findByMainCategoryAndSubCategory(String mainCategory, String subCategory, Pageable paging);

	Page<FileUpload> findByTitleContainingOrKeywordsContaining(String word1, String word2, Pageable paging);

	Page<FileUpload> findByMainCategoryAndTitleContainingOrKeywordsContaining(String mainCategory, String word1,
			String word2, Pageable paging);

	Page<FileUpload> findByMainCategoryAndSubCategoryAndTitleContainingOrKeywordsContaining(String mainCategory,
			String subCategory, String word1, String word2, Pageable paging);
	
	
	@Query("select b.downloads from FileUpload b")
	List<Integer> findAllDownloads();
	
	@Query("select b.views from FileUpload b")
	List<Integer> findAllViews();
	
	


}