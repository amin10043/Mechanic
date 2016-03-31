package com.project.mechanic.entity;

public class ExtraSettings {
	int Id;
	String Key;
	String Value;

	public ExtraSettings(int id, String key, String Value) {
		this.Id = id;
		this.Key = key;
		this.Value = Value;

	}

	public int getId() {
		return Id;
	}

	public void setId(int id) {
		Id = id;
	}

	public String getKey() {
		return Key;
	}

	public void setKey(String key) {
		Key = key;
	}

	public String getValue() {
		return Value;
	}

	public void setValue(String value) {
		Value = value;
	}

}
