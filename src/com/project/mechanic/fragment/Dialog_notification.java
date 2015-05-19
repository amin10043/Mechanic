package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.commentnotificationAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class Dialog_notification extends Dialog {

	private static final Context Dialog = null;
	//private DataBaseAdapter dbadapter;
	private Context context;
	private Fragment fragment;
	private int seen;
	ListView listcmnotification;
	DataBaseAdapter adapter;


	public Dialog_notification(Context context) {
		super(context);
		this.context = context;
		//dbadapter = new DataBaseAdapter(context);
		// TODO Auto-generated constructor stub
		adapter = new DataBaseAdapter(context);

	}

	@Override
public void onCreate(Bundle savedInstanceState) {
	// TODO Auto-generated method stub
	super.onCreate(savedInstanceState);
	setContentView(R.layout.dialog_notification);
	
	

	

	
		listcmnotification= (ListView) findViewById(R.id.listcmnotification);
//		String [] aa = {c.getDesk(),c.getDesk(),c.getDesk(),c.getDesk()};
		
		
		
		adapter.open();

		ArrayList<CommentInFroum> mylist = adapter.getUnseencomment();
		
		adapter.close();
		commentnotificationAdapter dataAdapter = new  commentnotificationAdapter(context,R.layout.row_notification_list,mylist);
		
		
//		commentnotificationAdapter dataAdapter = new commentnotificationAdapter(
//						context, android.R.layout.simple_list_item_1, mylist);
				listcmnotification.setAdapter(dataAdapter);			
			
	}

}
