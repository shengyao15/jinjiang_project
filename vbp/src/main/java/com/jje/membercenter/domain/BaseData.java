package com.jje.membercenter.domain;

import java.util.Date;

import com.jje.dto.membercenter.BaseDataDto;

public class BaseData {

	private String rowId;
	private String parRowId;
	private String name;
	private String type;
	private String val;
	
	public BaseData() {
		super();
	}

	public BaseData(String rowId, String parRowId, String name, String type,
			String val) {
		super();
		this.rowId = rowId;

		this.parRowId = parRowId;

		this.name = name;

		this.type = type;
		this.val = val;

		

	}

	public BaseDataDto toDto() {

		return new BaseDataDto(rowId, parRowId, name, type, val);
	}

	public String getRowId() {
		return rowId;
	}

	public void setRowId(String rowId) {
		this.rowId = rowId;
	}

	public String getParRowId() {
		return parRowId;
	}

	public void setParRowId(String parRowId) {
		this.parRowId = parRowId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getVal() {
		return val;
	}

	public void setVal(String val) {
		this.val = val;
	}


}
