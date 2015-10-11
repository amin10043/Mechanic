package com.project.mechanic.entity;

public class Post {

	int Id;
	String Desc;
	String ImagePath;
	String Date;
	String UserId;
	String ModifyDate;

	public Post(int Id, String ImagePath, String Date, String UserId,
			String ModifyDate) {

		this.Id = Id;
		this.ImagePath = ImagePath;
		this.Date = Date;
		this.UserId = UserId;
		this.ModifyDate = ModifyDate;

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public String getImagePath() {
		return ImagePath;
	}

	public void setImagePath(String imagePath) {
		ImagePath = imagePath;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getModifyDate() {
		return ModifyDate;
	}

	public void setModifyDate(String modifyDate) {
		ModifyDate = modifyDate;
	}

}
