package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;


@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private FileService fileService;

	@Autowired
	private AuthService authService;

	@PostMapping("/add")
	public String addFile(@RequestParam("fileUpload") MultipartFile file, Model model, Authentication authentication, HttpSession httpSession) {
		String userName = authentication.getName();
		int userId = authService.getUserId(userName);
		int result = 0;

		result = fileService.insertFile(file, userId);

		httpSession.setAttribute("result", result);
		return "redirect:/result";
	}

	@GetMapping("/delete/{fileId}")
	public String deleteFile(@PathVariable Integer fileId, Model model, HttpSession httpSession) {
		int result = 0;

		if (fileId != null) {
			result = fileService.deleteFile(fileId);
		}

		httpSession.setAttribute("result", result);

		return "redirect:/result";
	}

    @GetMapping("/view/{fileId}")
    public ResponseEntity<byte[]> viewFile(@PathVariable int fileId) {
        File file = fileService.getFile(fileId);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentDispositionFormData("attachment", file.getFileName());
        headers.setContentType(MediaType.parseMediaType(file.getContentType()));
        return new ResponseEntity<>(file.getFileData(), headers, HttpStatus.OK);
    }

}
