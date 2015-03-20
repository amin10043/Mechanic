package com.project.mechanic.entity;

public class ACL {

	int Id;
	int UserId;
	int ListItemId;

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

	public int getListItemId() {
		return ListItemId;
	}

	public void setListItemId(int listItemId) {
		ListItemId = listItemId;
	}

	public ACL(int id, int userId, int listItemId) {
		super();
		Id = id;
		UserId = userId;
		ListItemId = listItemId;
	}

}
