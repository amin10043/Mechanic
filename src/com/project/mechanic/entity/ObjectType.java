package com.project.mechanic.entity;

public class ObjectType {

	int Id;
	String Type;
	String Desc;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getType() {
		return Type;
	}

	public void setType(String type) {
	    Type = type;
	}
	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public ObjectType(int id, String type, String desc) {
		super();
		Id = id;
		Type = type;
		Desc=desc;
	}

}