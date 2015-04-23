package com.project.mechanic.entity;

public class Users {

	int Id;
	String Phonenumber;
	String Name;
	String Email;
	String Password;
	byte[] Image;

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

	public String getPhonennumber() {
		return Phonenumber;
	}

	public void setPhonennumber(String phonennumber) {
		this.Phonenumber = phonennumber;
	}

	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	public Users(int id, String name, String email, String password,
			String phonenumber, byte[] image) {
		super();
		Id = id;
		Name = name;
		Email = email;
		Password = password;
		Phonenumber = phonenumber;
		Image = image;

	}

	public Users() {

	}

}
