package com.project.mechanic.entity;

public class SubAdmin {
	int Id;
	int ObjectId;
	int UserId;
	int AdminID;

	public SubAdmin(int Id, int ObjectId, int UserId, int AdminID) {
		this.Id = Id;
		this.ObjectId = ObjectId;
		this.UserId = UserId;
		this.AdminID = AdminID;
	}

	public int getId() {
		return Id;
	}

	public int getAdminID() {
		return AdminID;
	}

	public void setAdminID(int adminID) {
		AdminID = adminID;
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

}
