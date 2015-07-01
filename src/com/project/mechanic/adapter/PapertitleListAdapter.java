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
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInPaper;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.PaperWithoutComment;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class PapertitleListAdapter extends ArrayAdapter<Paper> implements
		AsyncInterface {

	Context context;
	List<Paper> mylist;
	DataBaseAdapter adapter;
	private TextView NumofComment;
	private TextView NumofLike;
	private TextView DateView;
	int id;
	PersianDate p;
	Utility util;
	LinearLayout likePaper;
	Users currentUser;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	String serverDate = "";
	ServerDate date;
	int paperNumber;
	ProgressDialog ringProgressDialog;
	LinearLayout commentBtn;

	public PapertitleListAdapter(Context context, int resource,
			List<Paper> objects) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		p = new PersianDate();

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater
				.inflate(R.layout.raw_froumtitle, parent, false);

		// start find view

		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rowtitlepaper);
		TextView txt2 = (TextView) convertView
				.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		NumofComment = (TextView) convertView
				.findViewById(R.id.countCommentInEveryTopic);
		NumofLike = (TextView) convertView
				.findViewById(R.id.countLikeInFroumTitle);
		DateView = (TextView) convertView.findViewById(R.id.datetopicinFroum);
		ImageView iconProile = (ImageView) convertView
				.findViewById(R.id.iconfroumtitle);

		likePaper = (LinearLayout) convertView
				.findViewById(R.id.liketitleTopic);

		commentBtn = (LinearLayout) convertView.findViewById(R.id.l1cm);

		// end find view
		adapter.open();
		currentUser = util.getCurrentUser();

		Paper person1 = mylist.get(position);

		if (person1.getSeenBefore() > 0) {
			txt1.setTextColor(Color.GRAY);
			txt2.setTextColor(Color.GRAY);
			txt3.setTextColor(Color.GRAY);
			DateView.setTextColor(Color.GRAY);

		}

		Users x = adapter.getUserbyid(person1.getUserId());

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.topicTitleFroum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);

		if (x != null) {

			if (x.getImage() == null) {
				iconProile.setImageResource(R.drawable.no_img_profile);
				iconProile.setLayoutParams(lp);

			} else {

				byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeByteArray(byteImg, 0,
						byteImg.length);
				iconProile.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				iconProile.setLayoutParams(lp);
				adapter.close();
			}
			DateView.setText(util.getPersianDate(person1.getDate()));
			txt3.setText(x.getName());
		}
		adapter.close();
		iconProile.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				Toast.makeText(context,
						"PapertitleListAdapter",
						Toast.LENGTH_SHORT).show();
				
			}
		});
		txt1.setText(person1.getTitle());
		txt2.setText(person1.getContext());

		String item = txt1.getText().toString();
		int ItemId = 0;
		for (Paper listItem : mylist) {
			if (item.equals(listItem.getTitle())) {
				paperNumber = ItemId = listItem.getId();
			}
		}

		adapter.open();

		if (currentUser == null) {
			likePaper.setBackgroundResource(R.drawable.like_froum_off);

		} else {
			if (adapter.isUserLikedPaper(currentUser.getId(), paperNumber)) {
				likePaper.setBackgroundResource(R.drawable.like_froum);
			} else
				likePaper.setBackgroundResource(R.drawable.like_froum_off);
		}

		NumofComment.setText(adapter.CommentInPaper_count(person1.getId())
				.toString());

		NumofLike
				.setText(adapter.LikeInPaper_count(person1.getId()).toString());
		adapter.close();

		final SharedPreferences abc = context.getSharedPreferences("Id", 0);
		likePaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(context,
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					String item = txt1.getText().toString();
					int ItemId = 0;
					for (Paper listItem : mylist) {
						if (item.equals(listItem.getTitle())) {
							// check authentication and authorization
							paperNumber = ItemId = listItem.getId();
						}
					}

				}
				date = new ServerDate(context);
				date.delegate = PapertitleListAdapter.this;
				date.execute("");

			}
		});
		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();

				for (Paper listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				PaperWithoutComment fragment = new PaperWithoutComment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				abc.edit()
						.putInt("Froum_List_Id",
								((ListView) parent).getFirstVisiblePosition())
						.commit();

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				adapter.open();
				adapter.SetSeen("Paper", id, "1");
				adapter.close();

			}

		});
		commentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();

				for (Paper listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				Toast.makeText(context, "send = " + id, Toast.LENGTH_SHORT)
						.show();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				DialogcmtInPaper dialog = new DialogcmtInPaper(null, context,
						R.layout.dialog_addcomment, id, 3);
				Bundle bundle2 = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
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
			if (adapter.isUserLikedPaper(currentUser.getId(), paperNumber)) {
				adapter.deleteLikeFromPaper(currentUser.getId(), paperNumber);
				likePaper.setBackgroundResource(R.drawable.like_froum_off);

				NumofLike.setText(adapter.LikeInPaper_count(paperNumber)
						.toString());

			} else {
				adapter.insertLikeInPaperToDb(currentUser.getId(), paperNumber,
						serverDate);
				likePaper.setBackgroundResource(R.drawable.like_froum);
				NumofLike.setText(adapter.LikeInPaper_count(paperNumber)
						.toString());

			}
			adapter.close();

		} catch (NumberFormatException e) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				adapter.open();
				if (adapter.isUserLikedPaper(currentUser.getId(), paperNumber)) {
					adapter.open();
					// int c = adapter.LikeInFroum_count(ItemId) - 1;
					// countLikeFroum.setText(String.valueOf(c));

					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(context);
					deleting.delegate = PapertitleListAdapter.this;

					params.put("TableName", "LikeInPaper");
					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("PaperId", String.valueOf(paperNumber));

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
					saving.delegate = PapertitleListAdapter.this;

					params.put("TableName", "LikeInPaper");

					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("PaperId", String.valueOf(paperNumber));
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
		} catch (Exception e) {

			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}
}
