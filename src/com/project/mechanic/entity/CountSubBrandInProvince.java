package com.project.mechanic.entity;

public class CountSubBrandInProvince {

	int Id;
	int ObjectId;
	int AgencyService;
	int ProvinceId;
	int CountInProvince;

	public CountSubBrandInProvince(int Id, int ObjectId, int AgencyService, int ProvinceId, int CountInProvince) {

		this.Id = Id;
		this.ProvinceId = ProvinceId;
		this.ObjectId = ObjectId;
		this.AgencyService = AgencyService;
		this.CountInProvince = CountInProvince;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	public int getAgencyService() {
		return AgencyService;
	}

	public void setAgencyService(int agencyService) {
		AgencyService = agencyService;
	}

	public int getCountInProvince() {
		return CountInProvince;
	}

	public void setCountInProvince(int countInProvince) {
		CountInProvince = countInProvince;
	}

}
