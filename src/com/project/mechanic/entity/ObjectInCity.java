package com.project.mechanic.entity;

public class ObjectInCity {

	int Id;
	int ObjectId;
	int CityId;
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

	public int getCityId() {
		return CityId;
	}

	public void setCityId(int cityId) {
		CityId = cityId;
	}
	
	public String getDate() {
		return Date;
	}

	public void setDate(String Date) {
		this.Date = Date;
	}

	public ObjectInCity(int id, int objectId, int cityId , String Date) {
		super();
		Id = id;
		ObjectId = objectId;
		CityId = cityId;
		this.Date = Date;
	}

}
