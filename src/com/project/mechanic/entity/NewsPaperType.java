package com.project.mechanic.entity;

public class NewsPaperType {
	int Id;
	String Desc;
	int Type;
	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
	    Type = type;
	}


	public NewsPaperType(int id, String desc, int type) {
		super();
		Id = id;
		Desc=desc;
		Type = type;
		
	}

}
