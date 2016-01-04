package com.project.mechanic.entity;

public class Anad {

	int Id;
	byte[] Image;
	int ObjectId;
	String Date;
	int TypeId;
	int ProvinceId;
	int Seen;
	int Submit;
	String ImageServerDate;
	String ImagePath;
	int UserId;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public byte[] getImage() {
		return Image;
	}

	public void setImage(byte[] image) {
		Image = image;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int Objectid) {
		ObjectId = Objectid;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeid(int typeid) {
		TypeId = typeid;
	}

	public void setProvinceId(int provinceid) {
		ProvinceId = provinceid;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public int getSubmit() {
		return Submit;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}

	public Anad(int id, int Objectid, byte[] imge, String date, int typeid, int provinceid, int seen, int submit,
			String ImageServerDate, String ImagePath, int UserId) {

		super();
		Id = id;
		Image = imge;
		ObjectId = Objectid;
		Date = date;
		TypeId = typeid;
		ProvinceId = provinceid;
		Seen = seen;
		Submit = submit;
		this.ImageServerDate = ImageServerDate;
		this.ImagePath = ImagePath;
		this.UserId = UserId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getImageServerDate() {
		return ImageServerDate;
	}

	public void setImageServerDate(String imageServerDate) {
		ImageServerDate = imageServerDate;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public int getProvinceId() {
		return ProvinceId;
	}

	public void setTypeId(int typeId) {
		TypeId = typeId;
	}

}