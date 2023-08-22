package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.User;

@Service
public class AuthService implements AuthenticationProvider{

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private HashService hashService;
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		String userName = authentication.getName();
		String passWord = authentication.getCredentials().toString();
		User user = userMapper.getUser(userName);

		if (user != null) {
			String salt = user.getSalt();
			String hashPassWord = hashService.getHashedValue(passWord, salt);
			if(user.getPassword().equals(hashPassWord)) {
				return new UsernamePasswordAuthenticationToken(userName, passWord, new ArrayList<>());
			}
		}

		return null;
	}
	
	@Override
	public boolean supports(Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

	public int getUserId() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String userName = authentication.getName();
		User user = userMapper.getUser(userName);
		return user.getUserId();
	}
		
	public int getUserId(String userName) {
		User user = userMapper.getUser(userName);
		return user.getUserId();

	}
}
