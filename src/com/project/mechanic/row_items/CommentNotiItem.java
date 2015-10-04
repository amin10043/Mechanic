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
	String lastSeen;

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

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}

	public CommentNotiItem(int id, String title, String name, String desc,
			String Date, String lastSeen) {
		super();
		this.Title = title;
		this.Name = name;
		this.Desk = desc;
		this.Date = Date;
		this.lastSeen = lastSeen;
	}

}
