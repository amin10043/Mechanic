package com.project.mechanic.entity;

public class Settings implements Comparable<Settings> {

	int Id;
	String ServerDate_Object;
	String ServerDate_News;
	String ServerDate_Paper;
	String ServerDate_Froum;
	String ServerDate_Anad;
	String ServerDate_Ticket;
	String ServerDate_CommentInObject;
	String ServerDate_CommentInFroum;
	String ServerDate_CmtInPaper;
	String ServerDate_LikeInPaper;
	String ServerDate_LikeInFroum;
	String ServerDate_LikeInObject;
	String ServerDate_Users;

	String IMEI;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	@Override
	public int compareTo(Settings arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getServerDate_Object() {
		return ServerDate_Object;
	}

	public void setServerDate_Object(String serverDate_Object) {
		ServerDate_Object = serverDate_Object;
	}

	public String getServerDate_News() {
		return ServerDate_News;
	}

	public void setServerDate_News(String serverDate_News) {
		ServerDate_News = serverDate_News;
	}

	public String getServerDate_Paper() {
		return ServerDate_Paper;
	}

	public void setServerDate_Paper(String serverDate_Paper) {
		ServerDate_Paper = serverDate_Paper;
	}

	public String getServerDate_Froum() {
		return ServerDate_Froum;
	}

	public void setServerDate_Froum(String serverDate_Froum) {
		ServerDate_Froum = serverDate_Froum;
	}

	public String getServerDate_Anad() {
		return ServerDate_Anad;
	}

	public void setServerDate_Anad(String serverDate_Anad) {
		ServerDate_Anad = serverDate_Anad;
	}

	public String getServerDate_Ticket() {
		return ServerDate_Ticket;
	}

	public void setServerDate_Ticket(String serverDate_Ticket) {
		ServerDate_Ticket = serverDate_Ticket;
	}

	public String getServerDate_CommentInObject() {
		return ServerDate_CommentInObject;
	}

	public void setServerDate_CommentInObject(String serverDate_CommentInObject) {
		ServerDate_CommentInObject = serverDate_CommentInObject;
	}

	public String getServerDate_CommentInFroum() {
		return ServerDate_CommentInFroum;
	}

	public void setServerDate_CommentInFroum(String serverDate_CommentInFroum) {
		ServerDate_CommentInFroum = serverDate_CommentInFroum;
	}

	public String getServerDate_CmtInPaper() {
		return ServerDate_CmtInPaper;
	}

	public void setServerDate_CmtInPaper(String serverDate_CmtInPaper) {
		ServerDate_CmtInPaper = serverDate_CmtInPaper;
	}

	public String getServerDate_LikeInPaper() {
		return ServerDate_LikeInPaper;
	}

	public void setServerDate_LikeInPaper(String serverDate_LikeInPaper) {
		ServerDate_LikeInPaper = serverDate_LikeInPaper;
	}

	public String getServerDate_LikeInFroum() {
		return ServerDate_LikeInFroum;
	}

	public void setServerDate_LikeInFroum(String serverDate_LikeInFroum) {
		ServerDate_LikeInFroum = serverDate_LikeInFroum;
	}

	public String getServerDate_LikeInObject() {
		return ServerDate_LikeInObject;
	}

	public void setServerDate_LikeInObject(String serverDate_LikeInObject) {
		ServerDate_LikeInObject = serverDate_LikeInObject;
	}

	public String getServerDate_Users() {
		return ServerDate_Users;
	}

	public void setServerDate_Users(String serverDate_Users) {
		ServerDate_Users = serverDate_Users;
	}

	public Settings(int id, String serverDate_Object, String serverDate_News,
			String serverDate_Paper, String serverDate_Froum,
			String serverDate_Anad, String serverDate_Ticket,
			String serverDate_CommentInObject,
			String serverDate_CommentInFroum, String serverDate_CmtInPaper,
			String serverDate_LikeInPaper, String serverDate_LikeInFroum,
			String serverDate_LikeInObject, String serverDate_Users, String iMEI) {
		super();
		Id = id;
		ServerDate_Object = serverDate_Object;
		ServerDate_News = serverDate_News;
		ServerDate_Paper = serverDate_Paper;
		ServerDate_Froum = serverDate_Froum;
		ServerDate_Anad = serverDate_Anad;
		ServerDate_Ticket = serverDate_Ticket;
		ServerDate_CommentInObject = serverDate_CommentInObject;
		ServerDate_CommentInFroum = serverDate_CommentInFroum;
		ServerDate_CmtInPaper = serverDate_CmtInPaper;
		ServerDate_LikeInPaper = serverDate_LikeInPaper;
		ServerDate_LikeInFroum = serverDate_LikeInFroum;
		ServerDate_LikeInObject = serverDate_LikeInObject;
		ServerDate_Users = serverDate_Users;
		IMEI = iMEI;
	}

	public Settings() {

	}

}
