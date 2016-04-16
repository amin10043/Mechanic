package com.project.mechanic.entity;

public class ObjectInProvince {

	int Id;
	int ObjectId;
	int ProvinceId;
	String Date;

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

	public int getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(int provinceId) {
		ProvinceId = provinceId;
	}

	public ObjectInProvince(int id, int objectId, int provinceId, String Date) {
		super();
		Id = id;
		ObjectId = objectId;
		ProvinceId = provinceId;
		this.Date = Date;
	}

}
