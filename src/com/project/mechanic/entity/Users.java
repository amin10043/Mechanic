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
	String ServerDate;
	String Date;
	int Serviceid;
	int Submit;
	int Admin;
	String ImageServerDate;
	String ImagePath;
	String ShowInfoItem;
	String BirthDay;
	String ProvinceCity;

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

	// /////////////

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
	public String getServerDate() {
		return ServerDate;
	}

	public void setServerDate(String serverdate) {
		ServerDate = serverdate;
	}

	public String getImageServerDate() {
		return ImageServerDate;
	}

	public void setImageServerDate(String imageserverdate) {
		ImageServerDate = imageserverdate;
	}

	public int getSubmit() {
		return Submit;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}

	public int getAdmin() {
		return Admin;
	}

	public void setAdmin(int admin) {
		Admin = admin;
	}

	public Users(int id, String name, String email, String password,
			String phonenumber, String mobailenumber, String faxnumber,
			String address, byte[] image, int serviceid, String serverdate,
			String date, int submit, int admin, String ImageServerDate,
			String ImagePath, String ShowInfoItem, String Birthday,
			String ProvinceCity) {

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
		ServerDate = serverdate;
		Submit = submit;
		Date = date;
		Admin = admin;
		this.ImageServerDate = ImageServerDate;
		this.ImagePath = ImagePath;
		this.ShowInfoItem = ShowInfoItem;
		this.BirthDay = Birthday;
		this.ProvinceCity = ProvinceCity;
	}

	public String getBirthDay() {
		return BirthDay;
	}

	public void setBirthDay(String birthDay) {
		BirthDay = birthDay;
	}

	public String getProvinceCity() {
		return ProvinceCity;
	}

	public void setProvinceCity(String provinceCity) {
		ProvinceCity = provinceCity;
	}

	public String getShowInfoItem() {
		return ShowInfoItem;
	}

	public void setShowInfoItem(String showInfoItem) {
		ShowInfoItem = showInfoItem;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public Users() {
		super();
	}

}
