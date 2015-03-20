package com.project.mechanic.entity;

public class Comment {

	int Id;
	int UserId;
	int paperId;
	String Description;

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

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Comment(int id, int userId, int paperId, String description) {
		super();
		Id = id;
		UserId = userId;
		this.paperId = paperId;
		Description = description;
	}

}
