package com.project.mechanic.entity;

public class City {

	int Id;
	int ProvinceId;
	String Name;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(int ProvinceId) {
		ProvinceId = ProvinceId;
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public City(int id,  String name, int provinceId) {
		super();
		Id = id;
	 ProvinceId = ProvinceId;
	 Name = name;
	}

}
