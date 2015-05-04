package com.project.mechanic.entity;

public class Like {

	int Id;
	int UserId;
	int paperId;
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

	public int getPaperId() {
		return paperId;
	}

	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public Like(int id, int userId, int paperId, int seen) {
		super();
		Id = id;
		UserId = userId;
		this.paperId = paperId;
		Seen = seen;
	}

}
