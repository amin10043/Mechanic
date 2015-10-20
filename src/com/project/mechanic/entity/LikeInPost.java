package com.project.mechanic.entity;

public class LikeInPost {

	int Id;
	int UserId;
	int PostId;
	String Date;
	int CommentId;
	int Seen;

	public LikeInPost(int id, int userId, int postId, String date,
			int commentId, int seen) {
		super();
		Id = id;
		UserId = userId;
		PostId = postId;
		Date = date;
		CommentId = commentId;
		Seen = seen;
	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getPostId() {
		return PostId;
	}

	public void setPostId(int postId) {
		PostId = postId;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

}
