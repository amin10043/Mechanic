package com.project.mechanic.entity;

public class CountSubBrand {

	int Id;
	int CityId;
	int ProvinceId;
	int ObjectId;
	int CountInCity;
	int AgencyService;

	public CountSubBrand(int Id, int CityId, int ProvinceId, int ObjectId, int CountInCity, int AgencyService) {

		this.Id = Id;
		this.CityId = CityId;
		this.ProvinceId = ProvinceId;
		this.ObjectId = ObjectId;
		this.CountInCity = CountInCity;
		this.AgencyService = AgencyService;

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getCityId() {
		return CityId;
	}

	public void setCityId(int cityId) {
		CityId = cityId;
	}

	public int getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(int provinceId) {
		ProvinceId = provinceId;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	public int getCountInCity() {
		return CountInCity;
	}

	public void setCountInCity(int countInCity) {
		CountInCity = countInCity;
	}

	public int getAgencyService() {
		return AgencyService;
	}

	public void setAgencyService(int agencyService) {
		AgencyService = agencyService;
	}

}
