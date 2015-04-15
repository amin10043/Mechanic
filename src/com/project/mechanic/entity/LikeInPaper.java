package com.project.mechanic.entity;

public class LikeInPaper {
	int Id;
	int UserId;
	int PaperId;
	String Datetime;
	int CommentId;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public int getUserid() {
		return UserId;
	}

	public void setUserid(int userid) {
		UserId = userid;
	}

	public int getPaperid() {
		return PaperId;
	}

	public void setPaperid(int paperid) {
		PaperId = paperid;
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

	public LikeInPaper(int id, int userid, int paperid, String datetime,
			int commentid) {
		Id = id;
		UserId = userid;
		PaperId = paperid;
		Datetime = datetime;
		CommentId = commentid;
	}

}
