package com.project.mechanic.row_items;

public class LikeNotiItem {
	int id;
	String title;
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

	public String getLastSeen() {
		return lastSeen;
	}

	public void setLastSeen(String lastSeen) {
		this.lastSeen = lastSeen;
	}

	public LikeNotiItem(int id, String title, String lastSeen) {
		super();
		this.id = id;
		this.title = title;
		this.lastSeen = lastSeen;
	}

}
