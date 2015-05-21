package com.project.mechanic.entity;

public class LikeInFroum {
	int Id;
	int UserId;
	int Froumid;
	String Datetime;
	int CommentId;
	int Seen;
	
	
	public LikeInFroum(int id, int userid, int froumid, String datetime,
			int commentid, int seen) {
		Id = id;
		UserId = userid;
		Froumid = froumid;
		Datetime = datetime;
		CommentId = commentid;
		Seen = seen;

	}

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

	public int getFroumid() {
		return Froumid;
	}

	public void setFroumid(int froumid) {
		Froumid = froumid;
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

	

}
