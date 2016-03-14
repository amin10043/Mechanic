package com.project.mechanic.entity;

public class LikeInObject {
	int Id;
	int UserId;
	int paperId;
	String Datetime;
	int Commentid;
	int seen;
	int IsLike;

	public LikeInObject(int id, int userId, int paperId, String datetime, int commentid, int seen, int Islike) {
		super();
		Id = id;
		UserId = userId;
		this.paperId = paperId;
		Datetime = datetime;
		Commentid = commentid;
		this.seen = seen;
		this.IsLike = Islike;
	}

	public int getIsLike() {
		return IsLike;
	}

	public void setIsLike(int isLike) {
		IsLike = isLike;
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

	public int getSeen() {
		return seen;
	}

	public void setSeen(int seen) {
		this.seen = seen;
	}

}
