package com.ttu.rbt.pojo;

import org.springframework.context.annotation.Configuration;

import com.ttu.rbt.entity.Category;

@Configuration
public class Categorypojo {

	private Integer id;

	private String name;

	private Category parent;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Category getParent() {
		return parent;
	}

	public void setParent(Category parent) {
		this.parent = parent;
	}

}
