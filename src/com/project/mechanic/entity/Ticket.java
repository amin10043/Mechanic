package com.project.mechanic.entity;



public class Ticket {

	int Id;
	int TypeId;
	int UserId;
	int ProvinceId;
	String Title;
	String Desc;
	String Date;
	short Name;
	short Email;
	short Mobile;
	short Phone;
	short Fax;
	byte[] Image;

	
	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeId(int Typeid) {
		TypeId = Typeid;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int Userid) {
		UserId = Userid;
	}
	

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public byte[]  getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	public Ticket(int id, String title, String desc, int Userid, byte[] imge, String date,int Typeid, short name,short email,short mobile,short phone,short fax,int provinceid) {
		super();
		Id = id;
		Title = title;
		Desc = desc;
		UserId = Userid;
		Date = date;
		TypeId = Typeid;
		Image = imge;
		Name = name;
		Email = email;
		Mobile = mobile;
		Phone = phone;
		Fax = fax;
		ProvinceId=provinceid;
		
		
		}
}

