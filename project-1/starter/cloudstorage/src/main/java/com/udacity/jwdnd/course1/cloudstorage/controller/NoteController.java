package com.udacity.jwdnd.course1.cloudstorage.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.AuthService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;

@Controller
@RequestMapping("/note")
public class NoteController {

	@Autowired
	private AuthService authService;
	
	@Autowired
	private NoteService noteService;

	@PostMapping("/add")
	public String addNote(@ModelAttribute("note") Note note, Model model, HttpSession httpSession) {
		int userId = authService.getUserId();
		note.setUserId(userId);
		int result = 0;
		if(note.getNoteId() == null || "".equals(note.getNoteId())) {
			result = noteService.insertNote(note);
		}else {
			result = noteService.updateNote(note.getNoteId(), note.getNoteTitle(), note.getNoteDescription());
		}
		httpSession.setAttribute("result", result);

		return "redirect:/result";
	}

	@GetMapping("/delete/{noteId}")
	public String deleteNote(@PathVariable Integer noteId, Model Model, HttpSession httpSession) {
		int result = 0;

		if (noteId != null) {
			result = noteService.deleteNote(noteId);
		}

		httpSession.setAttribute("result", result);

		return "redirect:/result";
	}
	
}
