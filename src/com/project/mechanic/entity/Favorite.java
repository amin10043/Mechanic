package com.project.mechanic.entity;

public class Favorite {

	int Id;
	int ObjectId;

	public Favorite(int id, int objectId, int userId) {
		super();
		Id = id;
		ObjectId = objectId;
		UserId = userId;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	int UserId;

}
