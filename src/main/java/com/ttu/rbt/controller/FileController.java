package com.ttu.rbt.controller;

import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import com.ttu.rbt.entity.FileUpload;
import com.ttu.rbt.pojo.FilesPojo;
import com.ttu.rbt.pojo.HRefModel;
import com.ttu.rbt.service.FileService;

@RestController
public class FileController {

	@Autowired
	public FileService fileService;

	@PostMapping("/upload")
	public ResponseEntity<FileUpload> uploadFile(@RequestPart("file") MultipartFile file,
			@RequestPart("fileDetails") String fileDetails) throws IOException {

		FilesPojo files = fileService.getJson(fileDetails);
		return new ResponseEntity<>(fileService.store(file, files), HttpStatus.ACCEPTED);
	}

	@GetMapping("/getAllFiles")
	public ResponseEntity<List<FileUpload>> getAllFiles() {
		List<FileUpload> listFiles = fileService.getAllFiles();
		return new ResponseEntity<>(listFiles, HttpStatus.OK);
	}
	
	@GetMapping("/getFile/{name}")
	public ResponseEntity<Resource> getFile(@PathVariable("name") String fileName) throws IOException
	{
		
		Resource file = fileService.loadAsResource(fileName);
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
				.body(file);
	}

	@RequestMapping(value = "/files/list", method = RequestMethod.GET)
	public String listFiles(Model model) {
		List<Path> lodf = new ArrayList<>();
		List<HRefModel> uris = new ArrayList<>();

		try {
			lodf = fileService.listSourceFiles(fileService.getUploadLocation());
			for (Path pt : lodf) {
				HRefModel href = new HRefModel();
				href.setHref(MvcUriComponentsBuilder
						.fromMethodName(FileController.class, "serveFile", pt.getFileName().toString()).build()
						.toString());

				href.setHrefText(pt.getFileName().toString());
				uris.add(href);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		model.addAttribute("listOfEntries", uris);
		return "file_list :: urlFileList";
	}

}
