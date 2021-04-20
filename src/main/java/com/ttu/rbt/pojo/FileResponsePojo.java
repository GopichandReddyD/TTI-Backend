package com.ttu.rbt.pojo;

import java.util.List;

import com.ttu.rbt.entity.FileUpload;

public class FileResponsePojo {

	private List<FileUpload> data;

	private Integer totalSize;

	private Integer totalDownloads;

	private Integer totalViews;

	public List<FileUpload> getData() {
		return data;
	}

	public void setData(List<FileUpload> data) {
		this.data = data;
	}

	public Integer getTotalSize() {
		return totalSize;
	}

	public void setTotalSize(Integer totalSize) {
		this.totalSize = totalSize;
	}

	public Integer getTotalDownloads() {
		return totalDownloads;
	}

	public void setTotalDownloads(Integer totalDownloads) {
		this.totalDownloads = totalDownloads;
	}

	public Integer getTotalViews() {
		return totalViews;
	}

	public void setTotalViews(Integer totalViews) {
		this.totalViews = totalViews;
	}

}
