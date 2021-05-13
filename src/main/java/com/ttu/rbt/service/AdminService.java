package com.ttu.rbt.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttu.rbt.entity.MainCategory;
import com.ttu.rbt.entity.SubCategory;
import com.ttu.rbt.pojo.CategoryResponsePojo;
import com.ttu.rbt.repo.MainCategoryRepository;
import com.ttu.rbt.repo.SubCategoryRepository;

@Service
public class AdminService {

	@Autowired
	MainCategoryRepository mainCategoryRepository;

	@Autowired
	SubCategoryRepository subCategoryRepository;

	public void addMainCategory(String mainCategory) {

		MainCategory main = new MainCategory();
		main.setName(mainCategory);
		mainCategoryRepository.save(main);

	}

	public void addSubCategory(String mainCategory, String subCategory) {

		MainCategory main = mainCategoryRepository.findByName(mainCategory);

		SubCategory sub = new SubCategory();
		sub.setName(subCategory);
		sub.setParentId(main.getId());
		subCategoryRepository.save(sub);

	}

	public List<CategoryResponsePojo> getAll() {

		List<CategoryResponsePojo> response = new ArrayList<CategoryResponsePojo>();

		List<MainCategory> mainCategories = mainCategoryRepository.findAll();

		for (MainCategory mainCategory : mainCategories) {
			List<SubCategory> subCategories = subCategoryRepository.findAllByParentId(mainCategory.getId());
			List<String> subCategoryNames = new ArrayList<String>();
			for (SubCategory subCategory : subCategories) {
				subCategoryNames.add(subCategory.getName());
			}
			CategoryResponsePojo categoryResponsePojo = new CategoryResponsePojo();
			categoryResponsePojo.setMainCategory(mainCategory.getName());
			categoryResponsePojo.setSubCategory(subCategoryNames);
			response.add(categoryResponsePojo);

		}

		return response;
	}
	
	public void deleteMainCategory(String mainCategory) {
		mainCategoryRepository.deleteByName(mainCategory);
	}
}
