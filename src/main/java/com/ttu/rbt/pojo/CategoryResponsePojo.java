package com.ttu.rbt.pojo;

import java.util.List;

public class CategoryResponsePojo {

	private String mainCategory;

	private List<String> subCategory;

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public List<String> getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(List<String> subCategory) {
		this.subCategory = subCategory;
	}

}
