package com.project.mechanic.entity;

public class Post {

	int Id;
	int UserId;
	String Description;
	int Seen;
	String ServerDate;
	int Submit;
	String Date;
	int seenBefore;
	String Photo;
	int CountView;
	int ObjectId;

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

	public String getPhoto() {
		return Photo;
	}

	public void setPhoto(String Photo) {
		this.Photo = Photo;
	}

	public int getCountView() {
		return CountView;
	}

	public int getObjectId() {
		return ObjectId;
	}

	public void setObjectId(int objectId) {
		ObjectId = objectId;
	}

	public void setCountView(int countView) {
		CountView = countView;
	}

	public Post(int id, int userId, String description, int seen, String serverDate, int submit, String date,
			int seenBefore, String photo, int CountView, int ObjectId) {
		super();
		Id = id;
		UserId = userId;
		Description = description;
		Seen = seen;
		ServerDate = serverDate;
		Submit = submit;
		Date = date;
		Photo = photo;
		this.seenBefore = seenBefore;
		this.CountView = CountView;
		this.ObjectId = ObjectId;
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
