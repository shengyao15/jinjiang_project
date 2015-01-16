package com.jje.membercenter.persistence;

import java.util.Date;
import java.util.List;

import com.jje.membercenter.DataPrepareFramework;
import junit.framework.Assert;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jje.dto.membercenter.NoteOwnerCategoryDto;
import com.jje.membercenter.domain.Note;
import com.jje.membercenter.domain.NoteRepository;

@Transactional
public class NoteRepositoryImplTest extends DataPrepareFramework {

	@Autowired
	private NoteRepository noteRepository;
	 

	@Test
	public void create() {
		Note note =new Note();
		note.setContent("content");
		note.setCreateTime(new Date());
		note.setCreatorId("creatorId");
		note.setCreatorName("creatorName");
		note.setOperation("operation");
		note.setOwnerCategory(NoteOwnerCategoryDto.SCORES_ORDER);
        note.setOwnerId(20130325L);
        noteRepository.insert(note);
        List<Note> notes =noteRepository.getScoresOrderNotes(20130325L);
        Assert.assertNotNull(notes);
        Assert.assertEquals(1,notes.size());
        Assert.assertEquals("creatorName",notes.get(0).getCreatorName());
	}

	 
	@Test
	public void query() {
        Note note =new Note();
        note.setContent("content");
        note.setCreateTime(new Date());
        note.setCreatorId("creatorId");
        note.setCreatorName("creatorName");
        note.setOperation("operation");
        note.setOwnerCategory(NoteOwnerCategoryDto.SCORES_ORDER);
        note.setOwnerId(20130327L);
        noteRepository.insert(note);
        List<Note> notes =  noteRepository.query(note);
		Assert.assertNotNull(notes);
        Assert.assertEquals(1,notes.size());
        Assert.assertEquals("creatorName",notes.get(0).getCreatorName());
	}


}
