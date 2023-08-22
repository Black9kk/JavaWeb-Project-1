package com.udacity.jwdnd.course1.cloudstorage.services;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Service
public class FileService {

	@Autowired
	private FileMapper fileMapper;

	// add file
	public int insertFile(MultipartFile file, int userId) {
		String fileName = file.getOriginalFilename();
		String contentType = file.getContentType();
		String fileSize = String.valueOf(file.getSize());
		byte[] fileData = null;
		File fileInsert = null;
		if ("".equals(fileName) && "0".equals(fileSize)) {
			return 0;
		}
		try {
			fileData = file.getBytes();
			fileInsert = new File(fileName, contentType, fileSize, userId, fileData);
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		}

		
		return fileMapper.insertFile(fileInsert);
	}

	// get list file
	public List<File> getFileList(int userId) {
		return fileMapper.getFileList(userId);
	}

	// delete list file
	public int deleteFile(int fileId) {
		return fileMapper.deleteFile(fileId);
	}

	// get file by id
	public File getFile(int fileId) {
		return fileMapper.getFile(fileId);
	}
}
