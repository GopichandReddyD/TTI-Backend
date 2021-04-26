package com.ttu.rbt.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.pojo.CategoryResponsePojo;
import com.ttu.rbt.service.AdminService;

@RestController
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	AdminService adminService;

	@PostMapping("/addMainCategory/{mainCategory}")
	public void addMainCategory(@PathVariable("mainCategory") String mainCategory) {

		adminService.addMainCategory(mainCategory);
	}

	@PostMapping("/addMainCategory/{mainCategory}/{subCategory}")
	public void addSubCategory(@PathVariable("mainCategory") String mainCategory,
			@PathVariable("subCategory") String subCategory) {

		adminService.addSubCategory(mainCategory, subCategory);

	}

	@GetMapping("/getAllCategory")
	public ResponseEntity<List<CategoryResponsePojo>> getAll() {
		return new ResponseEntity<>(adminService.getAll(), HttpStatus.OK);

	}

}
