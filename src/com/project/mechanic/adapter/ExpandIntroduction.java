package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogLongClick;
import com.project.mechanic.fragment.DialogcmtInobject;
import com.project.mechanic.fragment.DisplayPersonalInformationFragment;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.utility.Utility;

public class ExpandIntroduction extends BaseExpandableListAdapter implements
		AsyncInterface {

	Context context;
	private Map<CommentInObject, List<CommentInObject>> mapCollection;
	private ArrayList<CommentInObject> CommentList;
	DataBaseAdapter adapter;
	Utility util;
	IntroductionFragment f;
	int ObjectID, userid;
	Users Currentuser;
	ProgressDialog ringProgressDialog;
	boolean flag;

	int GlobalId;
	int userId;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	public ExpandIntroduction(Context context,
			ArrayList<CommentInObject> CommentList,
			Map<CommentInObject, List<CommentInObject>> mapCollection,
			IntroductionFragment introductionFragment, int ObjectId) {

		this.context = context;
		this.mapCollection = mapCollection;
		this.CommentList = CommentList;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.f = introductionFragment;
		this.ObjectID = ObjectId;
		adapter.open();
		Currentuser = util.getCurrentUser();
		adapter.close();
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return mapCollection.get(CommentList.get(groupPosition)).get(
				childPosition);

	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		CommentInObject reply = (CommentInObject) getChild(groupPosition,
				childPosition);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_child_item, null);
		}
		TextView mainReply = (TextView) convertView
				.findViewById(R.id.reply_txt_child);

		TextView dateReply = (TextView) convertView
				.findViewById(R.id.date_replyed);
		TextView nameReplyer = (TextView) convertView
				.findViewById(R.id.name_replyed);

		ImageButton ReplyerPic = (ImageButton) convertView
				.findViewById(R.id.icon_reply_comment);
		adapter.open();

		final CommentInObject comment = CommentList.get(groupPosition);
		Users y = adapter.getUserbyid(comment.getUserid());

		if (y.getImage() == null) {
			ReplyerPic.setImageResource(R.drawable.no_img_profile);
		} else {

			byte[] byteImageProfile = y.getImage();

			Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
					byteImageProfile.length);

			ReplyerPic.setImageBitmap(bmp);

			RelativeLayout rl = (RelativeLayout) convertView
					.findViewById(R.id.main_icon_reply);
			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
					rl.getLayoutParams());

			lp.width = util.getScreenwidth() / 7;
			lp.height = util.getScreenwidth() / 7;
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			lp.setMargins(5, 5, 5, 5);
			ReplyerPic.setLayoutParams(lp);
		}

		convertView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {
				int i = 0;
				int u = 0;
				// برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
				// شود

				int d = (int) getGroupId(groupPosition);
				CommentInObject w = (CommentInObject) getChild(d, childPosition);
				if (w != null) {
					i = w.getId();
					u = w.getUserid();
				}
				Toast.makeText(context, "id = " + i + "Userid = " + u, 0)
						.show();
				
				DialogLongClick dia = new DialogLongClick(context, 7, u, i,
						f);
				dia.show();
				return true;
			}
		});
		ReplyerPic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				CommentInObject comment = CommentList.get(groupPosition);
				Users y = adapter.getUserbyid(comment.getUserid());
				userId = y.getId();
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				DisplayPersonalInformationFragment fragment = new DisplayPersonalInformationFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		mainReply.setText(reply.getDescription());
		dateReply.setText(util.getPersianDate(reply.getDatetime()));
		nameReplyer.setText(y.getName());

		adapter.close();

		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		if (mapCollection.get(CommentList.get(groupPosition)) != null)
			return mapCollection.get(CommentList.get(groupPosition)).size();
		return 0;
	}

	@Override
	public Object getGroup(int groupPosition) {
		return CommentList.get(groupPosition);

	}

	@Override
	public int getGroupCount() {
		return CommentList.size();

	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;

	}

	@Override
	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {
		adapter.open();

		final CommentInObject comment = CommentList.get(groupPosition);
		Users x = adapter.getUserbyid(comment.getUserid());

		adapter.close();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_group_item, null);
		}

		// start find view
		final TextView mainComment = (TextView) convertView
				.findViewById(R.id.peygham);

		TextView nameCommenter = (TextView) convertView
				.findViewById(R.id.name_froum_profile);

		final TextView countLike = (TextView) convertView
				.findViewById(R.id.countCommentFroum);
		final TextView countdisLike = (TextView) convertView
				.findViewById(R.id.countdislikecommentFroum);

		TextView dateCommenter = (TextView) convertView
				.findViewById(R.id.date_commented_in_froum);

		TextView countOfReply = (TextView) convertView
				.findViewById(R.id.numberOfCommentTopic);

		LinearLayout addreply = (LinearLayout) convertView
				.findViewById(R.id.addCommentToTopic);

		ImageButton profileImage = (ImageButton) convertView
				.findViewById(R.id.icon_froum_profile);

		final ImageButton imglikeComment = (ImageButton) convertView
				.findViewById(R.id.positive_img);

		final ImageButton imgdislikeComment = (ImageButton) convertView
				.findViewById(R.id.negative_img);

		final ExpandableListView mExpandableListView = (ExpandableListView) parent;

		// end find view

		// start set variable
		adapter.open();
		Currentuser = util.getCurrentUser();

		if (Currentuser == null) {
			imglikeComment.setImageResource((R.drawable.positive_off));
			imgdislikeComment.setImageResource((R.drawable.negative_off));
		} else {
			if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(),
					comment.getId(), 1)) {
				imglikeComment.setImageResource((R.drawable.positive));
			} else {
				imglikeComment.setImageResource((R.drawable.positive_off));

			}
			if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(),
					comment.getId(), 0)) {
				imgdislikeComment.setImageResource((R.drawable.negative));
			} else {
				imgdislikeComment.setImageResource((R.drawable.negative_off));

			}

		}
		profileImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				CommentInObject comment = CommentList.get(groupPosition);
				Users x = adapter.getUserbyid(comment.getUserid());
				userId = x.getId();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				DisplayPersonalInformationFragment fragment = new DisplayPersonalInformationFragment();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		mainComment.setText(comment.getDescription());
		nameCommenter.setText(x.getName());
		dateCommenter.setText(util.getPersianDate(comment.getDatetime()));

		countOfReply.setText(adapter.getCountOfReplyBrandPage(ObjectID,
				comment.getId()).toString());

		int com = 0;
		for (CommentInObject listItem : CommentList) {
			if (mainComment.getText().toString()
					.equals(listItem.getDescription())) {

				com = listItem.getId();

			}
		}
		countdisLike.setText(String.valueOf(adapter
				.NumberOfLikeOrDisLikeBrandPage(com, 0)));
		countLike.setText(String.valueOf(adapter
				.NumberOfLikeOrDisLikeBrandPage(com, 1)));
		// if (adapter.getCountOfReplyInObject(ObjectID, comment.getId()) == 0)
		// {
		// LinearLayout lrr = (LinearLayout) convertView
		// .findViewById(R.id.linearShowcountofRepply);
		// lrr.setVisibility(View.GONE);
		//
		// } else
		// countOfReply.setText(adapter.getCountOfReplyInObject(ObjectID,
		// comment.getId()).toString());

		// countLike.setText(comment.getId());
		// countdisLike.setText(comment.getFroumid());

		// start... this code for set image of profile
		adapter.open();
		if (x.getImage() == null) {
			profileImage.setImageResource(R.drawable.no_img_profile);
		} else {

			byte[] byteImageProfile = x.getImage();

			Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
					byteImageProfile.length);

			profileImage.setImageBitmap(bmp);
		}
		adapter.close();
		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.icon_header_comment_froum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		profileImage.setLayoutParams(lp);

		// end... this code for set image of profile

		adapter.close();

		// end set variable

		imgdislikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// khandan meghdar ghabli tedad like ha
				// tabdil be int
				// ezafe kardan 1 vahed be meghdar ghabli
				// tabdil be String
				adapter.open();

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					flag = false;

					RelativeLayout parentlayout = (RelativeLayout) v
							.getParent().getParent();
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumlike = parentlayout
							.findViewById(R.id.countCommentFroum);
					TextView txtlike = (TextView) viewnumlike;

					int CommentId = 0;

					for (CommentInObject listItem : CommentList) {
						if (txtMaincmt.getText().toString()
								.equals(listItem.getDescription())) {

							GlobalId = CommentId = listItem.getId();

						}
					}

					// send to database

					if (adapter.isUserLikedCommentBrandPage(
							Currentuser.getId(), CommentId, 0)) {

						/*
						 * start >>>>> delete dislike from server
						 */

						params = new LinkedHashMap<String, String>();
						deleting = new Deleting(context);
						deleting.delegate = ExpandIntroduction.this;

						params.put("TableName", "LikeInCommentObject");

						params.put("UserId",
								String.valueOf(Currentuser.getId()));

						params.put("IsLike", String.valueOf(0));
						params.put("CommentId", String.valueOf(CommentId));

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

						/*
						 * end >>>>> delete dislike from server
						 */

						// countdisLike.setText(String.valueOf(adapter
						// .NumberOfLikeOrDisLikeBrandPage(CommentId, 0)));
						//
						// imgdislikeComment
						// .setImageResource(R.drawable.negative_off);

					} else {

						if (adapter.isUserLikedCommentBrandPage(
								Currentuser.getId(), CommentId, 1))
							Toast.makeText(
									context,
									"شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						else {

							params = new LinkedHashMap<String, String>();

							saving = new Saving(context);
							saving.delegate = ExpandIntroduction.this;

							params.put("TableName", "LikeInCommentObject");

							params.put("UserId",
									String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(0));
							params.put("CommentId", String.valueOf(CommentId));

							params.put("IsUpdate", "0");
							params.put("Id", "0");

							saving.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
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

							/*
							 * end >>>>> save dislike to server
							 */

							notifyDataSetChanged();

						}

						// adapter.InsertLikeCommentFromObject(
						// Currentuser.getId(), 0, CommentId);
						// countdisLike.setText(String.valueOf(adapter
						// .NumberOfLikeOrDisLikeBrandPage(CommentId,
						// 0)));
						// imgdislikeComment
						// .setImageResource(R.drawable.negative);
						// Toast.makeText(context, "دیس لایک اضافه شد", 0)
						// .show();

						// }
					}
				}
			}
		});
		imglikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// khandan meghdar ghabli tedad like ha
				// tabdil be int
				// ezafe kardan 1 vahed be meghdar ghabli
				// tabdil be String
				adapter.open();
				flag = true;

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					RelativeLayout parentlayout = (RelativeLayout) v
							.getParent().getParent();
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumlike = parentlayout
							.findViewById(R.id.countCommentFroum);
					TextView txtlike = (TextView) viewnumlike;

					int CommentId = 0;

					for (CommentInObject listItem : CommentList) {
						if (txtMaincmt.getText().toString()
								.equals(listItem.getDescription())) {

							GlobalId = CommentId = listItem.getId();

						}
					}

					// send to database

					if (adapter.isUserLikedCommentBrandPage(
							Currentuser.getId(), CommentId, 1)) {

						params = new LinkedHashMap<String, String>();
						deleting = new Deleting(context);
						deleting.delegate = ExpandIntroduction.this;

						params.put("TableName", "LikeInCommentObject");

						params.put("UserId",
								String.valueOf(Currentuser.getId()));

						params.put("IsLike", String.valueOf(1));
						params.put("CommentId", String.valueOf(CommentId));

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

						// adapter.deleteLikeCommentBrandPage(CommentId,
						// Currentuser.getId(), 1);
						// countLike.setText(String.valueOf(adapter
						// .NumberOfLikeOrDisLikeBrandPage(CommentId, 1)));
						//
						// imglikeComment
						// .setImageResource(R.drawable.positive_off);
						// Toast.makeText(context, "لایک پاک شد", 0).show();

					} else {
						if (adapter.isUserLikedCommentBrandPage(
								Currentuser.getId(), CommentId, 0))
							Toast.makeText(
									context,
									"شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						else {

							params = new LinkedHashMap<String, String>();

							saving = new Saving(context);
							saving.delegate = ExpandIntroduction.this;

							params.put("TableName", "LikeInCommentObject");

							params.put("UserId",
									String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(1));
							params.put("CommentId", String.valueOf(CommentId));

							params.put("IsUpdate", "0");
							params.put("Id", "0");

							saving.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
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

							// adapter.InsertLikeCommentFromObject(id,
							// Currentuser.getId(), 1, CommentId);
							// countLike.setText(String.valueOf(adapter
							// .NumberOfLikeOrDisLikeBrandPage(CommentId,
							// 1)));
							// imglikeComment
							// .setImageResource(R.drawable.positive);
							// Toast.makeText(context, "لایک اضافه شد",
							// 0).show();
						}

					}

				}
			}
		});

		addreply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View m) {

				adapter.open();

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					RelativeLayout parentlayout = (RelativeLayout) m
							.getParent().getParent();
					View view = parentlayout.findViewById(R.id.peygham);
					TextView x = (TextView) view;
					String item = x.getText().toString();
					int commentid = 0;
					// String dd = comment.getDescription();
					for (CommentInObject test : CommentList) {
						if (item.equals(test.getDescription())) {
							commentid = test.getId();
							break;
						}
					}

					DialogcmtInobject dialog = new DialogcmtInobject(f,
							context, R.layout.dialog_addcomment, ObjectID,
							commentid);
					dialog.show();

					adapter.close();
				}
			}
		});

		convertView.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View v) {

				int u = 0;
				int i = 0;

				adapter.open();

				if (adapter.getCountOfReplyBrandPage(ObjectID, comment.getId()) > 0) {
					i = -1;
					int dd = (int) getGroupId(groupPosition);
					CommentInObject ww = (CommentInObject) getGroup(dd);
					if (ww != null) {
						u = ww.getUserid();
					}
					adapter.close();

					DialogLongClick dia = new DialogLongClick(context, 7, u, i,
							f);
					dia.show();

				} else {

					// برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
					// شود

					int d = (int) getGroupId(groupPosition);
					CommentInObject w = (CommentInObject) getGroup(d);
					if (w != null) {
						i = w.getId();
						u = w.getUserid();
					}

					// //////////////////////////

					DialogLongClick dia = new DialogLongClick(context, 7, u, i,
							f);
					dia.show();

				}

				return true;
			}
		});

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (isExpanded) {
					mExpandableListView.collapseGroup(groupPosition);
					notifyDataSetChanged();

				} else
					mExpandableListView.expandGroup(groupPosition);
				notifyDataSetChanged();

			}
		});
		mainComment.setTypeface(null, Typeface.BOLD);

		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		return true;
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public void processFinish(String output) {

		if (!"".equals(output) && output != null
				&& !(output.contains("Exception") || output.contains("java"))) {
			int id = -1;
			try {
				id = Integer.valueOf(output);

				adapter.open();
				if (flag) {

					if (adapter.isUserLikedCommentBrandPage(
							Currentuser.getId(), GlobalId, 1)) {

						adapter.deleteLikeCommentBrandPage(GlobalId,
								Currentuser.getId(), 1);
						notifyDataSetChanged();
						if (ringProgressDialog != null) {
							ringProgressDialog.dismiss();

						}
					} else {

						adapter.InsertLikeCommentFromObject(id,
								Currentuser.getId(), 1, GlobalId);
						notifyDataSetChanged();
						if (ringProgressDialog != null) {
							ringProgressDialog.dismiss();

						}

					}

				} else {
					if (adapter.isUserLikedCommentBrandPage(
							Currentuser.getId(), GlobalId, 0)) {

						adapter.deleteLikeCommentBrandPage(GlobalId,
								Currentuser.getId(), 0);
						notifyDataSetChanged();
						if (ringProgressDialog != null) {
							ringProgressDialog.dismiss();

						}
					} else {
						adapter.InsertLikeCommentFromObject(id,
								Currentuser.getId(), 0, GlobalId);
						notifyDataSetChanged();
						if (ringProgressDialog != null) {
							ringProgressDialog.dismiss();

						}
					}

				}
			} catch (Exception e) {
				Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT)
						.show();
			}
		}
	}

}
