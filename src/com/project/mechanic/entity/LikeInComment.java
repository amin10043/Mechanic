package com.project.mechanic.entity;

public class LikeInComment {

	int Id;
	int CommentId;
	int UserId;
	int IsLike;
	String Date;
	public LikeInComment(int id, int commentId, int userId, int isLike , String Date) {
		super();
		Id = id;
		CommentId = commentId;
		UserId = userId;
		IsLike = isLike;
		this.Date = Date;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getIsLike() {
		return IsLike;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
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

	public int isIsLike() {
		return IsLike;
	}

	public void setIsLike(int isLike) {
		IsLike = isLike;
	}

}
