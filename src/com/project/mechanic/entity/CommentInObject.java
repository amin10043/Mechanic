package com.project.mechanic.entity;

import android.R.string;

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
	

	public CommentInObject(int id, String description, int froumid, int userid,
			String datetime, int commentid, int NumofLike, int NumofDislike,
			int seen) {
		super();
		Id = id;
		Description = description;
		Objectid = froumid;
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

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}
	
}
