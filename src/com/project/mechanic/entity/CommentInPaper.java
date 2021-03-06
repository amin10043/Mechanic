package com.project.mechanic.entity;

public class CommentInPaper {
	public int getPaperId() {
		return PaperId;
	}

	public void setPaperId(int paperId) {
		PaperId = paperId;
	}

	public int getUserId() {
		return UserId;
	}

	public void setUserId(int userId) {
		UserId = userId;
	}

	public int getCommentId() {
		return CommentId;
	}

	public void setCommentId(int commentId) {
		CommentId = commentId;
	}

	int Id;
	String Description;
	int PaperId;
	int UserId;
	String Datetime;
	int CommentId;
	int Seen;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public int getPaperid() {
		return PaperId;
	}

	public void setPaperid(int paperid) {
		PaperId = paperid;
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

	public int getSeen() {
		return Seen;
	}

	public void setSeen(int seen) {
		Seen = seen;
	}

	public CommentInPaper(int id, String description, int paperId, int userId,
			String datetime, int commentId, int seen) {

		Id = id;
		Description = description;
		PaperId = paperId;
		UserId = userId;
		Datetime = datetime;
		CommentId = commentId;
		Seen = seen;
	}

}
