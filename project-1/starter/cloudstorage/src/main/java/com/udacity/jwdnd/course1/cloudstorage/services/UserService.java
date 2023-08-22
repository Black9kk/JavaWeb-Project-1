package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.Base64;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HashService hashService;

	public int createUser(User user) {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		String encodedKey = Base64.getEncoder().encodeToString(key);
		String hashPassWord = hashService.getHashedValue(user.getPassword(), encodedKey);
		User addedUser = new User(0, user.getUsername(), encodedKey, hashPassWord, user.getFirstName(), user.getLastName());
		int result = 0;
		try {
			result = userMapper.createUser(addedUser);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	public User getUser(String username) {
		User user = null;
		try {
			user = userMapper.getUser(username);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return user;
	}
}
