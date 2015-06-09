package com.project.mechanic.adapter;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogcmtInfroum;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.utility.Utility;

public class FroumtitleListadapter extends ArrayAdapter<Froum> implements
		AsyncInterface {

	Context context;
	List<Froum> mylist;
	DataBaseAdapter adapter;
	Utility util;
	Users CurrentUser;
	PersianDate date;
	String currentDate;
	LinearLayout LikeTitle;
	int ItemId;
	TextView countLikeFroum;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

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
	public View getView(final int position, View convertView, ViewGroup parent) {

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

		date = new PersianDate();
		currentDate = date.todayShamsi();

		Froum person1 = mylist.get(position);

		adapter.open();
		Users x = adapter.getUserbyid(person1.getUserId());
		CurrentUser = util.getCurrentUser();

		txt1.setText(person1.getTitle());
		txt2.setText(person1.getDescription());
		txt3.setText(x.getName());
		countcommentfroum.setText(adapter.CommentInFroum_count(person1.getId())
				.toString());
		countLikeFroum.setText(adapter.LikeInFroum_count(person1.getId())
				.toString());
		dateTopic.setText(x.getDate());
		adapter.open();

		String item = txt1.getText().toString();
		ItemId = 0;
		for (Froum listItem : mylist) {
			if (item.equals(listItem.getTitle())) {
				// check authentication and authorization
				ItemId = listItem.getId();
			}
		}

		if (x.getImage() == null) {
			profileImg.setImageResource(R.drawable.no_img_profile);
		} else {

			byte[] byteImg = x.getImage();
			Bitmap bmp = BitmapFactory.decodeByteArray(byteImg, 0,
					byteImg.length);
			profileImg.setImageBitmap(bmp);

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
					if (adapter.isUserLikedFroum(CurrentUser.getId(), position)) {
						adapter.open();
						int c = adapter.LikeInFroum_count(position) - 1;
						countLikeFroum.setText(String.valueOf(c));

						params = new LinkedHashMap<String, String>();
						deleting = new Deleting(context);
						deleting.delegate = FroumtitleListadapter.this;

						params.put("TableName", "LikeInFroum");
						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("FroumId", String.valueOf(position));
						deleting.execute(params);

						adapter.close();
					} else {
						adapter.open();
						params = new LinkedHashMap<String, String>();
						saving = new Saving(context);
						saving.delegate = FroumtitleListadapter.this;

						params.put("TableName", "LikeInFroum");

						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("FroumId", String.valueOf(position));
						params.put("CommentId", "0");
						params.put("Date", currentDate);
						saving.execute(params);
						countLikeFroum.setText(adapter.LikeInFroum_count(
								position).toString());

						adapter.close();

					}

				}
				adapter.close();

			}

		});
		commenttitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();
				ItemId = 0;
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

				DialogcmtInfroum dialog = new DialogcmtInfroum(null, ItemId,
						context, -1, R.layout.dialog_addcomment);

				Bundle bundle2 = new Bundle();
				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}

		});
		return convertView;
	}

	@Override
	public void processFinish(String output) {
		int id = -1;
		try {
			id = Integer.valueOf(output);
		} catch (Exception ex) {
			Toast.makeText(context, "خطا در ارتباط با سرور", Toast.LENGTH_SHORT)
					.show();
		}
		adapter.open();

		if (adapter.isUserLikedFroum(id, ItemId)) {
			LikeTitle.setBackgroundResource(R.drawable.like_froum_off);
			adapter.deleteLikeFromFroum(id, ItemId);

		} else {
			LikeTitle.setBackgroundResource(R.drawable.like_froum);
			adapter.insertLikeInFroumToDb(id, ItemId, currentDate, 0);

			countLikeFroum
					.setText(adapter.LikeInFroum_count(ItemId).toString());
		}
		adapter.close();

	}
}
