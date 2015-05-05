package com.project.mechanic.entity;

public class News {

	int Id;
	int Submit;
	String Title;
	String Description;
	String ServerDate;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	public News(int id, String title, String description, String serverdate,
			int submit) {
		super();
		Id = id;
		Title = title;
		Description = description;
		ServerDate = serverdate;
		Submit = submit;
	}

}
