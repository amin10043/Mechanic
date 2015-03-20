package com.project.mechanic.entity;

public class Users {

	int Id;
	String Name;
	String Email;
	String Password;

	protected int getId() {
		return Id;
	}

	protected void setId(int id) {
		Id = id;
	}

	protected String getName() {
		return Name;
	}

	protected void setName(String name) {
		Name = name;
	}

	protected String getEmail() {
		return Email;
	}

	protected void setEmail(String email) {
		Email = email;
	}

	protected String getPassword() {
		return Password;
	}

	protected void setPassword(String password) {
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
