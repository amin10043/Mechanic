package com.project.mechanic.entity;

public class Post {

	int Id;
	int UserId;
	String Title;
	String Description;
	int Seen;
	String ServerDate;
	int Submit;
	String Date;
	int seenBefore;

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

	public void setServerDate(String serverDate) {
		ServerDate = serverDate;
	}

	public int getSubmit() {
		return Submit;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getSeenBefore() {
		return seenBefore;
	}

	public void setSeenBefore(int seenBefore) {
		this.seenBefore = seenBefore;
	}

	public Post(int id, int userId, String title, String description, int seen,
			String serverDate, int submit, String date, int seenBefore) {
		super();
		Id = id;
		UserId = userId;
		Title = title;
		Description = description;
		Seen = seen;
		ServerDate = serverDate;
		Submit = submit;
		Date = date;
		this.seenBefore = seenBefore;
	}

	public int compareTo(Post comparePost) {
		long temp = Long.valueOf(getDate());
		Long comparetemp = Long.valueOf(comparePost.getDate());
		if (comparetemp > temp)
			return 1;
		else if (comparetemp < temp)
			return -1;
		else
			return 0;
	}

}
