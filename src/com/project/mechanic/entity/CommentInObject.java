package com.project.mechanic.entity;

public class CommentInObject {

	int Id;
	String Description;
	int Objectid;
	int Userid;
	String Datetime;
	int Commentid;
	int NumofLike;
	int NumofDisLike;
	int Seen;

	public CommentInObject(int id, String description, int objectid,
			int userid, String datetime, int commentid, int NumofLike,
			int NumofDislike, int seen) {
		super();
		Id = id;
		Description = description;
		Objectid = objectid;
		Userid = userid;
		Datetime = datetime;
		Commentid = commentid;
		this.NumofLike = NumofLike;
		this.NumofDisLike = NumofDislike;
		Seen = seen;
	}

	public int getNumofLike() {
		return NumofLike;
	}

	public void setNumofLike(int numofLike) {
		NumofLike = numofLike;
	}

	public int getNumofDisLike() {
		return NumofDisLike;
	}

	public void setNumofDisLike(int numofDisLike) {
		NumofDisLike = numofDisLike;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getObjectid() {
		return Objectid;
	}

	public void setObjectid(int objectid) {
		Objectid = objectid;
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

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}
}
