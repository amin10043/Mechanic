package com.project.mechanic.entity;

public class LikeInCommentObject {
	int Id;
	int CommentId;
	int UserId;
	int IsLike;
	String Date;

	public LikeInCommentObject(int Id, int CommentId, int UserId, int IsLike,
			String Date) {
		this.Id = Id;
		this.CommentId = CommentId;
		this.UserId = UserId;
		this.IsLike = IsLike;
		this.Date = Date;

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

	public int getIsLike() {
		return IsLike;
	}

	public void setIsLike(int isLike) {
		IsLike = isLike;
	}

}
