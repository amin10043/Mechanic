package com.project.mechanic.entity;

public class Froum implements Comparable<Froum> {

	int Id;
	int UserId;
	String Title;
	String Description;
	int Seen;
	String ServerDate;
	int Submit;
	String Date;
	int seenBefore;
	int CountView;
	int CountLike;
	int CountComment;

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

	public int getCountView() {
		return CountView;
	}

	public void setCountView(int countView) {
		CountView = countView;
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

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Froum(int id, int userId, String title, String description, int seen, String serverdate, int submit,
			String date, int seenBefore, int CountView, int CountLike, int CountComment) {
		super();
		Id = id;
		UserId = userId;
		Title = title;
		Description = description;
		Seen = seen;
		ServerDate = serverdate;
		Submit = submit;
		Date = date;
		this.seenBefore = seenBefore;
		this.CountView = CountView;
		this.CountLike = CountLike;
		this.CountComment = CountComment;
	}

	public int getCountLike() {
		return CountLike;
	}

	public void setCountLike(int countLike) {
		CountLike = countLike;
	}

	public int getCountComment() {
		return CountComment;
	}

	public void setCountComment(int countComment) {
		CountComment = countComment;
	}

	public int getSeenBefore() {
		return seenBefore;
	}

	public void setSeenBefore(int seenBefore) {
		this.seenBefore = seenBefore;
	}

	public int compareTo(Froum compareFroum) {
		long temp = Long.valueOf(getDate());
		Long comparetemp = Long.valueOf(compareFroum.getDate());
		if (comparetemp > temp)
			return 1;
		else if (comparetemp < temp)
			return -1;
		else
			return 0;
	}
}
