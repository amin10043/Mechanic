package com.project.mechanic.entity;

public class News {

	int Id;
	String Title;
	String Description;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public News(int id, String title, String description) {
		super();
		Id = id;
		Title = title;
		Description = description;
	}

}
