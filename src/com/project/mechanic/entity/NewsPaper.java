package com.project.mechanic.entity;

public class NewsPaper {

	int Id;
	String Name;
	int TypeId;
	String Url;
	

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeId(int typeid) {
	    TypeId = typeid;
	}
	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}


	public NewsPaper(int id, String name, int typeid,String url) {
		super();
		Id = id;
		Name=name;
		TypeId = typeid;
		Url=url;
		
	}

}
