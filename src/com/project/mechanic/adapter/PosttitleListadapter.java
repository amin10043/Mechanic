package com.project.mechanic.adapter;

import java.io.File;
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
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogLongClick;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.fragment.PostFragment;
import com.project.mechanic.fragment.PostWithoutComment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

@SuppressLint("SimpleDateFormat")
public class PosttitleListadapter extends ArrayAdapter<Post> implements
		AsyncInterface {

	Context context;
	List<Post> mylist;
	DataBaseAdapter adapter;
	Utility util;
	Users CurrentUser;
	// PersianDate todayDate;
	// String currentDate;
	LinearLayout LikeTitle;
	// int ItemId;
	int postNumber;
	TextView countLikePost;
	ProgressDialog ringProgressDialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	String serverDate = "";
	ServerDate date;
	int userId;
	Fragment fragment;
	ImageView report;
	View Parent;

	public PosttitleListadapter(Context context, int resource,
			List<Post> objects, Fragment fragment) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fragment = fragment;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView,
			final ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.raw_posttitle, parent, false);

		Parent = parent;
		final TextView txt1 = (TextView) convertView
				.findViewById(R.id.rowtitlepaper);
		final TextView txt2 = (TextView) convertView
				.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		TextView countcommentpost = (TextView) convertView
				.findViewById(R.id.countCommentInEveryTopic);
		TextView dateTopic = (TextView) convertView
				.findViewById(R.id.datetopicinFroum);
		countLikePost = (TextView) convertView
				.findViewById(R.id.countLikeInFroumTitle);
		ImageView profileImg = (ImageView) convertView
				.findViewById(R.id.iconfroumtitle);

		ImageView postImage = (ImageView) convertView
				.findViewById(R.id.postImage);

		LinearLayout commenttitle = (LinearLayout) convertView
				.findViewById(R.id.l1cm);
		LikeTitle = (LinearLayout) convertView
				.findViewById(R.id.liketitleTopic);

		report = (ImageView) convertView.findViewById(R.id.reportImage);

		Post person1 = mylist.get(position);

		txt1.setTypeface(util.SetFontCasablanca());
		txt2.setTypeface(util.SetFontCasablanca());

		adapter.open();
		Users x = adapter.getUserbyid(person1.getUserId());
		adapter.close();

		CurrentUser = util.getCurrentUser();

		if (person1.getSeenBefore() > 0) {
			txt1.setTextColor(Color.GRAY);
			txt2.setTextColor(Color.GRAY);
			txt3.setTextColor(Color.GRAY);
			dateTopic.setTextColor(Color.GRAY);

		}
		if (!person1.getTitle().isEmpty()) {
			txt1.setText(person1.getTitle());
			txt1.setVisibility(View.VISIBLE);
		}
		if (!person1.getDescription().isEmpty()) {
			txt2.setText(person1.getDescription());
			txt2.setVisibility(View.VISIBLE);
		}

		// txt2.setText(person1.getDescription());

		if (!person1.getPhoto().isEmpty()) {

			File imgFile = new File(person1.getPhoto());

			if (imgFile.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(person1.getPhoto());
				postImage.setImageBitmap(myBitmap);
				postImage.setVisibility(View.VISIBLE);
			}
		}

		adapter.open();

		if (x != null)
			txt3.setText(x.getName());
		countcommentpost.setText(adapter.CommentInPost_count(person1.getId())
				.toString());
		countLikePost.setText(adapter.LikeInPost_count(person1.getId())
				.toString());
		adapter.close();

		dateTopic.setText(util.getPersianDate(person1.getDate()));

		String item = txt2.getText().toString();

		int sizeDescription = item.length();
		String subItem;
		subItem = item.subSequence(0, sizeDescription - 4).toString();

		int ItemId = 0;
		for (Post listItem : mylist) {
			if (subItem.equals(listItem.getDescription())) {
				postNumber = ItemId = listItem.getId();
			}
		}
		adapter.open();

		countLikePost.setText(adapter.LikeInPost_count(ItemId).toString());

		if (CurrentUser == null) {
			LikeTitle.setBackgroundResource(R.drawable.like_froum_off);

		} else {
			if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
				LikeTitle.setBackgroundResource(R.drawable.like_froum);
			} else
				LikeTitle.setBackgroundResource(R.drawable.like_froum_off);
		}
		adapter.close();

		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.topicTitleFroum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		if (x != null) {

			if (x.getImagePath() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);
			} else {
				// byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeFile(x.getImagePath());
				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				profileImg.setLayoutParams(lp);
			}
		}
		// ////////////////////////////////////
		profileImg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				adapter.open();
				Post person1 = mylist.get(position);
				Users x = adapter.getUserbyid(person1.getUserId());
				userId = x.getId();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				adapter.close();
			}
		});

		LikeTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CurrentUser == null) {
					Toast.makeText(context,
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {

					LinearLayout parentlayout = (LinearLayout) v;
					LinearLayout parent = (LinearLayout) parentlayout
							.getParent().getParent();
					int id = ((Integer) parent.getTag());
					postNumber = id;
					date = new ServerDate(context);
					date.delegate = PosttitleListadapter.this;
					date.execute("");
					ringProgressDialog = ProgressDialog.show(context, "",
							"لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
				}
			}

		});
		final SharedPreferences abc = context.getSharedPreferences("Id", 0);
		final SharedPreferences postId = context.getSharedPreferences("Id", 0);

		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CurrentUser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
				} else {

					int ItemId = 0;
					String t = "";
					ListView listView = (ListView) v.getParent().getParent()
							.getParent().getParent();
					int position = listView.getPositionForView(v);
					Post f = getItem(position);
					if (f != null) {
						ItemId = f.getUserId();
						t = f.getDescription();
					}

					DialogLongClick dia = new DialogLongClick(context, 1,
							ItemId, f.getId(), fragment, t);
					Toast.makeText(context, ItemId + "", 0).show();

					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
					lp.copyFrom(dia.getWindow().getAttributes());
					lp.width = (int) (util.getScreenwidth() / 1.5);
					lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

					dia.show();

					dia.getWindow().setAttributes(lp);
					dia.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
				}
			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// در تمام صفحات از این کد ها استفاده شود
				int ItemId = 0;
				ListView listView = (ListView) v.getParent().getParent();
				int position = listView.getPositionForView(v);
				Post f = getItem(position);
				if (f != null) {
					ItemId = f.getId();
				}
				// تا اینجا

				adapter.open();
				adapter.SetSeen("Post", ItemId, "1");
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				PostWithoutComment fragment = new PostWithoutComment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 1).commit();
				abc.edit()
						.putInt("Post_List_Id",
								((ListView) parent).getFirstVisiblePosition())
						.commit();
				postId.edit().putInt("main_Id", ItemId).commit();

			}
		});
		commenttitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				LinearLayout parentlayout = (LinearLayout) v;

				String item = txt2.getText().toString();
				;
				int sizeDescription = item.length();
				String subItem;
				subItem = item.subSequence(0, sizeDescription - 4).toString();
				int ItemId = 0;
				for (Post listItem : mylist) {
					if (subItem.equals(listItem.getDescription())) {
						// check authentication and authorization
						ItemId = listItem.getId();
					}
				}

				// int ItemId = 0;
				// ListView listView = (ListView) v.getParent().getParent()
				// .getParent();
				// int position = listView.getPositionForView(v);
				// Post f = getItem(position);
				// if (f != null) {
				// ItemId = f.getId();
				// }

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				PostFragment fragment = new PostFragment();
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

		convertView.setTag(person1.getId());
		// Parent = convertView;
		return convertView;
	}

	@Override
	public void processFinish(String output) {

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		try {
			int id = Integer.valueOf(output);
			LinearLayout parentLayout = (LinearLayout) Parent
					.findViewWithTag(postNumber);
			LinearLayout likeTitle = (LinearLayout) parentLayout
					.findViewById(R.id.liketitleTopic);

			adapter.open();
			if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
				adapter.deleteLikeFromPost(CurrentUser.getId(), postNumber);

				likeTitle.setBackgroundResource(R.drawable.like_froum_off);

			} else {
				adapter.insertLikeInPostToDb(id, CurrentUser.getId(),
						postNumber, serverDate, 0);

				likeTitle.setBackgroundResource(R.drawable.like_froum);
			}

			TextView likeCountPost = (TextView) likeTitle
					.findViewById(R.id.countLikeInFroumTitle);
			likeCountPost.setText(adapter.LikeInPost_count(postNumber)
					.toString());

			adapter.close();
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

		} catch (NumberFormatException ex) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				adapter.open();
				if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(context);
					deleting.delegate = PosttitleListadapter.this;

					params.put("TableName", "LikeInPost");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("PostId", String.valueOf(postNumber));

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
				} else {
					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = PosttitleListadapter.this;

					params.put("TableName", "LikeInPost");

					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("PostId", String.valueOf(postNumber));
					params.put("CommentId", "0");
					params.put("Date", output);
					params.put("ModifyDate", output);
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
