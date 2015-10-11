package com.project.mechanic.row_items;

public class LikeNotiItem {
	int id;
	String title;
	String lastSeen;
	String name;
	String Date;
	int LikeId;

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

	public LikeNotiItem(int LikeId, int id, String name, String title,
			String Date) {
		super();
		this.id = id;
		this.title = title;
		// this.lastSeen = lastSeen;
		this.Date = Date;
		this.name = name;
		this.LikeId = LikeId;
	}

	public int getLikeId() {
		return LikeId;
	}

	public void setLikeId(int likeId) {
		LikeId = likeId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

}
