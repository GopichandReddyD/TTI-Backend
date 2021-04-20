package com.ttu.rbt.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ttu.rbt.entity.FileUpload;
import com.ttu.rbt.pojo.FilesPojo;
import com.ttu.rbt.repo.FileRepository;

@Service
public class FileService {

	public static String ALL = "ALL";

	@Autowired
	private FileRepository fileRepository;

	private Path uploadLocation;

	public static final String UPLOAD_LOCATION = "C:/Users/gdoggala/Documents/rbt";

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

			UUID uuid = UUID.randomUUID();
			fileUpload.setUuid(uuid.toString());
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

	public List<FileUpload> getAllFiles(Integer pageNo, Integer pageSize, String sortBy, String mainCategory,
			String subCategory, String search) {

		Pageable paging = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));

		Page<FileUpload> pageResult = null;

		if (search.equals(ALL)) {
			if (mainCategory.equals(ALL)) {
				pageResult = fileRepository.findAll(paging);
			} else {
				if (subCategory.equals(ALL)) {
					pageResult = fileRepository.findByMainCategory(mainCategory, paging);
				} else {
					pageResult = fileRepository.findByMainCategoryAndSubCategory(mainCategory, subCategory, paging);
				}
			}
		} else {
			if (mainCategory.equals(ALL)) {
				pageResult = fileRepository.findByTitleContainingOrKeywordsContaining(search, search, paging);
			} else {
				if (subCategory.equals(ALL)) {
					pageResult = fileRepository.findByMainCategoryAndTitleContainingOrKeywordsContaining(mainCategory,
							search, search, paging);
				} else {
					pageResult = fileRepository.findByMainCategoryAndSubCategoryAndTitleContainingOrKeywordsContaining(
							mainCategory, subCategory, search, search, paging);
				}
			}

		}

		if (pageResult.hasContent()) {
			return pageResult.getContent();

		} else {
			return new ArrayList<FileUpload>();
		}

	}

	public int getTotalSize() {
		return (int) fileRepository.count();
	}

	public int getTotalDownloadCount() {
		List<Integer> list = fileRepository.findAllDownloads();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {

			count = count + list.get(i);
		}
		return count;
	}

	public int getTotalViewsCount() {
		List<Integer> list = fileRepository.findAllViews();
		int count = 0;
		for (int i = 0; i < list.size(); i++) {

			count = count + list.get(i);
		}
		return count;
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

	public FileUpload loadFileDetailsWithUUID(String uuid) {
		return fileRepository.findByUuid(uuid);
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

	public void viewCountWithUUID(String uuid) {

		FileUpload fileUpload = fileRepository.findByUuid(uuid);
		Integer views = fileUpload.getViews();
		fileUpload.setViews(views + 1);
		fileRepository.save(fileUpload);
	}

	public Resource multipleDownload(String[] srcFiles) {
		String zipName = "archive.zip";
		String zipFile = UPLOAD_LOCATION + "/" + zipName;

		try {

			// create byte buffer
			byte[] buffer = new byte[1024];

			FileOutputStream fos = new FileOutputStream(zipFile);

			ZipOutputStream zos = new ZipOutputStream(fos);

			for (int i = 0; i < srcFiles.length; i++) {

				
				File srcFile = new File(UPLOAD_LOCATION + "/" + srcFiles[i]);
				downloadCount(srcFiles[i]);

				FileInputStream fis = new FileInputStream(srcFile);

				// begin writing a new ZIP entry, positions the stream to the start of the entry
				// data
				zos.putNextEntry(new ZipEntry(srcFile.getName()));

				int length;

				while ((length = fis.read(buffer)) > 0) {
					zos.write(buffer, 0, length);
				}

				zos.closeEntry();

				// close the InputStream
				fis.close();

			}

			// close the ZipOutputStream
			zos.close();

			return loadAsResource(zipName);

		} catch (IOException ioe) {
			System.out.println("Error creating zip file: " + ioe);
			return null;
		}
		
	}

}
