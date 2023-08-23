package com.udacity.jwdnd.course1.cloudstorage.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Mapper
public interface CredentialMapper {

	@Insert("INSERT INTO CREDENTIALS (url, username, key, password, userid)  VALUES (#{url}, #{username}, #{key}, #{password}, #{userId})")
	@Options(useGeneratedKeys = true, keyProperty = "credentialId")
	int insertCredential(Credential credential);

	@Select("SELECT * FROM CREDENTIALS WHERE userid = #{userId}")
	List<Credential> getCredentialList(int userId);
	
	@Update("UPDATE CREDENTIALS SET url = #{url}, username = #{username} , key = #{key}, password = #{password}, userid = #{userId} WHERE credentialid = #{credentialId}")
	int updateCredential(Credential credential);

	@Delete("DELETE FROM CREDENTIALS WHERE credentialid = #{credentialId}")
	int deleteCredential(int credentialId);

	@Select("SELECT * FROM CREDENTIALS WHERE credentialid = #{credentialId}")
	Credential getCredential(int credentialId);

	@Select("SELECT 1 FROM CREDENTIALS WHERE userid = #{userId} and username = #{username}")
	Integer isExistCredential(String username, Integer userId);
}
