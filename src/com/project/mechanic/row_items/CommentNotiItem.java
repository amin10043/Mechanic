package com.project.mechanic.row_items;

public class CommentNotiItem {
	int id;
	String title;
	String Desc;
	String lastSeen;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}

	public CommentNotiItem(int id, String title, String desc, String lastSeen) {
		super();
		this.id = id;
		this.title = title;
		Desc = desc;
		this.lastSeen = lastSeen;
	}

}
