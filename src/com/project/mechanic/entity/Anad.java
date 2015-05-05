package com.project.mechanic.entity;

public class Anad {

	int Id;
	int ObjectId;
	byte[] Image;
	String Date;
	int TypeId;
	int ProvinceId;
	int Seen;

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

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public Anad(int id, int Objectid, byte[] imge, String date, int typeid,
			int provinceid, int seen) {
		super();
		Id = id;
		ObjectId = Objectid;
		Image = imge;
		Date = date;
		TypeId = typeid;
		ProvinceId = provinceid;
		Seen = seen;
	}
}
