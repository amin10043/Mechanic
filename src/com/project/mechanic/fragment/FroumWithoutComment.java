package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class FroumWithoutComment extends Fragment implements AsyncInterface {
	TextView titletxt, descriptiontxt, dateTopic, countComment, countLike,
			nametxt;
	LinearLayout addComment, likeTopic;
	ImageButton sharebtn;
	ImageView profileImg;
	Froum topics;
	DataBaseAdapter adapter;
	Users CurrentUser;
	Utility util;
	int IDcurrentUser;
	// String currentDate;
	// PersianDate date;
	int IdGglobal;
	DialogcmtInfroum dialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ProgressDialog ringProgressDialog;
	String serverDate = "";
	ServerDate date;
	RelativeLayout count, commentcounter;
	FroumFragment ff;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.froum_without_comment, null);
		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		// date = new PersianDate();
		// currentDate = date.todayShamsi();

		titletxt = (TextView) view.findViewById(R.id.title_topic);
		descriptiontxt = (TextView) view.findViewById(R.id.description_topic);
		dateTopic = (TextView) view.findViewById(R.id.date_cc);
		countComment = (TextView) view.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) view.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) view.findViewById(R.id.name_cc);

		addComment = (LinearLayout) view.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) view.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) view.findViewById(R.id.sharefroumicon);
		profileImg = (ImageView) view.findViewById(R.id.iconfroumtitle);

		count = (RelativeLayout) view.findViewById(R.id.countLike);
		commentcounter = (RelativeLayout) view.findViewById(R.id.countComment);

		final SharedPreferences froumId = getActivity().getSharedPreferences(
				"Id", 0);
		final int idFroum = froumId.getInt("main_Id", -1);
		IdGglobal = idFroum;

		adapter.open();
		CurrentUser = util.getCurrentUser();

		if (CurrentUser == null) {
		} else
			IDcurrentUser = CurrentUser.getId();

		topics = adapter.getFroumItembyid(idFroum);
		Users u = adapter.getUserbyid(topics.getUserId());

		if (CurrentUser == null) {
			likeTopic.setBackgroundResource(R.drawable.like_off);
			count.setBackgroundResource(R.drawable.count_like_off);

		} else {

			if (adapter.isUserLikedFroum(CurrentUser.getId(), idFroum)) {
				likeTopic.setBackgroundResource(R.drawable.like_on);
				count.setBackgroundResource(R.drawable.count_like);

			} else {

				likeTopic.setBackgroundResource(R.drawable.like_off);
				count.setBackgroundResource(R.drawable.count_like_off);

			}
		}

		if (u != null) {

			nametxt.setText(u.getName());
			LinearLayout rl = (LinearLayout) view
					.findViewById(R.id.profileLinearcommenterinContinue);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					rl.getLayoutParams());

			lp.width = util.getScreenwidth() / 7;
			lp.height = util.getScreenwidth() / 7;
			profileImg.setLayoutParams(lp);

			if (u.getImage() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);

			} else {
				byte[] bytepic = u.getImage();

				Bitmap bmp = BitmapFactory.decodeByteArray(bytepic, 0,
						bytepic.length);

				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				profileImg.setLayoutParams(lp);
			}
		}

		titletxt.setText(topics.getTitle());
		descriptiontxt.setText(topics.getDescription());
		countComment.setText(adapter.CommentInFroum_count(idFroum).toString());
		countLike.setText(adapter.LikeInFroum_count(idFroum).toString());
		dateTopic.setText(util.getPersianDate(topics.getDate()));

		adapter.close();
		final SharedPreferences abc = getActivity().getSharedPreferences("Id",
				0);

		commentcounter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				FroumFragment fragment = new FroumFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(idFroum));
				fragment.setArguments(bundle);

				bundle.putString("Id", String.valueOf(idFroum));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 2).commit();

			}
		});
		count.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				ArrayList<LikeInFroum> likedist = adapter
						.getLikefroumLikeInFroumByFroumId(idFroum);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
							.show();
				} else {
					DialogPersonLiked dia = new DialogPersonLiked(
							getActivity(), idFroum, likedist);
					dia.show();
				}
			}
		});

		addComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج کامنت ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();

				} else {
					dialog = new DialogcmtInfroum(FroumWithoutComment.this, 0,
							getActivity(), idFroum, R.layout.dialog_addcomment,
							1);
					dialog.show();

					// FragmentTransaction trans = ((MainActivity)
					// getActivity())
					// .getSupportFragmentManager().beginTransaction();
					// FroumFragment fragment = new FroumFragment();
					// trans.setCustomAnimations(R.anim.pull_in_left,
					// R.anim.push_out_right);
					// Bundle b = new Bundle();
					// b.putString("Id", String.valueOf(idFroum));
					// fragment.setArguments(b);
					//
					// trans.replace(R.id.content_frame, fragment);
					// trans.commit();
					// ff = new FroumFragment();
					// ff.updateList();
				}
			}
		});
		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(
						android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				// String shareBody = x.getDescription();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,
						topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						topics.getDescription());
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(),
							"برای درج لایک ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				} else {
					date = new ServerDate(getActivity());
					date.delegate = FroumWithoutComment.this;
					date.execute("");

				}
				adapter.close();

			}

		});

		return view;
	}

	public void setcount() {
		adapter.open();
		countComment
				.setText(adapter.CommentInFroum_count(IdGglobal).toString());
		adapter.close();
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
			if (adapter.isUserLikedFroum(CurrentUser.getId(), IdGglobal)) {
				adapter.deleteLikeFromFroum(CurrentUser.getId(), IdGglobal);
				likeTopic.setBackgroundResource(R.drawable.like_off);
				count.setBackgroundResource(R.drawable.count_like_off);

				countLike.setText(adapter.LikeInFroum_count(IdGglobal)
						.toString());

			} else {
				adapter.insertLikeInFroumToDb(CurrentUser.getId(), IdGglobal,
						serverDate, 0);
				likeTopic.setBackgroundResource(R.drawable.like_on);
				count.setBackgroundResource(R.drawable.count_like);

				countLike.setText(adapter.LikeInFroum_count(IdGglobal)
						.toString());
			}
			adapter.close();

		} catch (NumberFormatException ex) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				adapter.open();

				if (adapter.isUserLikedFroum(CurrentUser.getId(), IdGglobal)) {
					adapter.open();
					// int c = adapter.LikeInFroum_count(ItemId) - 1;
					// countLikeFroum.setText(String.valueOf(c));

					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(getActivity());
					deleting.delegate = FroumWithoutComment.this;

					params.put("TableName", "LikeInFroum");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(IdGglobal));

					deleting.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
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
					saving = new Saving(getActivity());
					saving.delegate = FroumWithoutComment.this;

					params.put("TableName", "LikeInFroum");

					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(IdGglobal));
					params.put("CommentId", "0");
					params.put("Date", output);
					params.put("IsUpdate", "0");
					params.put("Id", "0");

					serverDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(), "",
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
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
			}
		}

		catch (Exception e) {

			Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT)
					.show();
		}
	}

}