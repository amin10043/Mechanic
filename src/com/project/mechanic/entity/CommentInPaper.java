package com.project.mechanic.entity;

public class CommentInPaper {
	int Id;
	String Description;
	int PaperId;
	int UserId;
	String Datetime;
	int CommentId;

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

	public CommentInPaper(int id, String description, int paperId, int userId,
			String datetime, int commentId) {

		Id = id;
		Description = description;
		PaperId = paperId;
		UserId = userId;
		Datetime = datetime;
		CommentId = commentId;

	}

}
