package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import com.project.mechanic.adapter.CommentObjectNotificationAdapter;
import com.project.mechanic.adapter.CommentPaperNotificationAdapter;
import com.project.mechanic.adapter.commentnotificationAdapter;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.CommentNotiItem;
import com.project.mechanic.row_items.LikeNotiItem;
import com.project.mechanic.utility.Utility;

public class Dialog_notification extends Dialog {
	public void tasks() {
	}

	private static final Context Dialog = null;
	// private DataBaseAdapter dbadapter;
	private Context context;
	private Fragment fragment;
	private int seen;
	ListView listcmnotification;
	DataBaseAdapter adapter;
	Utility util;
	Users user;

	boolean f1 ;
	boolean f2 ;
	boolean f3 ;

	public Dialog_notification(Context context, int r, int r1, int r2) {
		super(context);
		this.context = context;
		// dbadapter = new DataBaseAdapter(context);
		adapter = new DataBaseAdapter(context);
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

		setContentView(R.layout.dialog_notification1);

		ImageButton allCommentFroum = (ImageButton) findViewById(R.id.btnshowcmf);
		ImageButton allCommentObject = (ImageButton) findViewById(R.id.btnshowcmo);
		ImageButton allCommentPaper = (ImageButton) findViewById(R.id.btnshowcmp);

		final ListView listnewcmf = (ListView) findViewById(R.id.listnewcmf);

		final ImageView soundPaper = (ImageView) findViewById(R.id.soundpaper);
		final ImageView soundPage = (ImageView) findViewById(R.id.soundpage);
		final ImageView soundFroum = (ImageView) findViewById(R.id.soundFroum);

		final TextView numf = (TextView) findViewById(R.id.numf);
		final TextView numo = (TextView) findViewById(R.id.numo);
		final TextView nump = (TextView) findViewById(R.id.nump);
		
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

		adapter.open();

		int r = adapter.NumOfNewCmtInFroum(user.getId());
		int r1 = adapter.NumOfNewCmtInObject(user.getId());
		int r2 = adapter.NumOfNewCmtInPaper(user.getId());

		if (r == 0) {
			//numf.setVisibility(View.INVISIBLE);
			l1.setVisibility(View.INVISIBLE);

		} else {
			numf.setText("" + r);
		}
		;

		if (r1 == 0) {
			//numo.setVisibility(View.INVISIBLE);
			l2.setVisibility(View.INVISIBLE);

		} else {
			numo.setText("" + r1);
		}
		;
		if (r2 == 0) {
			//nump.setVisibility(View.INVISIBLE);
			l3.setVisibility(View.INVISIBLE);

		} else {
			nump.setText("" + r2);
		}
		;

		adapter.close();

		allCommentFroum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
			//	numf.setVisibility(View.INVISIBLE);
				l1.setVisibility(View.INVISIBLE);

				adapter.open();
				final ArrayList<CommentNotiItem> mylist = adapter
						.getUnseencomment(user.getId());
				final commentnotificationAdapter dataAdapter = new commentnotificationAdapter(
						context, R.layout.row_notification_list, mylist);
				listnewcmf.setAdapter(dataAdapter);

				for (int i = 0; i < mylist.size(); i++) {

					CommentNotiItem comment = mylist.get(i);
					adapter.updatecmseentodb(1, comment.getCommentId());

				}
				adapter.close();

				listnewcmf.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						FroumFragment fragment = new FroumFragment();
						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, fragment);

						CommentNotiItem comment = mylist.get(position);

						adapter.open();

						CommentInFroum c = adapter
								.getCommentInFroumbyID(comment.getCommentId());
						Froum f = adapter.getFroumItembyid(c.getFroumid());
						adapter.close();

						trans.setCustomAnimations(R.anim.pull_in_left,
								R.anim.push_out_right);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(f.getId()));
						fragment.setArguments(bundle);

						trans.commit();
						dismiss();

					}

				});

			}
		});

		allCommentObject.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//numo.setVisibility(View.INVISIBLE);
				l2.setVisibility(View.INVISIBLE);

				adapter.open();
				final ArrayList<CommentNotiItem> mylist1 = adapter
						.getUnseencommentobject(user.getId());

				final CommentObjectNotificationAdapter dataAdapter1 = new CommentObjectNotificationAdapter(
						context, R.layout.row_notification_list, mylist1);
				listnewcmf.setAdapter(dataAdapter1);

				for (int i = 0; i < mylist1.size(); i++) {

					CommentNotiItem comment = mylist1.get(i);
					adapter.updatecmobjectseentodb(1, comment.getCommentId());

				}
				adapter.close();

				listnewcmf.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						IntroductionFragment fragment = new IntroductionFragment();
						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, fragment);

						CommentNotiItem comment = mylist1.get(position);

						adapter.open();

						CommentInObject c = adapter
								.getCommentInObjectbyID(comment.getCommentId());
						Object o = adapter.getObjectbyid(c.getObjectid());
						adapter.close();

						trans.setCustomAnimations(R.anim.pull_in_left,
								R.anim.push_out_right);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(o.getId()));
						fragment.setArguments(bundle);

						trans.commit();
						dismiss();
					}
				});

			}
		});
		allCommentPaper.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//nump.setVisibility(View.INVISIBLE);
				l3.setVisibility(View.INVISIBLE);

				adapter.open();

				final ArrayList<CommentNotiItem> mylist2 = adapter
						.getUnseencommentpaper(user.getId());
				final CommentPaperNotificationAdapter dataAdapter2 = new CommentPaperNotificationAdapter(
						context, R.layout.row_notification_list, mylist2);
				listnewcmf.setAdapter(dataAdapter2);

				for (int i = 0; i < mylist2.size(); i++) {

					CommentNotiItem comment = mylist2.get(i);
					adapter.updatecmpaperseentodb(1, comment.getCommentId());

				}
				adapter.close();

				listnewcmf.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						PaperFragment fragment = new PaperFragment();

						android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
								.getSupportFragmentManager().beginTransaction();
						trans.replace(R.id.content_frame, fragment);

						CommentNotiItem comment = mylist2.get(position);

						adapter.open();

						CommentInPaper c = adapter
								.getCommentInPaperbyID((comment.getCommentId()));

						Paper p = adapter.getPaperItembyid((c.getPaperid()));
						adapter.close();

						trans.setCustomAnimations(R.anim.pull_in_left,
								R.anim.push_out_right);
						Bundle bundle = new Bundle();
						bundle.putString("Id", String.valueOf(p.getId()));
						fragment.setArguments(bundle);

						trans.commit();
						dismiss();
					}
				});

			}

		});
	}
}
