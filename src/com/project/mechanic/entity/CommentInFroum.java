package com.project.mechanic.entity;

public class CommentInFroum {
	int ID;
	String Description;
	int Froumid;
	int UserId;
	String Datetime;
	int CommentId;
	String NumofLike;
	String NumofDisLike;

	public String getNumofLike() {
		return NumofLike;
	}

	public void setNumofLike(String numofLike) {
		NumofLike = numofLike;
	}

	public String getNumofDisLike() {
		return NumofDisLike;
	}

	public void setNumofDisLike(String numofDisLike) {
		NumofDisLike = numofDisLike;
	}

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		ID = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getFroumid() {
		return Froumid;
	}

	public void setFroumid(int froumid) {
		Froumid = froumid;
	}

	public int getUserid() {
		return UserId;
	}

	public void setUserid(int userid) {
		UserId = userid;
	}

	public String getDatetime() {
		return Datetime;
	}

	public void setDatetime(String datetime) {
		Datetime = datetime;
	}

	public int getCommentid() {
		return CommentId;
	}

	public void setCommentid(int commentid) {
		CommentId = commentid;
	}

	public CommentInFroum(int id, String description, int froumId, int userId,
			String datetime, int commentId,String numofLike,String numofDisLike) {
		ID = id;
		Description = description;
		Froumid = froumId;
		UserId = userId;
		Datetime = datetime;
		CommentId = commentId;
		NumofLike=numofDisLike;
		NumofDisLike=numofDisLike;

	}

}
