package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.LikeFroumNotificationAdapter;
import com.project.mechanic.adapter.LikeNotificationAdapter;
import com.project.mechanic.adapter.LikePaperNotificationAdapter;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
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
		final ListView listnewlikef = (ListView) findViewById(R.id.listnewlikef);
		final ListView listnewlikeo = (ListView) findViewById(R.id.listnewlikeo);
		final ListView listnewlikep = (ListView) findViewById(R.id.listnewlikep);

		// listnotificationlike= (ListView)
		// findViewById(R.id.listnotificationlike);
		// listnotificationlike1= (ListView)
		// findViewById(R.id.listnotificationlike1);
		// listnotificationlike2= (ListView)
		// findViewById(R.id.listnotificationlike2);

		dbadapter.open();

		ArrayList<LikeInObject> mylist = dbadapter.getUnseenlike(user.getId());
		ArrayList<LikeInFroum> mylist1 = dbadapter.getUnseenlikeInFroum(user
				.getId());
		ArrayList<LikeInPaper> mylist2 = dbadapter.getUnseenlikeInPaper(user
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
				listnewlikeo.setAdapter(dataAdapter);

				listnewlikeo
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								FragmentTransaction trans = ((MainActivity) context)
										.getSupportFragmentManager()
										.beginTransaction();
								trans.replace(R.id.content_frame,
										new ObjectFragment());
								trans.commit();

							}
						});
			}
		});
		btnshowlikef.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlikef.setAdapter(dataAdapter1);

				listnewlikef
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								FragmentTransaction trans = ((MainActivity) context)
										.getSupportFragmentManager()
										.beginTransaction();
								trans.replace(R.id.content_frame,
										new FroumFragment());
								trans.commit();

							}
						});

			}
		});
		btnshowlikep.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				listnewlikep.setAdapter(dataAdapter2);

				listnewlikep
						.setOnItemClickListener(new AdapterView.OnItemClickListener() {

							@Override
							public void onItemClick(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								FragmentTransaction trans = ((MainActivity) context)
										.getSupportFragmentManager()
										.beginTransaction();
								trans.replace(R.id.content_frame,
										new PaperFragment());
								trans.commit();

							}

						});

			}
		});

		// if(listnotificationlike!=null){
		// listnotificationlike.setAdapter(dataAdapter);}
		//
		// else{listnotificationlike.setVisibility(View.INVISIBLE);
		//
		// }
		//
		//
		// if(listnotificationlike1!=null){
		// listnotificationlike1.setAdapter(dataAdapter1); }
		// else{listnotificationlike1.setVisibility(View.INVISIBLE);
		//
		// }
		// if(listnotificationlike2!=null){
		// listnotificationlike2.setAdapter(dataAdapter2); }
		// else{listnotificationlike2.setVisibility(View.INVISIBLE);
		//
		// }

		// listnotificationlike.setAdapter(dataAdapter);
		// listnotificationlike1.setAdapter(dataAdapter1);
		// listnotificationlike2.setAdapter(dataAdapter2);

	}

}
