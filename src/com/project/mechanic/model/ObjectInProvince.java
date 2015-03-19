package com.project.mechanic.model;

public class ObjectInProvince {

	int Id;
	int ObjectId;
	int ProvinceId;

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

	public ObjectInProvince(int id, int objectId, int provinceId) {
		super();
		Id = id;
		ObjectId = objectId;
		ProvinceId = provinceId;
	}

}
