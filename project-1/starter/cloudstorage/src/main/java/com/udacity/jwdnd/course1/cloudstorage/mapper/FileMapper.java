package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import com.udacity.jwdnd.course1.cloudstorage.model.File;

@Mapper
public interface FileMapper {

	@Insert("INSERT INTO FILES (filename, contenttype, filesize, userid, filedata) VALUES (#{fileName}, #{contentType}, #{fileSize}, #{userId}, #{fileData})")
	@Options( useGeneratedKeys = true, keyProperty = "fileId")
	int insertFile(File file);

	@Select("SELECT * FROM FILES WHERE userid = #{userId}")
	List<File> getFileList(int userId);
	
	@Delete("DELETE FROM FILES WHERE fileId = #{fileId}")
	int deleteFile(int fileId);

	@Select("SELECT * FROM FILES WHERE fileId = #{fileId}")
	File getFile(int fileId);
}
