package com.ttu.rbt.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ttu.rbt.entity.FileUpload;
import com.ttu.rbt.pojo.FilesPojo;
import com.ttu.rbt.service.FileService;

@RestController
public class FileController {

	@Autowired
	public FileService fileService;

	@CrossOrigin(origins = "http://localhost:4200")
	@PostMapping("/upload/file")
	public ResponseEntity<FileUpload> uploadFile(@RequestPart("file") MultipartFile file,
			@RequestPart("fileDetails") String fileDetails) throws IOException {

		FilesPojo files = fileService.getJson(fileDetails);
		return new ResponseEntity<>(fileService.store(file, files), HttpStatus.ACCEPTED);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getAllFiles")
	public ResponseEntity<List<FileUpload>> getAllFiles() {
		List<FileUpload> listFiles = fileService.getAllFiles();
		return new ResponseEntity<>(listFiles, HttpStatus.OK);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFile/{name}")
	public ResponseEntity<Resource> getFile(@PathVariable("name") String fileName) throws IOException {

		fileService.downloadCount(fileName);
		Resource file = fileService.loadAsResource(fileName);
		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION).body(file);
	}

	@CrossOrigin(origins = "http://localhost:4200")
	@GetMapping("/getFileDetails/{title}")
	public ResponseEntity<FileUpload> getFileDetails(@PathVariable("title") String fileTitle) {

		fileService.viewCount(fileTitle);
		return new ResponseEntity<>(fileService.loadFileDetails(fileTitle), HttpStatus.OK);
	}

}
