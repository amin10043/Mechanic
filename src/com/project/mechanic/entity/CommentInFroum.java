package com.project.mechanic.entity;

public class CommentInFroum {
	int ID;
	String Desk;
	int Froumid;
	int UserId;
	String Datetime;
	int CommentId;
	String NumOfLike;
	String NumOfDislike;

	public String getNumOfLike() {
		return NumOfLike;
	}

	public void setNumOfLike(String numOfLike) {
		NumOfLike = numOfLike;
	}

	public String getNumOfDislike() {
		return NumOfDislike;
	}

	public void setNumOfDislike(String numOfDislike) {
		NumOfDislike = numOfDislike;
	}

	public int getId() {
		return ID;
	}

	public void setId(int id) {
		ID = id;
	}

	public String getDesk() {
		return Desk;
	}

	public void setDesk(String desk) {
		Desk = desk;
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

	public CommentInFroum(int iD, String desk, int froumid, int userId,
			String datetime, int commentId, String numofDisLike,
			String numofLike) {
		super();
		ID = iD;
		Desk = desk;
		Froumid = froumid;
		UserId = userId;
		Datetime = datetime;
		CommentId = commentId;
		NumOfLike = numofLike;
		NumOfDislike = numofDisLike;
	}

}
