package com.project.mechanic.model;

public class Object {

	int Id;
	String Name;
	String Phone;
	String Email;
	String Fax;
	String Description;
	Byte[] Image1;
	Byte[] Image2;
	Byte[] Image3;
	Byte[] Image4;

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

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getFax() {
		return Fax;
	}

	public void setFax(String fax) {
		Fax = fax;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public Byte[] getImage1() {
		return Image1;
	}

	public void setImage1(Byte[] image1) {
		Image1 = image1;
	}

	public Byte[] getImage2() {
		return Image2;
	}

	public void setImage2(Byte[] image2) {
		Image2 = image2;
	}

	public Byte[] getImage3() {
		return Image3;
	}

	public void setImage3(Byte[] image3) {
		Image3 = image3;
	}

	public Byte[] getImage4() {
		return Image4;
	}

	public void setImage4(Byte[] image4) {
		Image4 = image4;
	}

	public Object(int id, String name, String phone, String email, String fax,
			String description, Byte[] image1, Byte[] image2, Byte[] image3,
			Byte[] image4) {
		super();
		Id = id;
		Name = name;
		Phone = phone;
		Email = email;
		Fax = fax;
		Description = description;
		Image1 = image1;
		Image2 = image2;
		Image3 = image3;
		Image4 = image4;
	}

}
