package com.project.mechanic.entity;

public class CommentInPost {

	int Id;
	String Desc;
	int PostId;
	int UserId;
	String Date;
	int CommentId;
	int Seen;

	public CommentInPost(int Id, String Desc, int PostId, int UserId,
			String Date, int CommentId, int Seen) {

		this.Id = Id;
		this.Desc = Desc;
		this.PostId = PostId;
		this.UserId = UserId;
		this.Date = Date;
		this.CommentId = CommentId;
		this.Seen = Seen;

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDesc() {
		return Desc;
	}

	public void setDesc(String desc) {
		Desc = desc;
	}

	public int getPostId() {
		return PostId;
	}

	public void setPostId(int postId) {
		PostId = postId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
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
