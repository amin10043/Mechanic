package com.project.mechanic.entity;

public class ListItem {

	int Id;
	int ListId;
	String Name;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getListId() {
		return ListId;
	}

	public void setListId(int listId) {
		ListId = listId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public ListItem(int id, String name, int listId) {
		super();
		Id = id;
		ListId = listId;
		Name = name;
	}

}
