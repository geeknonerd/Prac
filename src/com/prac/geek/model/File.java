package com.prac.geek.model;

import java.io.Serializable;

public class File implements Serializable {
	private static final long serialVersionUID = 18937515190L;
	
	private int id;
	private String FName;
	private byte[] FContent;
	
	public File() {
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getFName() {
		return FName;
	}

	public void setFName(String fName) {
		FName = fName;
	}

	public byte[] getFContent() {
		return FContent;
	}

	public void setFContent(byte[] fContent) {
		FContent = fContent;
	}

}
