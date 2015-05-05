package com.project.mechanic.entity;

public class Paper {

	int Id;
	String Title;
	String Context;
	int Seen;

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

	public Paper(int id, String title, String context, int seen) {
		super();
		Id = id;
		Title = title;
		Context = context;
		Seen = seen;
	}

}
