package com.ttu.rbt.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.ttu.rbt.entity.FileUpload;
import com.ttu.rbt.repo.FileRepository;

@RestController
public class VideoController {

	@Autowired
	public FileRepository fileRepository;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/upload/video")
	public ResponseEntity<FileUpload> uploadVideo(@RequestBody FileUpload fileUpload) {

		return new ResponseEntity<>(fileRepository.save(fileUpload), HttpStatus.ACCEPTED);

	}

}
