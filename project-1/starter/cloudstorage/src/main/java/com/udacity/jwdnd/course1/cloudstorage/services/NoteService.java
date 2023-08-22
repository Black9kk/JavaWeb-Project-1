package com.udacity.jwdnd.course1.cloudstorage.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;

@Service
public class NoteService {

	@Autowired
	private NoteMapper noteMapper;

	// Insert note
	public int insertNote(Note note) {
		int result = 0;
		try {
			result = noteMapper.insertNote(note);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return -1;
		}
		return result;
	}

	// Get Notes List
	public List<Note> getNoteList(int userId) {
		
		return noteMapper.getNoteList(userId);
	}

	public int deleteNote(int noteId) {
		int result = 0;
		try {
			result = noteMapper.deleteNote(noteId);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}

	// Update Note
	public int updateNote(int noteId, String noteTitle, String noteDescription) {
		int result = 0;
		try {
			result = noteMapper.updateNote(noteId, noteTitle, noteDescription);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
		return result;
	}
}
