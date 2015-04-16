package com.project.mechanic.entity;

public class ObjectBrandType {
	int Id;
	String Description;
	
	
	public ObjectBrandType(int id , String description){
		Id=id;
		Description =description;
	}


	public int getId() {
		return Id;
	}


	public void setId(int id) {
		Id = id;
	}


	public String getDescription() {
		return Description;
	}


	public void setDescription(String description) {
		Description = description;
	}
	
	

}
