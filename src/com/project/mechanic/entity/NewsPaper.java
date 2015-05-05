package com.project.mechanic.entity;

public class NewsPaper {

	int Id;
	String Name;
	int TypeId;
	String Url;
	String ServerDate;

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public int getTypeId() {
		return TypeId;
	}

	public void setTypeId(int typeid) {
		TypeId = typeid;
	}

	public String getUrl() {
		return Url;
	}

	public void setUrl(String url) {
		Url = url;
	}

	public String getServerDate() {
		return ServerDate;
	}

	public void setServerDate(String serverdate) {
		ServerDate = serverdate;
	}

	public NewsPaper(int id, String name, int typeid, String url,
			String serverdate) {
		super();
		Id = id;
		Name = name;
		TypeId = typeid;
		Url = url;
		ServerDate = serverdate;
	}

}
