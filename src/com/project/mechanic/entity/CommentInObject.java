package com.project.mechanic.entity;

public class CommentInObject {
	
	

	int Id;
	String Description;
	int Objectid;
	int Userid;
	String Datetime;
	int Commentid;

	public CommentInObject(int id, String description, int froumid, int userid,
			String datetime, int commentid) {
		super();
		Id = id;
		Description = description;
		Objectid = froumid;
		Userid = userid;
		Datetime = datetime;
		Commentid = commentid;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getFroumid() {
		return Objectid;
	}

	public void setFroumid(int froumid) {
		Objectid = froumid;
	}

	public int getUserid() {
		return Userid;
	}

	public void setUserid(int userid) {
		Userid = userid;
	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public int getCommentid() {
		return Commentid;
	}

	public void setCommentid(int commentid) {
		Commentid = commentid;
	}

	public int getId() {
		return Id;
	}

	public void setId(int Id) {
		this.Id = Id;
	}

}
