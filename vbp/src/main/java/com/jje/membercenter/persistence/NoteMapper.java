package com.jje.membercenter.persistence;

import java.util.List;

import com.jje.membercenter.domain.Note;

public interface NoteMapper {
    
    void insert(Note note);
    
    List<Note> query(Note note);
    
}
