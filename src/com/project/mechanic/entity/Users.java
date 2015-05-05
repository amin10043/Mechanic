package com.project.mechanic.entity;

public class Users {

	int Id;
	String Name;
	String Email;
	String Password;
	String Phonenumber;
	String Mobailenumber;
	String Faxnumber;
	String Address;
	byte[] Image;
	String Date;
	int Serviceid;

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
///////////////
	
	
	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}
	// //////////////////////////////////////////
	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	// ////////////////////////////////////////////////
	public String getPassword() {
		return Password;
	}

	public void setPassword(String password) {
		Password = password;
	}

	// ////////////////////////////////////////////////////////////phonnumber
	public String getPhonenumber() {
		return Phonenumber;

	}

	public void setPhonenumber(String phonennumber) {
		this.Phonenumber = phonennumber;
	}

	// //////////////////////////////////////////////////mobilenumber

	public String getMobailenumber() {
		return Mobailenumber;

	}

	public void setMobailenumber(String mobailenumber) {
		this.Mobailenumber = mobailenumber;
	}

	// /////////////////////////////////////////////////faxenumber
	public String getFaxnumber() {
		return Faxnumber;

	}

	public void setFaxnumber(String faxnumber) {
		this.Faxnumber = faxnumber;
	}

	// //////////////////////////////////////////////////////////address
	public String getAddress() {
		return Address;

	}

	public void setAddress(String address) {
		this.Address = address;
	}

	// ////////////////////////////////////////////////////////////
	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	// /////////////////////////////////////////////////////////////////////////////////
	public Users(int id, String name, String email, String password,
			String phonenumber, String mobailenumber, String faxnumber,
			String address, byte[] image, int serviceid,String date) {

		super();

		Id = id;
		Name = name;
		Email = email;
		Password = password;
		Phonenumber = phonenumber;
		Mobailenumber = mobailenumber;
		Faxnumber = faxnumber;
		Address = address;
		Image = image;
		Serviceid = serviceid;
		Date=date;
	}

	public Users() {
		super();
	}

}
