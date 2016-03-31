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
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.CommentObjectNotificationAdapter;
import com.project.mechanic.adapter.CommentPaperNotificationAdapter;
import com.project.mechanic.adapter.LikeFroumNotificationAdapter;
import com.project.mechanic.adapter.LikeNotificationAdapter;
import com.project.mechanic.adapter.LikePaperNotificationAdapter;
import com.project.mechanic.adapter.commentnotificationAdapter;
import com.project.mechanic.chat.SetAdminNoGroup;
import com.project.mechanic.chat.TabHostChatType;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.LikeInObject;
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

	boolean f1;
	boolean f2;
	boolean f3;

	RadioGroup radioGroup;
	RadioButton r1, r2;

	String commentLable = "   ‰Ÿ—« ";
	String likeLable = "   Å”‰œÂ«";
	boolean commentOrLike = true; // comment == true /// like == false

	TextView numf, numo, nump;

	LinearLayout l1, l2, l3;

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
		setContentView(R.layout.dialog_notification1);
		getWindow().setLayout(util.getScreenwidth(), WindowManager.LayoutParams.WRAP_CONTENT);

		r1 = (RadioButton) findViewById(R.id.r1);
		r2 = (RadioButton) findViewById(R.id.r2);

		r1.setText(commentLable);
		r2.setText(likeLable);

		r1.setTypeface(util.SetFontIranSans());
		r2.setTypeface(util.SetFontIranSans());

		radioGroup = (RadioGroup) findViewById(R.id.rb1);

		final LinearLayout informationLayout = (LinearLayout) findViewById(R.id.information);

		ImageButton allCommentFroum = (ImageButton) findViewById(R.id.btnshowcmf);
		ImageButton allCommentObject = (ImageButton) findViewById(R.id.btnshowcmo);
		ImageButton allCommentPaper = (ImageButton) findViewById(R.id.btnshowcmp);

		final ListView listnewcmf = (ListView) findViewById(R.id.listnewcmf);

		final ImageView soundPaper = (ImageView) findViewById(R.id.soundpaper);
		final ImageView soundPage = (ImageView) findViewById(R.id.soundpage);
		final ImageView soundFroum = (ImageView) findViewById(R.id.soundFroum);

		numf = (TextView) findViewById(R.id.numf);
		numo = (TextView) findViewById(R.id.numo);
		nump = (TextView) findViewById(R.id.nump);

		l1 = (LinearLayout) findViewById(R.id.t00);
		l2 = (LinearLayout) findViewById(R.id.t2);
		l3 = (LinearLayout) findViewById(R.id.t3);
		
		TextView lable1 = (TextView)findViewById(R.id.textView3);
		TextView lable2 = (TextView)findViewById(R.id.textView6);
		TextView lable3 = (TextView)findViewById(R.id.textDay);
		
		lable1.setTypeface(util.SetFontIranSans());
		lable2.setTypeface(util.SetFontIranSans());
		lable3.setTypeface(util.SetFontIranSans());
		

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

		setNumber();

		allCommentFroum.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// numf.setVisibility(View.INVISIBLE);
				l1.setVisibility(View.INVISIBLE);

				adapter.open();

				if (commentOrLike) {

					final ArrayList<CommentNotiItem> mylist = adapter.getUnseencomment(user.getId());
					final commentnotificationAdapter dataAdapter = new commentnotificationAdapter(context,
							R.layout.row_notification_list, mylist);
					listnewcmf.setAdapter(dataAdapter);

					for (int i = 0; i < mylist.size(); i++) {

						CommentNotiItem comment = mylist.get(i);
						adapter.updatecmseentodb(1, comment.getCommentId());

					}

					listnewcmf.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
							FroumFragment fragment = new FroumFragment();
							android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
									.getSupportFragmentManager().beginTransaction();
							trans.replace(R.id.content_frame, fragment);

							CommentNotiItem comment = mylist.get(position);

							adapter.open();

							CommentInFroum c = adapter.getCommentInFroumbyID(comment.getCommentId());
							Froum f = adapter.getFroumItembyid(c.getFroumid());
							adapter.close();

							trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(f.getId()));
							fragment.setArguments(bundle);

							trans.commit();
							dismiss();

						}

					});

				} else {

					adapter.open();
					final ArrayList<LikeNotiItem> mylist1 = adapter.getUnseenlikeInFroum(user.getId());
					final LikeFroumNotificationAdapter dataAdapter1 = new LikeFroumNotificationAdapter(context,
							R.layout.row_notification_list, mylist1);
					listnewcmf.setAdapter(dataAdapter1);

					for (int i = 0; i < mylist1.size(); i++) {

						LikeNotiItem like = mylist1.get(i);
						adapter.updatelikefroumseentodb(1, like.getLikeId());

					}

					listnewcmf.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

							FroumFragment fragment = new FroumFragment();

							android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
									.getSupportFragmentManager().beginTransaction();
							trans.replace(R.id.content_frame, fragment);

							LikeNotiItem like = mylist1.get(position);

							adapter.open();

							LikeInFroum l = adapter.getLikeInFroumById(like.getLikeId());
							Froum f = adapter.getFroumItembyid(l.getFroumid());
							adapter.close();

							trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(f.getId()));
							fragment.setArguments(bundle);

							trans.commit();
							dismiss();
						}
					});

				}

				adapter.close();

			}
		});

		allCommentObject.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// numo.setVisibility(View.INVISIBLE);
				l2.setVisibility(View.INVISIBLE);

				adapter.open();

				if (commentOrLike) {

					final ArrayList<CommentNotiItem> mylist1 = adapter.getUnseencommentobject(user.getId());

					final CommentObjectNotificationAdapter dataAdapter1 = new CommentObjectNotificationAdapter(context,
							R.layout.row_notification_list, mylist1);
					listnewcmf.setAdapter(dataAdapter1);

					for (int i = 0; i < mylist1.size(); i++) {

						CommentNotiItem comment = mylist1.get(i);
						adapter.updatecmobjectseentodb(1, comment.getCommentId());

					}

					listnewcmf.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
							IntroductionFragment fragment = new IntroductionFragment();
							android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
									.getSupportFragmentManager().beginTransaction();
							trans.replace(R.id.content_frame, fragment);

							CommentNotiItem comment = mylist1.get(position);

							adapter.open();

							CommentInObject c = adapter.getCommentInObjectbyID(comment.getCommentId());
							Object o = adapter.getObjectbyid(c.getObjectid());
							adapter.close();

							trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(o.getId()));
							fragment.setArguments(bundle);

							trans.commit();
							dismiss();
						}
					});

				} else {

					final ArrayList<LikeNotiItem> mylist = adapter.getUnseenlike(user.getId());

					final LikeNotificationAdapter dataAdapter = new LikeNotificationAdapter(context,
							R.layout.row_notification_list, mylist);

					listnewcmf.setAdapter(dataAdapter);

					for (int i = 0; i < mylist.size(); i++) {

						LikeNotiItem like = mylist.get(i);
						adapter.updatecmobjectseentodb(1, like.getLikeId());

					}

					listnewcmf.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

							IntroductionFragment fragment = new IntroductionFragment();

							android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
									.getSupportFragmentManager().beginTransaction();
							trans.replace(R.id.content_frame, fragment);

							LikeNotiItem like = mylist.get(position);

							adapter.open();

							LikeInObject l = adapter.getLikeInObjectById(like.getLikeId());
							com.project.mechanic.entity.Object o = adapter.getObjectbyid(l.getPaperId());
							adapter.close();

							trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(o.getId()));
							fragment.setArguments(bundle);

							trans.commit();
							dismiss();
						}

					});
				}

				adapter.close();

				util.setNoti((Activity) context, user.getId());

			}
		});
		allCommentPaper.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// nump.setVisibility(View.INVISIBLE);
				l3.setVisibility(View.INVISIBLE);

				adapter.open();

				if (commentOrLike) {

					final ArrayList<CommentNotiItem> mylist2 = adapter.getUnseencommentpaper(user.getId());
					final CommentPaperNotificationAdapter dataAdapter2 = new CommentPaperNotificationAdapter(context,
							R.layout.row_notification_list, mylist2);
					listnewcmf.setAdapter(dataAdapter2);

					for (int i = 0; i < mylist2.size(); i++) {

						CommentNotiItem comment = mylist2.get(i);
						adapter.updatecmpaperseentodb(1, comment.getCommentId());

					}

					listnewcmf.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
							PaperFragment fragment = new PaperFragment();

							android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
									.getSupportFragmentManager().beginTransaction();
							trans.replace(R.id.content_frame, fragment);

							CommentNotiItem comment = mylist2.get(position);

							adapter.open();

							CommentInPaper c = adapter.getCommentInPaperbyID((comment.getCommentId()));

							Paper p = adapter.getPaperItembyid((c.getPaperid()));
							adapter.close();

							trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(p.getId()));
							fragment.setArguments(bundle);

							trans.commit();
							dismiss();
						}
					});

				} else {

					final ArrayList<LikeNotiItem> mylist2 = adapter.getUnseenlikeInPaper(user.getId());
					final LikePaperNotificationAdapter dataAdapter2 = new LikePaperNotificationAdapter(context,
							R.layout.row_notification_list, mylist2);
					listnewcmf.setAdapter(dataAdapter2);

					for (int i = 0; i < mylist2.size(); i++) {

						LikeNotiItem like = mylist2.get(i);
						adapter.updatelikepaperseentodb(1, like.getLikeId());

					}

					listnewcmf.setOnItemClickListener(new OnItemClickListener() {

						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

							PaperFragment fragment = new PaperFragment();

							android.support.v4.app.FragmentTransaction trans = ((MainActivity) context)
									.getSupportFragmentManager().beginTransaction();
							trans.replace(R.id.content_frame, fragment);

							LikeNotiItem like = mylist2.get(position);

							adapter.open();

							LikeInPaper l = adapter.getLikeInPaperById(like.getLikeId());

							Paper p = adapter.getPaperItembyid((l.getPaperid()));
							adapter.close();

							trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(p.getId()));
							fragment.setArguments(bundle);

							trans.commit();
							dismiss();
						}
					});

				}

				adapter.close();

			}

		});

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup rd, int checkedId) {

				RadioButton radio = (RadioButton) findViewById(checkedId);

				String title = radio.getText() + "";

				if (title.equals(commentLable)) {

					commentOrLike = true;

					informationLayout.setVisibility(View.VISIBLE);
					
				setNumber();

				}
				if (title.equals(likeLable)) {

					commentOrLike = false;
					informationLayout.setVisibility(View.VISIBLE);
					
					setNumber();


				}

			}
		});

	}

	public void setNumber() {

		adapter.open();

		int r = adapter.NumOfNewCmtInFroum(user.getId());
		int r1 = adapter.NumOfNewCmtInObject(user.getId());
		int r2 = adapter.NumOfNewCmtInPaper(user.getId());

		int t = adapter.NumOfNewLikeInFroum(user.getId());
		int t1 = adapter.NumOfNewLikeInObject(user.getId());
		int t2 = adapter.NumOfNewLikeInPaper(user.getId());

		adapter.close();

		if (r == 0 && t == 0) {
			// numf.setVisibility(View.INVISIBLE);
			l1.setVisibility(View.INVISIBLE);

		} else {
			if (commentOrLike)
				numf.setText("" + r);
			else
				numf.setText("" + t);

		}

		if (r1 == 0 && t1 == 0) {
			// numo.setVisibility(View.INVISIBLE);
			l2.setVisibility(View.INVISIBLE);

		} else {
			if (commentOrLike)
				numo.setText("" + r1);
			else
				numo.setText("" + t1);
		}
		;
		if (r2 == 0 && t2 == 0) {
			// nump.setVisibility(View.INVISIBLE);
			l3.setVisibility(View.INVISIBLE);

		} else {
			if (commentOrLike)
				nump.setText("" + r2);
			else
				nump.setText("" + t2);

		}
		;

	}
}
