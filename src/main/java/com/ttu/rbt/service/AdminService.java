package com.ttu.rbt.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ttu.rbt.entity.Category;
import com.ttu.rbt.entity.MainCategory;
import com.ttu.rbt.entity.SubCategory;
import com.ttu.rbt.pojo.CategoryResponsePojo;
import com.ttu.rbt.pojo.Categorypojo;
import com.ttu.rbt.repo.CategoryRepository;
import com.ttu.rbt.repo.MainCategoryRepository;
import com.ttu.rbt.repo.SubCategoryRepository;

@Service
public class AdminService {

	@Autowired
	CategoryRepository categoryRepository;

	@Autowired
	MainCategoryRepository mainCategoryRepository;

	@Autowired
	SubCategoryRepository subCategoryRepository;

	@Autowired
	Categorypojo categorypojo;

	public void addMainCategory(String mainCategory) {

//		Category main = new Category();
//		main.setName(mainCategory);
//		categoryRepository.save(main);
		MainCategory main = new MainCategory();
		main.setName(mainCategory);
		mainCategoryRepository.save(main);

	}

	public void addSubCategory(String mainCategory, String subCategory) {

//		Category main = categoryRepository.findAllByName(mainCategory);
//		Category sub = new Category(subCategory, main);
//		categoryRepository.save(sub);

		MainCategory main = mainCategoryRepository.findByName(mainCategory);

		SubCategory sub = new SubCategory();
		sub.setName(subCategory);
		sub.setParentId(main.getId());
		subCategoryRepository.save(sub);

	}

	public List<CategoryResponsePojo> getAll() {

		/*
		 * Category electronics = session.get(Category.class, 1);
		 * 
		 * Set<Category> children = electronics.getChildren();
		 * 
		 * System.out.println(electronics.getName());
		 * 
		 * for (Category child : children) { System.out.println("--" + child.getName());
		 * printChildren(child, 1); }
		 */
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

	private static void printChildren(Category parent, int subLevel) {
		Set<Category> children = parent.getChildren();

		for (Category child : children) {
			for (int i = 0; i <= subLevel; i++)
				System.out.print("--");

			System.out.println(child.getName());

			printChildren(child, subLevel + 1);
		}
	}
}
