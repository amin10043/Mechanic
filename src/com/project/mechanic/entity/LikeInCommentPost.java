package com.project.mechanic.entity;

public class LikeInCommentPost {
	int ID;
	int CommentId;
	int UserId;
	int IsLike;
	String Date;

	public LikeInCommentPost(int iD, int commentId, int userId, int isLike,
			String date) {
		super();
		ID = iD;
		CommentId = commentId;
		UserId = userId;
		IsLike = isLike;
		Date = date;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getIsLike() {
		return IsLike;
	}

	public void setIsLike(int isLike) {
		IsLike = isLike;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

}
