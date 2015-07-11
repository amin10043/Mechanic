package com.project.mechanic.entity;

public class Paper implements Comparable<Paper> {

	int Id;
	String Title;
	String Context;
	int Seen;
	String ServerDate;
	int Submit;
	int UserId;
	String Date;
	int seenBefore;

	public int getSeenBefore() {
		return seenBefore;
	}

	public void setSeenBefore(int seenBefore) {
		this.seenBefore = seenBefore;
	}

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

	public String getContext() {
		return Context;
	}

	public void setContext(String context) {
		Context = context;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public void setServerDate(String serversate) {
		ServerDate = serversate;
	}

	public String getServerDate() {
		return ServerDate;
	}

	public int getSubmit() {
		return Submit;
	}

	public void setSubmit(int submit) {
		Submit = submit;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public Paper(int id, String title, String context, int seen,
			String serversate, int submit, int userId, String date,
			int seenBefore) {
		super();
		Id = id;
		Title = title;
		Context = context;
		Seen = seen;
		ServerDate = serversate;
		Submit = submit;
		UserId = userId;
		Date = date;
		this.seenBefore = seenBefore;
	}
	public int compareTo(Paper comparePaper) {
		try {
			long temp = Long.valueOf(getDate());
			long comparetemp = Long.valueOf(comparePaper.getDate());
			return (int) (comparetemp - temp);
		} catch (Exception ex) {
			return 0;
		}

	}

	public int compareTo(Paper comparePaper) {
		long temp = Long.valueOf(getDate());
		Long comparetemp = Long.valueOf(comparePaper.getDate());
		return (int) (comparetemp - temp);
	}

}
