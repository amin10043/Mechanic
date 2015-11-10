package com.project.mechanic.entity;

public class Visit {

	int Id;
	int UserId;
	int ObjectId;
	int TypeId;

	public int getUserId() {
		return UserId;
	}

	public void setId(int userid) {
		UserId = userid;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int Objectid) {
		ObjectId = Objectid;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeid(int typeid) {
		TypeId = typeid;
	}

	public Visit(int Id, int userid, int Objectid, int typeid) {

		UserId = userid;
		ObjectId = Objectid;
		TypeId = typeid;
		this.Id = Id;
	}

	public int getId() {
		return Id;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public void setTypeId(int typeId) {
		TypeId = typeId;
	}

}
