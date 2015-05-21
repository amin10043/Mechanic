package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.CommentObjectNotificationAdapter;
import com.project.mechanic.adapter.CommentPaperNotificationAdapter;
import com.project.mechanic.adapter.commentnotificationAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.CommentInPaper;
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
	ListView listcmnotification2;
	ListView listcmnotification3;
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
		listcmnotification2= (ListView) findViewById(R.id.listcmnotification2);
		listcmnotification3= (ListView) findViewById(R.id.listcmnotification3);
		
		
		
		adapter.open();

		ArrayList<CommentInFroum> mylist = adapter.getUnseencomment();
		ArrayList<CommentInObject> mylist1 = adapter.getUnseencommentobject();
		ArrayList<CommentInPaper> mylist2 = adapter.getUnseencommentpaper();
		
		adapter.close();
		commentnotificationAdapter dataAdapter = new  commentnotificationAdapter(context,R.layout.row_notification_list,mylist);
		CommentObjectNotificationAdapter dataAdapter1 = new  CommentObjectNotificationAdapter(context, R.layout.row_notification_list,mylist1);
		CommentPaperNotificationAdapter dataAdapter2 = new  CommentPaperNotificationAdapter(context, R.layout.row_notification_list,mylist2);
		
		//if(listcmnotification!=null){
		//	listcmnotification.setAdapter(dataAdapter);	}
		//else{listcmnotification.setVisibility(View.GONE);
			
//		commentnotificationAdapter dataAdapter = new commentnotificationAdapter(
//						context, android.R.layout.simple_list_item_1, mylist);
		listcmnotification.setAdapter(dataAdapter);
				listcmnotification2.setAdapter(dataAdapter1);
				listcmnotification3.setAdapter(dataAdapter2);
			
	}

}
