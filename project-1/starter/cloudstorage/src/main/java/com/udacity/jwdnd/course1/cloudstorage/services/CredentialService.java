package com.udacity.jwdnd.course1.cloudstorage.services;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;

@Service
public class CredentialService {

	@Autowired
	private CredentialMapper credentialMapper;

	@Autowired
	private EncryptionService encryptionService;

	public List<Credential> getCredentialList(int userId) {
		List<Credential> credentialList = new ArrayList<>();
		try {
			credentialList = credentialMapper.getCredentialList(userId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return credentialList;
	}

	public int addOrModifiedCredential(Credential credential) {
		int result = 0;
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[16];
		random.nextBytes(key);
		String encodeKey = Base64.getEncoder().encodeToString(key);
		String encryptPassword = encryptionService.encryptValue(credential.getPassword(), encodeKey);
		credential.setKey(encodeKey);
		credential.setPassword(encryptPassword);
		if(isExistCredential(credential.getUsername(), credential.getUserId()) && credential.getCredentialId() == 0) {
			return result;
		}
		try {
			if(credential.getCredentialId() == 0) {
				result = credentialMapper.insertCredential(credential);
			} else {
				result = credentialMapper.updateCredential(credential);
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	public int deleteCredential(int credentialId) {
		int result = 0;
		try {
			result = credentialMapper.deleteCredential(credentialId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	public Credential getCredential(int credentialId) {
		Credential result = null;
		try {
			result = credentialMapper.getCredential(credentialId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return result;
	}

	public boolean isExistCredential(String username, Integer userId) {
		return credentialMapper.isExistCredential(username, userId) != null;
	}

}
