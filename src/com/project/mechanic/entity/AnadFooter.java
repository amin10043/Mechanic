package com.project.mechanic.entity;

public class AnadFooter {
	int Id;
	byte[] Image;
	int ObjectId;
	String Date;
	int TypeId;
	int Seen;
	int Submit;
	String ImageServerDate;

	public AnadFooter(int Id, byte[] Image, int ObjectId, String Date,
			int TypeId, int Seen, int Submit, String ImageServerDate) {
		this.Id = Id;
		this.Image = Image;
		this.ObjectId = ObjectId;
		this.Date = Date;
		this.TypeId = TypeId;
		this.Seen = Seen;
		this.Submit = Submit;
		this.ImageServerDate = ImageServerDate;

	}

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

	public void setObjectId(int objectId) {
		ObjectId = objectId;
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

	public void setTypeId(int typeId) {
		TypeId = typeId;
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

	public String getImageServerDate() {
		return ImageServerDate;
	}

	public void setImageServerDate(String imageServerDate) {
		ImageServerDate = imageServerDate;
	}

}
