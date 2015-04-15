package com.project.mechanic.entity;

public class NewsPaperType {
	int Id;
	String Desc;
	int Type;
	String Url;
	

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
	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}


	public NewsPaperType(int id, String desc, int type,String url) {
		super();
		Id = id;
		Desc=desc;
		Type = type;
		Url=url;
		
	}

}
