package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
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

	public Dialog_notificationlike(Context context, int t, int t1, int t2) {
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
		final ListView listnewlikef = (ListView) findViewById(R.id.listnewlikef);
		final ListView listnewlikeo = (ListView) findViewById(R.id.listnewlikeo);
		final ListView listnewlikep = (ListView) findViewById(R.id.listnewlikep);
		final TextView numlikef = (TextView) findViewById(R.id.numlikef);
		final TextView numlikeo = (TextView) findViewById(R.id.numlikeo);
		final TextView numlikep = (TextView) findViewById(R.id.numlikep);

		dbadapter.open();

		int t1 = dbadapter.NumOfNewLikeInObject(user.getId());

		int t = dbadapter.NumOfNewLikeInFroum(user.getId());
		int t2 = dbadapter.NumOfNewLikeInPaper(user.getId());
		int t3 = t + t1 + t2;

		if (t == 0) {
			numlikef.setVisibility(View.GONE);
		} else {
			numlikef.setText("" + t);
		}
		;
		if (t1 == 0) {
			numlikeo.setVisibility(View.GONE);
		} else {
			numlikeo.setText("" + t1);
		}
		;
		if (t2 == 0) {
			numlikep.setVisibility(View.GONE);
		} else {
			numlikep.setText("" + t2);
		}
		;

		// ArrayList<LikeInObject> mylist =
		// dbadapter.getUnseenlike(user.getId());
		// ArrayList<LikeInFroum> mylist1 = dbadapter.getUnseenlikeInFroum(user

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

		btnshowlikeo.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				numlikeo.setVisibility(View.GONE);
				listnewlikeo.setAdapter(dataAdapter);

				listnewlikef.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, new ObjectFragment());
						trans.commit();
						dismiss();
					}
				});
			}
		});
		btnshowlikef.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				numlikef.setVisibility(View.GONE);
				listnewlikef.setAdapter(dataAdapter1);

				listnewlikef.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame,
								new FroumtitleFragment());
						trans.commit();
						dismiss();
					}
				});

			}
		});
		btnshowlikep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				numlikep.setVisibility(View.GONE);
				listnewlikep.setAdapter(dataAdapter2);

				listnewlikep.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame,
								new TitlepaperFragment());
						trans.commit();
						dismiss();
					}
				});

			}
		});

	}

}
