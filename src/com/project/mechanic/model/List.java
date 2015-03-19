package com.project.mechanic.model;

public class List {

	int Id;
	int parentId;
	String Name;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getParentId() {
		return parentId;
	}

	public void setParentId(int parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public List(int id, int parentId, String name) {
		super();
		Id = id;
		this.parentId = parentId;
		Name = name;
	}

}
