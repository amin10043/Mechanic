package com.project.mechanic.entity;

public class Anad {

	int Id;
	int ObjectId;
	byte[] Image;
	String Date;
	int TypeId;
	int ProvinceId;

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

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeid(int typeid) {
		TypeId = typeid;
	}

	public int getProvinceid() {
		return ProvinceId;
	}

	public void setProvinceId(int provinceid) {
		ProvinceId = provinceid;
	}

	public Anad(int id, int Objectid, byte[] imge, String date, int typeid,
			int provinceid) {
		super();
		Id = id;
		ObjectId = Objectid;
		Image = imge;
		Date = date;
		TypeId = typeid;
		ProvinceId = provinceid;
	}
}
