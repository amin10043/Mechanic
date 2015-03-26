package com.project.mechanic.row_items;

public class RowMain {

	String Name;
	String noti;
	byte[] image;

	public RowMain(String name, String noti, byte[] image) {
		super();
		Name = name;
		this.noti = noti;
		this.image = image;
	}

	public RowMain(String name) {
		super();
		Name = name;
	}

	public RowMain() {

	}

	public String getName() {
		return this.Name;
	}

	public void setName(String Name) {

		this.Name = Name;
	}

	public String getNoti() {

		return this.noti;

	}

	public void setNoti(String noti) {

		this.noti = noti;
	}

	public byte[] getimage() {

		return this.image;

	}

	public void setimage(byte[] image) {

		this.image = image;
	}

}
