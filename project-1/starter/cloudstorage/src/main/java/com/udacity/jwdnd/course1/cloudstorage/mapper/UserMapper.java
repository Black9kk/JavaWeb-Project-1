package com.udacity.jwdnd.course1.cloudstorage.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Mapper
public interface UserMapper {

	@Insert("INSERT INTO USERS (username, salt, password, firstname, lastname) VALUES (#{username}, #{salt}, #{password}, #{firstName}, #{lastName})")
	@Options(useGeneratedKeys = true, keyProperty = "userId")
	int createUser(User user);


	@Select("SELECT * FROM USERS WHERE username = #{username}")
	User getUser(String username);

	@Update("UPDATE USERS SET userid = #{userId}, username = #{username}, salt = #{salt}, password = #{password}, firstname = #{firstName}, lastname = #{lastName}")
	int updateUser(User user);
	
	@Delete("DELETE FROM USERS WHERE userid = #{userId}")
	int deleteUser(int userId);
}
