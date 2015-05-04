package com.project.mechanic.entity;

public class Froum {

	int Id;
	int UserId;
	String Title;
	String Description;
	int Seen;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
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

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public Froum(int id, int userId, String title, String description, int seen) {
		super();
		Id = id;
		UserId = userId;
		Title = title;
		Description = description;
		Seen = seen;
	}

}
