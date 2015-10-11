package com.project.mechanic.row_items;

public class CommentNotiItem {
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

	int Id;
	String Title;
	String Name;
	String Desk;
	String Date;
	int CommentId;

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getDesk() {
		return Desk;
	}

	public void setDesk(String desk) {
		Desk = desk;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public CommentNotiItem(int commentId, int id, String name, String title,
			String Date, String desc) {
		super();
		this.Title = title;
		this.Name = name;
		this.Desk = desc;
		this.Date = Date;
		this.CommentId = commentId;

	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

}
