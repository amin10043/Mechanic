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
	// private DataBaseAdapter dbadapter;
	private Context context;
	private Fragment fragment;
	private int seen;
	ListView listcmnotification;
	ListView listcmnotification2;
	ListView listcmnotification3;
	DataBaseAdapter adapter;
	Utility util;
	Users user;

	public Dialog_notification(Context context) {
		super(context);
		this.context = context;
		// dbadapter = new DataBaseAdapter(context);
		// TODO Auto-generated constructor stub
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		user = util.getCurrentUser();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if (user == null) {
			return;
		}
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.dialog_notification1);

		// listcmnotification = (ListView)
		// findViewById(R.id.listcmnotification);
		// // String [] aa = {c.getDesk(),c.getDesk(),c.getDesk(),c.getDesk()};
		// listcmnotification2 = (ListView)
		// findViewById(R.id.listcmnotification2);
		// listcmnotification3 = (ListView)
		// findViewById(R.id.listcmnotification3);

		// final LinearLayout linlist1 = (LinearLayout)
		// findViewById(R.id.linlist1);
		// final LinearLayout linlist2 = (LinearLayout)
		// findViewById(R.id.linlist2);
		// final LinearLayout linlist3 = (LinearLayout)
		// findViewById(R.id.linlist3);

		// AbsListView.LayoutParams lp = new
		// AbsListView.LayoutParams(linlist1.getLayoutParams());
		ImageButton btnshowcmf = (ImageButton) findViewById(R.id.btnshowcmf);
		ImageButton btnshowcmo = (ImageButton) findViewById(R.id.btnshowcmo);
		ImageButton btnshowcmp = (ImageButton) findViewById(R.id.btnshowcmp);
		final ListView listnewcmf = (ListView) findViewById(R.id.listnewcmf);
		final ListView listnewcmo = (ListView) findViewById(R.id.listnewcmo);
		final ListView listnewcmp = (ListView) findViewById(R.id.listnewcmp);
		//
		adapter.open();
		// Users u = util.getCurrentUser();
		// int id = u.getId();

		ArrayList<CommentInFroum> mylist = adapter.getUnseencomment(user
				.getId());
		ArrayList<CommentInObject> mylist1 = adapter
				.getUnseencommentobject(user.getId());
		ArrayList<CommentInPaper> mylist2 = adapter.getUnseencommentpaper(user
				.getId());

		adapter.close();
		final commentnotificationAdapter dataAdapter = new commentnotificationAdapter(
				context, R.layout.row_notification_list, mylist);
		final CommentObjectNotificationAdapter dataAdapter1 = new CommentObjectNotificationAdapter(
				context, R.layout.row_notification_list, mylist1);
		final CommentPaperNotificationAdapter dataAdapter2 = new CommentPaperNotificationAdapter(
				context, R.layout.row_notification_list, mylist2);

		btnshowcmf.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewcmf.setAdapter(dataAdapter);

			}
		});
		btnshowcmo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewcmo.setAdapter(dataAdapter1);

			}
		});
		btnshowcmp.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewcmp.setAdapter(dataAdapter2);

			}

		});
		// listnewcmf.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// FragmentTransaction trans = ((FragmentActivity) context)
		// .getSupportFragmentManager().beginTransaction();
		// trans.replace(R.id.content_frame, new FroumFragment());
		// trans.commit();
		//
		// }
		// });
		//
		// listnewcmo.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// FragmentTransaction trans = ((FragmentActivity) context)
		// .getSupportFragmentManager().beginTransaction();
		// trans.replace(R.id.content_frame, new ObjectFragment());
		// trans.commit();
		//
		// }
		// });
		//
		// listnewcmp.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// // TODO Auto-generated method stub
		// FragmentTransaction trans = ((FragmentActivity) context)
		// .getSupportFragmentManager().beginTransaction();
		// trans.replace(R.id.content_frame, new PaperFragment());
		// trans.commit();
		//
		// }
		// });

		// listcmnotification.setAdapter(dataAdapter);
		// listcmnotification2.setAdapter(dataAdapter1);
		// listcmnotification3.setAdapter(dataAdapter2);
	}

	// if (listcmnotification2 != null) {
	//
	// }
	//
	// else {
	//
	// linlist2.setVisibility(View.GONE);

	// Toast.makeText(context, "dovomi khali ",
	// Toast.LENGTH_LONG).show();

}

// if (listcmnotification != null) {
// if (listcmnotification2 != null && listcmnotification3 != null) {
// ListView.LayoutParams lp2 = new ListView.LayoutParams(
// listcmnotification.getLayoutParams());
// lp2.height = (int) ((util.getScreenHeight() / 3));
// listcmnotification.setLayoutParams(lp2);

// } else if (listcmnotification2 != null
// || listcmnotification3 != null) {
//
// Toast.makeText(context, "dovomi khali ", Toast.LENGTH_LONG)
// .show();
// ListView.LayoutParams lp1 = new ListView.LayoutParams(
// listcmnotification.getLayoutParams());
// lp1.height = (int) ((util.getScreenHeight() / 2));
// listcmnotification.setLayoutParams(lp1);

// } else {
// // ListView.LayoutParams lp = new ListView.LayoutParams(
// listcmnotification.getLayoutParams());
// lp.height = (int) ((util.getScreenHeight()));
// listcmnotification.setLayoutParams(lp);

// }
// }
// if (listcmnotification2 != null) {
// if (listcmnotification != null && listcmnotification3 != null) {
// ListView.LayoutParams lp5 = new ListView.LayoutParams(
// listcmnotification2.getLayoutParams());
// lp5.height = (int) ((util.getScreenHeight() / 3));
// listcmnotification2.setLayoutParams(lp5);

// } else if (listcmnotification != null
// || listcmnotification3 != null) {
// ListView.LayoutParams lp4 = new ListView.LayoutParams(
// listcmnotification2.getLayoutParams());
// lp4.height = (int) ((util.getScreenHeight() / 2));
// listcmnotification2.setLayoutParams(lp4);

// } else {
// ListView.LayoutParams lp3 = new ListView.LayoutParams(
// listcmnotification2.getLayoutParams());
// lp3.height = (int) ((util.getScreenHeight()));
// listcmnotification2.setLayoutParams(lp3);

// }
// }

// if (listcmnotification3 != null) {
// if (listcmnotification != null && listcmnotification2 != null) {
// ListView.LayoutParams lp8 = new ListView.LayoutParams(
// listcmnotification3.getLayoutParams());
// lp8.height = (int) ((util.getScreenHeight() / 3));
// listcmnotification3.setLayoutParams(lp8);
// } else if (listcmnotification2 != null
// || listcmnotification != null) {
// ListView.LayoutParams lp7 = new ListView.LayoutParams(
// listcmnotification3.getLayoutParams());
// lp7.height = (int) ((util.getScreenHeight() / 2));
// listcmnotification3.setLayoutParams(lp7);
// } else {
// ListView.LayoutParams lp6 = new ListView.LayoutParams(
// listcmnotification3.getLayoutParams());
// lp6.height = (int) ((util.getScreenHeight()));
// listcmnotification3.setLayoutParams(lp6);

// }
// }

