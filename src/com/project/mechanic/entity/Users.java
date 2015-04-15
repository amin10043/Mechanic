package com.project.mechanic.entity;

public class Users {

	int Id;
	String Name;
	String Email;
	String Password;

	

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



	public String getEmail() {
		return Email;
	}



	public void setEmail(String email) {
		Email = email;
	}



	public String getPassword() {
		return Password;
	}



	public void setPassword(String password) {
		Password = password;
	}



	public Users(int id, String name, String email, String password) {
		super();
		Id = id;
		Name = name;
		Email = email;
		Password = password;
	}

}
