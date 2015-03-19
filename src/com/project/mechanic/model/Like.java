package com.project.mechanic.model;

public class Like {

	int Id;
	int UserId;
	int paperId;

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

	public Like(int id, int userId, int paperId) {
		super();
		Id = id;
		UserId = userId;
		this.paperId = paperId;
	}

}
