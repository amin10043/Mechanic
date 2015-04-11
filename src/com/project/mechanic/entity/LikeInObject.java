package com.project.mechanic.entity;

public class LikeInObject {
	int Id;
	int UserId;
	int paperId;
	String Datetime;
	int Commentid;
	
	
	public LikeInObject(int id, int userId, int paperId, String datetime,
			int commentid) {
		super();
		Id = id;
		UserId = userId;
		this.paperId = paperId;
		Datetime = datetime;
		Commentid = commentid;
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
	public int getPaperId() {
		return paperId;
	}
	
	public void setPaperId(int paperId) {
		this.paperId = paperId;
	}
	public String getDatetime() {
		return Datetime;
	}
	public void setDatetime(String datetime) {
		Datetime = datetime;
	}
	public int getCommentid() {
		return Commentid;
	}
	public void setCommentid(int commentid) {
		Commentid = commentid;
	}
}
