package com.project.mechanic.entity;

public class Anad {

	int Id;
	int ObjectId;
	byte[] Image;
	String Date;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int Objectid) {
		ObjectId = Objectid;
	}

	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	public Anad(int id, int Objectid, byte[] imge, String date) {
		super();
		Id = id;
		ObjectId = Objectid;
		Image = imge;
		Date = date;

	}
}
