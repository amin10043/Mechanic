package com.project.mechanic.row_items;

import java.util.ArrayList;

import com.project.mechanic.entity.Froum;
import com.project.mechanic.model.DataBaseAdapter;

public class FroumItem {
	
	String Username;
	String comment;
	DataBaseAdapter adapter;
	
	public String getComment()
	   { return this.comment;}
	public void setComment(String comment)
	   {this.comment=comment;}
	public String getUsername(){
		return this.Username;
		
	}
	
	public void setUsername(String username)
	   {this.Username=username;}
	
	
	
}
