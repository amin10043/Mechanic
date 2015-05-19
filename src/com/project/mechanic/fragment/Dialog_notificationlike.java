package com.project.mechanic.fragment;

import java.util.ArrayList;

import com.project.mechanic.R;
import com.project.mechanic.adapter.LikeNotificationAdapter;
import com.project.mechanic.adapter.commentnotificationAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Object;
import com.project.mechanic.model.DataBaseAdapter;

import android.R.array;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Dialog_notificationlike extends Dialog{
	private static final Context Dialog = null;
	private DataBaseAdapter dbadapter;
	private Context context;
	private Fragment fragment;
	private int seen;
	ListView listnotificationlike;

	public Dialog_notificationlike(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		dbadapter = new DataBaseAdapter(context);
		// TODO Auto-generated constructor stub
	}

	@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dialog_notificationlike);
	
	

	

	
		
		
		listnotificationlike= (ListView) findViewById(R.id.listnotificationlike);
		dbadapter.open();
		
		ArrayList<Object> mylist = dbadapter.getUnseenlike();
		//CommentInFroum c = dbadapter.getCommentInFroumbyID(1);
		//String [] aa = {c.getDesk(),c.getDesk(),c.getDesk(),c.getDesk()};
		dbadapter.close();
		
		
		LikeNotificationAdapter dataAdapter = new  LikeNotificationAdapter(context,R.layout.row_notification_list,mylist);
		
		//ArrayAdapter dataAdapter = new ArrayAdapter(
						//context, android.R.layout.simple_list_item_1, mylist);
				//listnotificationlike.setAdapter(dataAdapter);
			
			
		listnotificationlike.setAdapter(dataAdapter);	
			
	}



	}


