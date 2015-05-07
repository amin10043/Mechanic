package com.project.mechanic.entity;

public class Favorite {

	int Id;
	int ObjectId;
	int UserId;
	int IdTickte;

	public Favorite(int id, int objectId, int userId, int idTickte) {
		super();
		Id = id;
		ObjectId = objectId;
		UserId = userId;
		IdTickte = idTickte;
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

	public int getIdTickte() {
		return IdTickte;
	}

	public void setIdTickte(int idTickte) {
		IdTickte = idTickte;
	}

}
