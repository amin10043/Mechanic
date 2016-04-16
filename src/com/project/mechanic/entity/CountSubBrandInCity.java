package com.project.mechanic.entity;

public class CountSubBrandInCity {

	int Id;
	int ObjectId;
	int AgencyService;
	int CityId;
	int CountInCity;

	public CountSubBrandInCity(int Id, int ObjectId, int AgencyService, int CityId, int CountInCity) {

		this.Id = Id;
		this.CityId = CityId;
		this.ObjectId = ObjectId;
		this.AgencyService = AgencyService;
		this.CountInCity = CountInCity;
	}

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

	public int getAgencyService() {
		return AgencyService;
	}

	public void setAgencyService(int agencyService) {
		AgencyService = agencyService;
	}

	public int getCityId() {
		return CityId;
	}

	public void setCityId(int cityId) {
		CityId = cityId;
	}

	public int getCountInCity() {
		return CountInCity;
	}

	public void setCountInCity(int countInCity) {
		CountInCity = countInCity;
	}

}
