package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.LikeFroumNotificationAdapter;
import com.project.mechanic.adapter.LikeNotificationAdapter;
import com.project.mechanic.adapter.LikePaperNotificationAdapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.LikeInObject;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Paper;
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

	boolean f1;
	boolean f2;
	boolean f3;

	public Dialog_notificationlike(Context context, int t, int t1, int t2) {
		super(context);
		this.context = context;
		dbadapter = new DataBaseAdapter(context);
		util = new Utility(context);
		user = util.getCurrentUser();
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		if (user == null) {

			return;
		}
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setBackgroundDrawable(
				new ColorDrawable(android.graphics.Color.TRANSPARENT));
		setContentView(R.layout.dialog_notificationlike1);

		ImageButton allLikeFroum = (ImageButton) findViewById(R.id.btnshowlikef);
		ImageButton allLikePage = (ImageButton) findViewById(R.id.btnshowlikeo);
		ImageButton allLikePaper = (ImageButton) findViewById(R.id.btnshowlikep);

		final ListView ItemListView = (ListView) findViewById(R.id.listnewlikef);

		final ImageView soundPaper = (ImageView) findViewById(R.id.soundpaper);
		final ImageView soundPage = (ImageView) findViewById(R.id.soundpage);
		final ImageView soundFroum = (ImageView) findViewById(R.id.soundFroum);

		final TextView numlikef = (TextView) findViewById(R.id.numlikef);
		final TextView numlikeo = (TextView) findViewById(R.id.numlikeo);
		final TextView numlikep = (TextView) findViewById(R.id.numlikep);

		final LinearLayout l1 = (LinearLayout) findViewById(R.id.t00);
		final LinearLayout l2 = (LinearLayout) findViewById(R.id.t2);
		final LinearLayout l3 = (LinearLayout) findViewById(R.id.t3);

		soundPaper.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (f1 == true) {
					soundPaper.setBackgroundResource(R.drawable.ic_sound_on);
					f1 = false;
				} else {
					soundPaper.setBackgroundResource(R.drawable.ic_sound_off);
					f1 = true;
				}
			}
		});

		soundPage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (f2 == true) {
					soundPage.setBackgroundResource(R.drawable.ic_sound_on);
					f2 = false;
				} else {
					soundPage.setBackgroundResource(R.drawable.ic_sound_off);
					f2 = true;
				}
			}
		});

		soundFroum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (f3 == true) {
					soundFroum.setBackgroundResource(R.drawable.ic_sound_on);
					f3 = false;
				} else {
					soundFroum.setBackgroundResource(R.drawable.ic_sound_off);
					f3 = true;
				}
			}
		});

		dbadapter.open();

		int t1 = dbadapter.NumOfNewLikeInObject(user.getId());

		int t = dbadapter.NumOfNewLikeInFroum(user.getId());
		int t2 = dbadapter.NumOfNewLikeInPaper(user.getId());
		int t3 = t + t1 + t2;

		dbadapter.close();

		if (t == 0) {
			//numlikef.setVisibility(View.INVISIBLE);
			l1.setVisibility(View.INVISIBLE);
		} else {
			numlikef.setText("" + t);
		}
		;
		if (t1 == 0) {
			//numlikeo.setVisibility(View.INVISIBLE);
			l2.setVisibility(View.INVISIBLE);

		} else {
			numlikeo.setText("" + t1);
		}
		;
		if (t2 == 0) {
		//	numlikep.setVisibility(View.INVISIBLE);
			l3.setVisibility(View.INVISIBLE);

		} else {
			numlikep.setText("" + t2);
		}
		;

		allLikePage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				//numlikeo.setVisibility(View.INVISIBLE);
				l2.setVisibility(View.INVISIBLE);

				dbadapter.open();

				final ArrayList<LikeNotiItem> mylist = dbadapter
						.getUnseenlike(user.getId());

				final LikeNotificationAdapter dataAdapter = new LikeNotificationAdapter(
						context, R.layout.row_notification_list, mylist);

				ItemListView.setAdapter(dataAdapter);

				for (int i = 0; i < mylist.size(); i++) {

					LikeNotiItem like = mylist.get(i);
					dbadapter.updatecmobjectseentodb(1, like.getLikeId());

				}
				dbadapter.close();

				ItemListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						IntroductionFragment fragment = new IntroductionFragment();

						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, fragment);

						LikeNotiItem like = mylist.get(position);

						dbadapter.open();

						LikeInObject l = dbadapter.getLikeInObjectById(like
								.getLikeId());
						com.project.mechanic.entity.Object o = dbadapter
								.getObjectbyid(l.getPaperId());
						dbadapter.close();

						trans.setCustomAnimations(R.anim.pull_in_left,
								R.anim.push_out_right);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(o.getId()));
						fragment.setArguments(bundle);

						trans.commit();
						dismiss();
					}

				});
				util.setNoti((Activity) context, user.getId());

			}

		});
		allLikeFroum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				//numlikef.setVisibility(View.INVISIBLE);
				l1.setVisibility(View.INVISIBLE);

				dbadapter.open();
				final ArrayList<LikeNotiItem> mylist1 = dbadapter
						.getUnseenlikeInFroum(user.getId());
				final LikeFroumNotificationAdapter dataAdapter1 = new LikeFroumNotificationAdapter(
						context, R.layout.row_notification_list, mylist1);
				ItemListView.setAdapter(dataAdapter1);

				for (int i = 0; i < mylist1.size(); i++) {

					LikeNotiItem like = mylist1.get(i);
					dbadapter.updatelikefroumseentodb(1, like.getLikeId());

				}
				dbadapter.close();

				ItemListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						FroumFragment fragment = new FroumFragment();

						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, fragment);

						LikeNotiItem like = mylist1.get(position);

						dbadapter.open();

						LikeInFroum l = dbadapter.getLikeInFroumById(like
								.getLikeId());
						Froum f = dbadapter.getFroumItembyid(l.getFroumid());
						dbadapter.close();

						trans.setCustomAnimations(R.anim.pull_in_left,
								R.anim.push_out_right);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(f.getId()));
						fragment.setArguments(bundle);

						trans.commit();
						dismiss();
					}
				});

				util.setNoti((Activity) context, user.getId());

				dbadapter.close();

			}

		});
		allLikePaper.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dbadapter.open();
				//numlikep.setVisibility(View.INVISIBLE);
				l3.setVisibility(View.INVISIBLE);

				final ArrayList<LikeNotiItem> mylist2 = dbadapter
						.getUnseenlikeInPaper(user.getId());
				final LikePaperNotificationAdapter dataAdapter2 = new LikePaperNotificationAdapter(
						context, R.layout.row_notification_list, mylist2);
				ItemListView.setAdapter(dataAdapter2);

				for (int i = 0; i < mylist2.size(); i++) {

					LikeNotiItem like = mylist2.get(i);
					dbadapter.updatelikepaperseentodb(1, like.getLikeId());

				}

				dbadapter.close();

				ItemListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {

						PaperFragment fragment = new PaperFragment();

						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, fragment);

						LikeNotiItem like = mylist2.get(position);

						dbadapter.open();

						LikeInPaper l = dbadapter.getLikeInPaperById(like
								.getLikeId());

						Paper p = dbadapter.getPaperItembyid((l.getPaperid()));
						dbadapter.close();

						trans.setCustomAnimations(R.anim.pull_in_left,
								R.anim.push_out_right);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(p.getId()));
						fragment.setArguments(bundle);

						trans.commit();
						dismiss();
					}
				});
				util.setNoti((Activity) context, user.getId());

			}
		});

	}
}
