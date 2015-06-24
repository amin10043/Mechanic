package com.project.mechanic.adapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.FroumWithoutComment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

@SuppressLint("SimpleDateFormat")
public class FroumtitleListadapter extends ArrayAdapter<Froum> implements
		AsyncInterface {

	Context context;
	List<Froum> mylist;
	DataBaseAdapter adapter;
	Utility util;
	Users CurrentUser;
	LinearLayout LikeTitle;
	int froumNumber;
	TextView countLikeFroum;
	ProgressDialog ringProgressDialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	String serverDate = "";
	ServerDate date;

	public FroumtitleListadapter(Context context, int resource,
			List<Froum> objects) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		adapter = new DataBaseAdapter(context);

		convertView = myInflater
				.inflate(R.layout.raw_froumtitle, parent, false);

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rowtitlepaper);
		TextView txt2 = (TextView) convertView
				.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		TextView countcommentfroum = (TextView) convertView
				.findViewById(R.id.countCommentInEveryTopic);
		TextView dateTopic = (TextView) convertView
				.findViewById(R.id.datetopicinFroum);
		countLikeFroum = (TextView) convertView
				.findViewById(R.id.countLikeInFroumTitle);
		ImageView profileImg = (ImageView) convertView
				.findViewById(R.id.iconfroumtitle);
		LinearLayout commenttitle = (LinearLayout) convertView
				.findViewById(R.id.l1cm);
		LikeTitle = (LinearLayout) convertView
				.findViewById(R.id.liketitleTopic);

		Froum person1 = mylist.get(position);

		adapter.open();

		Users x = adapter.getUserbyid(person1.getUserId());
		CurrentUser = util.getCurrentUser();

		if (person1.getSeenBefore() > 0) {
			txt1.setTextColor(Color.GRAY);
			txt2.setTextColor(Color.GRAY);
			txt3.setTextColor(Color.GRAY);
			dateTopic.setTextColor(Color.GRAY);

		}
		txt1.setText(person1.getTitle());
		txt2.setText(person1.getDescription() + "mmmmm");

		txt2.setText(person1.getDescription());
		if (x != null)
			txt3.setText(x.getName());
		countcommentfroum.setText(adapter.CommentInFroum_count(person1.getId())
				.toString());
		countLikeFroum.setText(adapter.LikeInFroum_count(person1.getId())
				.toString());

		dateTopic.setText(util.getPersianDate(person1.getDate()));

		String item = txt1.getText().toString();
		int ItemId = 0;
		for (Froum listItem : mylist) {
			if (item.equals(listItem.getTitle())) {
				froumNumber = ItemId = listItem.getId();
			}
		}
		countLikeFroum.setText(adapter.LikeInFroum_count(ItemId).toString());

		if (CurrentUser == null) {
			LikeTitle.setBackgroundResource(R.drawable.like_froum_off);

		} else {
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumNumber)) {
				LikeTitle.setBackgroundResource(R.drawable.like_froum);
			} else
				LikeTitle.setBackgroundResource(R.drawable.like_froum_off);
		}

		if (x != null) {

			if (x.getImage() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				RelativeLayout rl = (RelativeLayout) convertView
						.findViewById(R.id.topicTitleFroum);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
						rl.getLayoutParams());

				lp.width = util.getScreenwidth() / 7;
				lp.height = util.getScreenwidth() / 7;
				lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp.setMargins(5, 5, 5, 5);
				profileImg.setLayoutParams(lp);
			} else {

				byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeByteArray(byteImg, 0,
						byteImg.length);
				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				RelativeLayout rl = (RelativeLayout) convertView
						.findViewById(R.id.topicTitleFroum);
				RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
						rl.getLayoutParams());

				lp.width = util.getScreenwidth() / 7;
				lp.height = util.getScreenwidth() / 7;
				lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
				lp.setMargins(5, 5, 5, 5);
				profileImg.setLayoutParams(lp);
				adapter.close();
			}
		}
		adapter.close();

		LikeTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(context,
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					String item = txt1.getText().toString();
					int ItemId = 0;
					for (Froum listItem : mylist) {
						if (item.equals(listItem.getTitle())) {
							// check authentication and authorization
							froumNumber = ItemId = listItem.getId();
						}
					}

				}
				date = new ServerDate(context);
				date.delegate = FroumtitleListadapter.this;
				date.execute("");
				adapter.close();

			}

		});
		final SharedPreferences abc = context.getSharedPreferences("Id", 0);
		final SharedPreferences froumId = context.getSharedPreferences("Id", 0);

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();
				int ItemId = 0;
				for (Froum listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						ItemId = listItem.getId();
					}
				}

				adapter.open();
				adapter.SetSeen("Froum", ItemId, "1");
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				FroumWithoutComment fragment = new FroumWithoutComment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 1).commit();
				abc.edit()
						.putInt("Froum_List_Id",
								((ListView) parent).getFirstVisiblePosition())
						.commit();
				froumId.edit().putInt("main_Id", ItemId).commit();

			}
		});
		commenttitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();
				int ItemId = 0;
				for (Froum listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						ItemId = listItem.getId();
					}
				}

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				FroumFragment fragment = new FroumFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 2).commit();

			}

		});
		return convertView;
	}

	@Override
	public void processFinish(String output) {
		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		int id = -1;

		try {
			id = Integer.valueOf(output);
			adapter.open();
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumNumber)) {
				adapter.deleteLikeFromFroum(CurrentUser.getId(), froumNumber);
				LikeTitle.setBackgroundResource(R.drawable.like_froum_off);

				countLikeFroum.setText(adapter.LikeInFroum_count(froumNumber)
						.toString());

			} else {
				adapter.insertLikeInFroumToDb(CurrentUser.getId(), froumNumber,
						serverDate, 0);
				LikeTitle.setBackgroundResource(R.drawable.like_froum);

				countLikeFroum.setText(adapter.LikeInFroum_count(froumNumber)
						.toString());
			}
			adapter.close();

		} catch (NumberFormatException ex) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				adapter.open();

				if (adapter.isUserLikedFroum(CurrentUser.getId(), froumNumber)) {
					adapter.open();
					// int c = adapter.LikeInFroum_count(ItemId) - 1;
					// countLikeFroum.setText(String.valueOf(c));

					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(context);
					deleting.delegate = FroumtitleListadapter.this;

					params.put("TableName", "LikeInFroum");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(froumNumber));

					deleting.execute(params);

					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

					adapter.close();
				} else {
					adapter.open();
					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = FroumtitleListadapter.this;

					params.put("TableName", "LikeInFroum");

					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(froumNumber));
					params.put("CommentId", "0");
					params.put("Date", output);
					params.put("IsUpdate", "0");
					params.put("Id", "0");

					serverDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

					// countLikeFroum.setText(adapter
					// .LikeInFroum_count(ItemId).toString());

					adapter.close();

				}
				adapter.close();

			} else {
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
						Toast.LENGTH_SHORT).show();
			}
		}

		catch (Exception e) {

			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

}
