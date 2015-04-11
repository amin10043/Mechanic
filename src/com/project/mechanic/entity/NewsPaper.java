package com.project.mechanic.entity;

public class NewsPaper {

	int Id;
	String Name;
	int TypeId;
	

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


	public NewsPaper(int id, String name, int typeid) {
		super();
		Id = id;
		Name=name;
		TypeId = typeid;
		
	}

}
