package com.project.mechanic.entity;

public class TicketType {

	int Id;
	String Desc;

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

	public TicketType(int id, String desc) {
		super();
		Id = id;
		Desc = desc;
	}

}
