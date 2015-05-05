package com.project.mechanic.entity;

public class City implements Comparable<City> {

	int Id;
	int ProvinceId;
	String Name;
    int Count;
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getProvinceId() {
		return ProvinceId;
	}

	public void setProvinceId(int provinceid) {
		ProvinceId = provinceid;
	}
	
	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}
	public int getCount(){
		return Count;
	}
	public void setCount(int count){
		Id = count;
	}

	public City(int id,  String name, int provinceid,int count) {
		super();
		Id = id;
	 ProvinceId = provinceid;
	 Name = name;
	 Count = count;
	}

	@Override
	public int compareTo(City comparecity) {
		// TODO Auto-generated method stub
int compareCount =  comparecity.getCount(); 
		 
		
		//return this.Count - compareCount;

		
		return compareCount - this.Count;
		
	}

}
