package com.project.mechanic.entity;

public class Froum {

	int Id;
	int UserId;
	String Title;
	String Description;
	int Seen;
	String ServerDate;
	int Submit;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getTitle() {
		return Title;
	}

	public void setTitle(String title) {
		Title = title;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public String getServerDate() {
		return ServerDate;
	}

	public void setServerDate(String serverdate) {
		ServerDate = serverdate;
	}

	public int getSubmit() {
		return Submit;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}

	public Froum(int id, int userId, String title, String description,
			int seen, String serverdate, int submit) {
		super();
		Id = id;
		UserId = userId;
		Title = title;
		Description = description;
		Seen = seen;
		ServerDate = serverdate;
		Submit = submit;
	}

}
