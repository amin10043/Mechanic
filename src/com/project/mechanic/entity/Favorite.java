package com.project.mechanic.entity;

public class Favorite {

	int Id;
	int ObjectId;
	int UserId;
	int IdTickte;
	int Type;

	public Favorite(int id, int objectId, int userId, int idTickte, int Type) {
		super();
		Id = id;
		ObjectId = objectId;
		UserId = userId;
		IdTickte = idTickte;
		this.Type = Type;
	}

	public int getType() {
		return Type;
	}

	public void setType(int type) {
		Type = type;
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
