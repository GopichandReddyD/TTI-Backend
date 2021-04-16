package com.ttu.rbt.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttu.rbt.entity.FileUpload;
import com.ttu.rbt.pojo.FilesPojo;
import com.ttu.rbt.repo.FileRepository;

@Service
public class FileService {

	@Autowired
	private FileRepository fileRepository;

	private Path uploadLocation;

	public static final String UPLOAD_LOCATION = "/Users/gopichandureddy/Desktop/rbt";

	@PostConstruct
	public void init() {
		this.uploadLocation = Paths.get(UPLOAD_LOCATION);
		try {
			Files.createDirectories(uploadLocation);
		} catch (IOException e) {
			throw new RuntimeException("Could not initialize storage", e);
		}
	}

	public FilesPojo getJson(String fileDetails) {
		FilesPojo filesJson = new FilesPojo();

		try {
			ObjectMapper objectMapper = new ObjectMapper();
			filesJson = objectMapper.readValue(fileDetails, FilesPojo.class);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		return filesJson;
	}

	public FileUpload store(MultipartFile file, FilesPojo fileDetails) throws IOException {

		FileUpload fileUpload = new FileUpload();
		fileUpload.setTitle("test");
		fileRepository.save(fileUpload);

		String filename = fileUpload.getId() + "_" + StringUtils.cleanPath(file.getOriginalFilename());

		try (InputStream inputStream = file.getInputStream()) {

			fileUpload.setTitle(fileDetails.getTitle());
			fileUpload.setDescription(fileDetails.getDescription());
			fileUpload.setMainCategory(fileDetails.getMainCategory());
			fileUpload.setSubCategory(fileDetails.getSubCategory());
			fileUpload.setType(file.getContentType());
			fileUpload.setKeywords(fileDetails.getKeywords());
			fileUpload.setName(filename);
			fileUpload.setDownloads(0);
			fileUpload.setViews(0);

			Path path = this.uploadLocation.resolve(filename);
			Files.copy(inputStream, path, StandardCopyOption.REPLACE_EXISTING);

			fileUpload.setFilePath(path.toString());
			fileUpload.setRef(fileDetails.getRef());

			return fileRepository.save(fileUpload);

		} catch (IOException e) {
			throw new RuntimeException("Failed to store file " + filename, e);
		}
	}

	public List<FileUpload> getAllFiles() {
		List<FileUpload> listFiles = fileRepository.findAll();

		return listFiles;
	}

	public Resource loadAsResource(String filename) {
		try {
			Path file = uploadLocation.resolve(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new RuntimeException("Could not read file: " + filename);
			}
		} catch (MalformedURLException e) {
			throw new RuntimeException("Could not read file: " + filename, e);
		}
	}

	public FileUpload loadFileDetails(String fileTitle) {
		return fileRepository.findByTitle(fileTitle);
	}

	public void downloadCount(String filename) {
		FileUpload fileUpload = fileRepository.findByName(filename);
		Integer downloads = fileUpload.getDownloads();
		fileUpload.setDownloads(downloads + 1);
		fileRepository.save(fileUpload);
	}

	public void viewCount(String fileTitle) {
		FileUpload fileUpload = fileRepository.findByTitle(fileTitle);
		Integer views = fileUpload.getViews();
		fileUpload.setViews(views + 1);
		fileRepository.save(fileUpload);
	}

}
