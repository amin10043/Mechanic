package com.project.mechanic.entity;

public class Settings implements Comparable<Settings> {

	int Id;
	String ServerDate;
	String IMEI;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getServerDate() {
		return ServerDate;
	}

	public void setServerDate(String serverDate) {
		ServerDate = serverDate;
	}

	public String getIMEI() {
		return IMEI;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public Settings(int id, String serverDate, String iMEI) {
		super();
		Id = id;
		ServerDate = serverDate;
		IMEI = iMEI;

	}

	@Override
	public int compareTo(Settings arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

}
