package com.ttu.rbt.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Categories {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;

	public String mainCategory;

	public String subCategory;

	public Categories() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Categories(Integer id, String mainCategory, String subCategory) {
		super();
		this.id = id;
		this.mainCategory = mainCategory;
		this.subCategory = subCategory;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getMainCategory() {
		return mainCategory;
	}

	public void setMainCategory(String mainCategory) {
		this.mainCategory = mainCategory;
	}

	public String getSubCategory() {
		return subCategory;
	}

	public void setSubCategory(String subCategory) {
		this.subCategory = subCategory;
	}

}
