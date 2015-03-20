package com.project.mechanic.entity;

public class WorkmanType {

	int Id;
	String Name;

	protected int getId() {
		return Id;
	}

	protected void setId(int id) {
		Id = id;
	}

	protected String getName() {
		return Name;
	}

	protected void setName(String name) {
		Name = name;
	}

	public WorkmanType(int id, String name) {
		super();
		Id = id;
		Name = name;
	}

}
