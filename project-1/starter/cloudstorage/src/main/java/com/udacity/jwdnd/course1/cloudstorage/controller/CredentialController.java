package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.EncryptionService;

@Controller
@RequestMapping("/credential")
public class CredentialController {

	@Autowired
	private CredentialService credentialService;
	
	@Autowired
	private AuthService authService;
	
	@Autowired
	private EncryptionService encryptionService;

	@PostMapping("/add")
	public String addCredential(@ModelAttribute("credential") Credential credential, Authentication authentication, Model model, HttpSession httpSession) {
		String userName = authentication.getName();
		Integer userId = authService.getUserId(userName);
		credential.setUserId(userId);
		int result = credentialService.addOrModifiedCredential(credential);
		httpSession.setAttribute("result", result);
		return "redirect:/result";
	}
	
	@GetMapping("/delete/{credentialId:.*}")
	public String deleteCredential(@PathVariable("credentialId") Integer credentialId, Model model, HttpSession httpSession) {
		int result = 0;
		result = credentialService.deleteCredential(credentialId);
		httpSession.setAttribute("result", result);
		return "redirect:/result";
	}

	@GetMapping("/decodePassword")
	public ResponseEntity<String> handleRequest(@RequestParam("param") String credentialId) {
		Credential credential = credentialService.getCredential(Integer.parseInt(credentialId));
		String password = encryptionService.decryptValue(credential.getPassword(), credential.getKey());
		return ResponseEntity.ok(password);
	}

}
