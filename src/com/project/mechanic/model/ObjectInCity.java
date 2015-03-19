package com.project.mechanic.model;

public class ObjectInCity {

	int Id;
	int ObjectId;
	int CityId;

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

	public int getCityId() {
		return CityId;
	}

	public void setCityId(int cityId) {
		CityId = cityId;
	}

	public ObjectInCity(int id, int objectId, int cityId) {
		super();
		Id = id;
		ObjectId = objectId;
		CityId = cityId;
	}

}
