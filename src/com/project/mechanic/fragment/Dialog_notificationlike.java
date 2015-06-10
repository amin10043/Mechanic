package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.LikeFroumNotificationAdapter;
import com.project.mechanic.adapter.LikeNotificationAdapter;
import com.project.mechanic.adapter.LikePaperNotificationAdapter;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.LikeNotiItem;
import com.project.mechanic.utility.Utility;

public class Dialog_notificationlike extends Dialog {
	private static final Context Dialog = null;
	private DataBaseAdapter dbadapter;
	private Context context;
	private Fragment fragment;
	private int seen;
	ListView listnotificationlike;
	ListView listnotificationlike1;
	ListView listnotificationlike2;
	Users user;
	Utility util;

	public Dialog_notificationlike(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
		this.context = context;
		dbadapter = new DataBaseAdapter(context);
		util = new Utility(context);
		user = util.getCurrentUser();
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (user == null) {
			return;
		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.dialog_notificationlike1);

		ImageButton btnshowlikef = (ImageButton) findViewById(R.id.btnshowlikef);
		ImageButton btnshowlikeo = (ImageButton) findViewById(R.id.btnshowlikeo);
		ImageButton btnshowlikep = (ImageButton) findViewById(R.id.btnshowlikep);
		final ListView listnewlike = (ListView) findViewById(R.id.listnewlike);

		dbadapter.open();
		ArrayList<LikeNotiItem> mylist = dbadapter.getUnseenlike(user.getId());
		ArrayList<LikeNotiItem> mylist1 = dbadapter.getUnseenlikeInFroum(user
				.getId());
		ArrayList<LikeNotiItem> mylist2 = dbadapter.getUnseenlikeInPaper(user
				.getId());
		// CommentInFroum c = dbadapter.getCommentInFroumbyID(1);
		// String [] aa = {c.getDesk(),c.getDesk(),c.getDesk(),c.getDesk()};
		dbadapter.close();

		final LikeNotificationAdapter dataAdapter = new LikeNotificationAdapter(
				context, R.layout.row_notification_list, mylist);
		final LikeFroumNotificationAdapter dataAdapter1 = new LikeFroumNotificationAdapter(
				context, R.layout.row_notification_list, mylist1);
		final LikePaperNotificationAdapter dataAdapter2 = new LikePaperNotificationAdapter(
				context, R.layout.row_notification_list, mylist2);

		btnshowlikef.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlike.setAdapter(dataAdapter);

			}
		});
		btnshowlikeo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlike.setAdapter(dataAdapter1);

			}
		});
		btnshowlikep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlike.setAdapter(dataAdapter2);

			}
		});

	}

}
