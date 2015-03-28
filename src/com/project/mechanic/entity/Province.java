package com.project.mechanic.entity;

public class Province {

	int Id;
	String Name;

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

	public Province(int id, String name) {
		super();
		Id = id;
		Name = name;
	}

}
