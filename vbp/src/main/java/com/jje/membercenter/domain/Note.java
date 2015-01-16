package com.jje.membercenter.domain;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.jje.dto.membercenter.NoteDto;
import com.jje.dto.membercenter.NoteOwnerCategoryDto;



public class Note {
    
	private Long id;
	private NoteOwnerCategoryDto ownerCategory;
	private Long ownerId;
	private String content;
	private String operation;
    private String creatorId;
	private String creatorName;
	private Date createTime;
	
	public Note() {
	}
	
	public Note(Long ownerId, NoteOwnerCategoryDto ownerCategory, String operation, String content, String creatorId, String creatorName) { 
	    this.ownerId = ownerId;
	    this.ownerCategory = ownerCategory;
	    this.operation = operation;
	    this.content = content;
	    this.creatorId = creatorId;
	    this.creatorName = creatorName;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public NoteOwnerCategoryDto getOwnerCategory() {
		return ownerCategory;
	}

	public void setOwnerCategory(NoteOwnerCategoryDto ownerCategory) {
		this.ownerCategory = ownerCategory;
	}

	public Long getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getOperation() {
		return operation;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

    public String getCreatorId() {
		return creatorId;
	}

    public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public static Note from(NoteDto dto) {
		if (dto == null) {
			return null;
		}
		Note note = new Note();
		BeanUtils.copyProperties(dto, note);
		return note;
	}

	public static NoteDto toDto(Note note) {
		NoteDto dto = new NoteDto();
		BeanUtils.copyProperties(note, dto);
		return dto;
	}

	public static List<NoteDto> toDtos(List<Note> notes) {
		List<NoteDto> noteArray = new ArrayList<NoteDto>();
		if (notes == null) {
			return noteArray;
		}
		for (Note note : notes) {
			noteArray.add(toDto(note));
		}
		return noteArray;
	}

    public String getCreatorName() {
        return creatorName;
    }

    public void setCreatorName(String creatorName) {
        this.creatorName = creatorName;
    }
}
