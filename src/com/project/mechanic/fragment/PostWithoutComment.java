package com.project.mechanic.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.LikeInPost;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class PostWithoutComment extends Fragment implements AsyncInterface {
	TextView titletxt, descriptiontxt, dateTopic, countComment, countLike,
			nametxt;
	LinearLayout addComment, likeTopic;
	ImageButton sharebtn;
	ImageView profileImg;
	Post topics;
	DataBaseAdapter adapter;
	Users CurrentUser;
	Utility util;
	int IDcurrentUser;

	int IdGglobal;
	DialogcmtInpost dialog;
	DialogPersonLikedPost dia;
	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	ImageView postImage;

	ProgressDialog ringProgressDialog;
	String serverDate = "";
	ServerDate date;
	RelativeLayout count, commentcounter;
	PostFragment ff;
	int userId;
	int diss = 0;
	boolean LikeOrComment; // like == true & comment == false

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.post_without_comment, null);
		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		// date = new PersianDate();
		// currentDate = date.todayShamsi();

		postImage = (ImageView) view.findViewById(R.id.postImage);

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

		count = (RelativeLayout) view.findViewById(R.id.countLiketext);
		commentcounter = (RelativeLayout) view.findViewById(R.id.countComment);

		final SharedPreferences postId = getActivity().getSharedPreferences(
				"Id", 0);
		final int idPost = postId.getInt("main_Id", -1);
		IdGglobal = idPost;

		adapter.open();
		CurrentUser = util.getCurrentUser();

		if (CurrentUser == null) {
		} else
			IDcurrentUser = CurrentUser.getId();

		topics = adapter.getPostItembyid(idPost);
		Users u = adapter.getUserbyid(topics.getUserId());
		userId = u.getId();
		if (CurrentUser == null) {
			likeTopic.setBackgroundResource(R.drawable.like_off);
			count.setBackgroundResource(R.drawable.count_like_off);

		} else {

			if (adapter.isUserLikedPost(CurrentUser.getId(), idPost)) {
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

			if (u.getImagePath() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);

			} else {
				// byte[] bytepic = u.getImage();

				Bitmap bmp = BitmapFactory.decodeFile(u.getImagePath());

				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp,
						50));

				profileImg.setLayoutParams(lp);
			}
		}

		// titletxt.setTypeface(util.SetFontCasablanca());
		// descriptiontxt.setTypeface(util.SetFontCasablanca());
		profileImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

		if (!topics.getTitle().isEmpty()) {
			titletxt.setText(topics.getTitle());
			titletxt.setVisibility(View.VISIBLE);
		}
		if (!topics.getDescription().isEmpty()) {
			descriptiontxt.setText(topics.getDescription());
			descriptiontxt.setVisibility(View.VISIBLE);
		}
		if (!topics.getPhoto().isEmpty()) {
			File imgFile = new File(topics.getPhoto());

			if (imgFile.exists()) {
				Bitmap myBitmap = BitmapFactory.decodeFile(topics.getPhoto());
				postImage.setImageBitmap(myBitmap);
				postImage.setVisibility(View.VISIBLE);
			}
		}

		countComment.setText(adapter.CommentInPost_count(idPost).toString());
		countLike.setText(adapter.LikeInPost_count(idPost).toString());
		dateTopic.setText(util.getPersianDate(topics.getDate()));

		adapter.close();
		final SharedPreferences abc = getActivity().getSharedPreferences("Id",
				0);

		commentcounter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				PostFragment fragment = new PostFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(idPost));
				fragment.setArguments(bundle);

				/*
				 * bundle.putString("Id", String.valueOf(idPost));
				 * fragment.setArguments(bundle);
				 */

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 2).commit();

			}
		});
		count.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				ArrayList<LikeInPost> likedist = adapter
						.getLikepostLikeInPostByPostId(idPost);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
							.show();
				} else {

					DialogPersonLikedPost dia = new DialogPersonLikedPost(
							getActivity(), idPost, likedist);
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
					dialog = new DialogcmtInpost(PostWithoutComment.this, 0,
							getActivity(), idPost, R.layout.dialog_addcomment,
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
					if (getActivity() != null) {
						ringProgressDialog = ProgressDialog.show(getActivity(),
								"", "لطفا منتظر بمانید...", true);
						ringProgressDialog.setCancelable(false);
						date = new ServerDate(getActivity());
						date.delegate = PostWithoutComment.this;
						date.execute("");
						LikeOrComment = true;
					}
				}
				adapter.close();
			}

		});

		ImageView report = (ImageView) view.findViewById(R.id.reportImage);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0)
							.show();
				} else {

					DialogLongClick dia = new DialogLongClick(getActivity(), 1,
							topics.getUserId(), topics.getId(),
							PostWithoutComment.this, topics.getDescription());
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
					lp.copyFrom(dia.getWindow().getAttributes());
					lp.width = (int) (util.getScreenwidth() / 1.5);
					lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
					;
					dia.show();

					dia.getWindow().setAttributes(lp);
					dia.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
				}
			}
		});

		ImageView send = util.ShowFooterAgahi(getActivity(), true, 3);
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if ("".equals(util.inputComment(getActivity()))) {
					Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0)
							.show();
				} else {
					date = new ServerDate(getActivity());
					date.delegate = PostWithoutComment.this;
					date.execute("");
					LikeOrComment = false;

					util.ReplyLayout(getActivity(), "", false);

				}
			}
		});
		ImageView delete = util.deleteReply(getActivity());

		delete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				util.ReplyLayout(getActivity(), "", false);

			}
		});
		return view;
	}

	public void setcount() {
		adapter.open();
		countComment.setText(adapter.CommentInPost_count(IdGglobal).toString());
		adapter.close();
	}

	@Override
	public void processFinish(String output) {

		int id = -1;
		try {
			try {
				id = Integer.valueOf(output);
				adapter.open();
				if (LikeOrComment == true) {

					if (adapter.isUserLikedPost(CurrentUser.getId(), IdGglobal)) {
						adapter.deleteLikeFromPost(CurrentUser.getId(),
								IdGglobal);
						likeTopic.setBackgroundResource(R.drawable.like_off);
						count.setBackgroundResource(R.drawable.count_like_off);

						countLike.setText(adapter.LikeInPost_count(IdGglobal)
								.toString());
					} else {
						adapter.insertLikeInPostToDb(id, CurrentUser.getId(),
								IdGglobal, serverDate, 0);
						likeTopic.setBackgroundResource(R.drawable.like_on);
						count.setBackgroundResource(R.drawable.count_like);

						countLike.setText(adapter.LikeInPost_count(IdGglobal)
								.toString());
					}
				} else {

					adapter.open();

					adapter.insertCommentInPosttoDb(id,
							util.inputComment(getActivity()), IdGglobal,
							CurrentUser.getId(), serverDate, 0);

					adapter.close();
					util.ToEmptyComment(getActivity());

					FragmentTransaction trans = ((MainActivity) getActivity())
							.getSupportFragmentManager().beginTransaction();
					PostFragment fragment = new PostFragment();
					Bundle bundle = new Bundle();
					bundle.putString("Id", String.valueOf(IdGglobal));
					fragment.setArguments(bundle);
					trans.replace(R.id.content_frame, fragment);

					trans.commit();

					final SharedPreferences realizeIdComment = getActivity()
							.getSharedPreferences("Id", 0);
					realizeIdComment.edit().putInt("main_Id", 1371).commit();

				}

				adapter.close();
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}

			} catch (NumberFormatException ex) {
				if (output != null
						&& !(output.contains("Exception") || output
								.contains("java"))) {

					if (LikeOrComment == false) {

						if (getActivity() != null) {
							params = new LinkedHashMap<String, String>();

							saving = new Saving(getActivity());
							saving.delegate = PostWithoutComment.this;

							params.put("TableName", "CommentInPost");

							params.put("Desk", util.inputComment(getActivity()));
							params.put("PostId", String.valueOf(IdGglobal));
							params.put("UserId",
									String.valueOf(CurrentUser.getId()));
							params.put("CommentId", String.valueOf(0));

							params.put("Date", output);
							params.put("ModifyDate", output);
							params.put("IsUpdate", "0");
							params.put("Id", "0");

							serverDate = output;

							saving.execute(params);
						}
						ringProgressDialog = ProgressDialog.show(getActivity(),
								"", "لطفا منتظر بمانید...", true);

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

						adapter.open();

						if (adapter.isUserLikedPost(CurrentUser.getId(),
								IdGglobal)) {
							adapter.open();
							params = new LinkedHashMap<String, String>();

							if (getActivity() != null) {

								deleting = new Deleting(getActivity());
								deleting.delegate = PostWithoutComment.this;

								params.put("TableName", "LikeInPost");
								params.put("UserId",
										String.valueOf(CurrentUser.getId()));
								params.put("PostId", String.valueOf(IdGglobal));

								deleting.execute(params);
							}
							adapter.close();
						} else {
							adapter.open();
							params = new LinkedHashMap<String, String>();

							if (getActivity() != null) {
								saving = new Saving(getActivity());
								saving.delegate = PostWithoutComment.this;

								params.put("TableName", "LikeInPost");

								params.put("UserId",
										String.valueOf(CurrentUser.getId()));
								params.put("PostId", String.valueOf(IdGglobal));
								params.put("CommentId", "0");
								params.put("Date", output);
								params.put("ModifyDate", output);
								params.put("IsUpdate", "0");
								params.put("Id", "0");

								serverDate = output;

								saving.execute(params);
							}
							adapter.close();

						}
					}
					adapter.close();

				} else {
					Toast.makeText(getActivity(),
							"خطا در ثبت. پاسخ نا مشخص از سرور",
							Toast.LENGTH_SHORT).show();
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				}
			}

		} catch (Exception e) {

			Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT)
					.show();
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}
		}
	}
}
