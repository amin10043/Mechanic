package com.project.mechanic.entity;

public class Province implements Comparable<Province> {

	int Id;
	String Name;
	int Count;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getCount() {
		return Count;
	}

	public void setCount(int count) {
		Id = count;
	}

	public Province() {

	}

	public Province(int id, String name, int count) {
		super();
		Id = id;
		Name = name;
		Count = count;
	}

	@Override
	public int compareTo(Province compareProvince) {
		int compareCount = compareProvince.getCount();
		return compareCount - this.Count;

	}

}
