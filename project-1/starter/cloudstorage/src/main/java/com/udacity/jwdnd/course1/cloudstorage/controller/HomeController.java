package com.udacity.jwdnd.course1.cloudstorage.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@Controller
public class HomeController {

	@Autowired
	private FileService fileService;

	@Autowired
	private NoteService noteService;

	@Autowired
	private AuthService authService;

	@Autowired
	private CredentialService credentialService;

	@GetMapping("/home")
	public String home(Model model, Authentication authentication) {
		Integer userId = authService.getUserId(authentication.getName());
		List<Note> noteList = noteService.getNoteList(userId);
		List<File> fileList = fileService.getFileList(userId);
		List<Credential> credentialList = credentialService.getCredentialList(userId);
		Note note = new Note(); File file = new File(); Credential credential = new Credential();
		model.addAttribute("note", note);
		model.addAttribute("file", file);
		model.addAttribute("noteList", noteList);
		model.addAttribute("credential", credential);
		model.addAttribute("fileList", fileList);
		model.addAttribute("credentialList", credentialList);
		return "home";
	}

	@GetMapping("/logout")
	public String logout() {
		return "redirect:/login";
	}

    @GetMapping("/result")
    public String resultView(HttpSession httpSession, Model model){
        model.addAttribute("result", httpSession.getAttribute("result"));
        return "result";
    }
}
