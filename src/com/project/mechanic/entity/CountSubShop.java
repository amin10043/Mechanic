package com.project.mechanic.entity;

public class CountSubShop {

	int Id;
	int MainObjectId;
	int CityId;
	int ProvinceId;
	int ObjectTypeId;
	int CountInCity;

	public CountSubShop(int Id, int MainObjectId, int CityId, int ProvinceId, int ObjectTypeId, int CountInCity) {

		this.Id = Id;
		this.MainObjectId = MainObjectId;
		this.CityId = CityId;
		this.ProvinceId = ProvinceId;
		this.ObjectTypeId = ObjectTypeId;
		this.CountInCity = CountInCity;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getMainObjectId() {
		return MainObjectId;
	}

	public void setMainObjectId(int mainObjectId) {
		MainObjectId = mainObjectId;
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

	public int getObjectTypeId() {
		return ObjectTypeId;
	}

	public void setObjectTypeId(int objectTypeId) {
		ObjectTypeId = objectTypeId;
	}

	public int getCountInCity() {
		return CountInCity;
	}

	public void setCountInCity(int countInCity) {
		CountInCity = countInCity;
	}

}
