package com.project.mechanic.model;

public class Paper {

	int Id;
	String Title;
	String Context;

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

	public Paper(int id, String title, String context) {
		super();
		Id = id;
		Title = title;
		Context = context;
	}

}
