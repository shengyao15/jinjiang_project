package com.jje.membercenter.domain;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.jje.dto.membercenter.NoteOwnerCategoryDto;
import com.jje.membercenter.persistence.NoteMapper;

@Repository
public class NoteRepository {
	@Autowired
	private NoteMapper noteMapper;

	public void insert(Note note) {
 		 noteMapper.insert(note);
	}

	public List<Note> getScoresOrderNotes(Long ownerId) {
		return this.getNotesByOwner(ownerId, NoteOwnerCategoryDto.SCORES_ORDER);
	}

	private List<Note> getNotesByOwner(Long ownerId,
			NoteOwnerCategoryDto ownerCategory) {
		Note note = new Note();
		note.setOwnerId(ownerId);
		note.setOwnerCategory(ownerCategory);
		return this.query(note);
	}

	public List<Note> query(Note note) {
		return noteMapper.query(note);
	}
}
