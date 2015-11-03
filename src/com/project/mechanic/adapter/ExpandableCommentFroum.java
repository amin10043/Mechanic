package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogLongClick;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class ExpandableCommentFroum extends BaseExpandableListAdapter implements
		AsyncInterface/* , CommInterface */{

	Context context;
	private Map<CommentInFroum, List<CommentInFroum>> mapCollection;
	private ArrayList<CommentInFroum> cmt;
	DataBaseAdapter adapter;
	Utility util;
	FroumFragment f;
	int froumID, userid, GlobalId, userId, iid;
	Users Currentuser, uu;;
	boolean flag;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	TextView countLike, countdisLike;
	ProgressDialog ringProgressDialog;
	ImageView reportComment, reportReply;
	String serverDate = "";
	ServerDate date;
	CommentInFroum reply;
	CommentInFroum comment;
	List<String> menuItems;
	int itemId, userIdsender;;
	String description;
	boolean IsDeleteing;

	public ExpandableCommentFroum(Context context,
			ArrayList<CommentInFroum> laptops,
			Map<CommentInFroum, List<CommentInFroum>> mapCollection,
			FroumFragment f, int froumID) {
		this.context = context;
		this.mapCollection = mapCollection;
		this.cmt = laptops;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.f = f;
		this.froumID = froumID;
		adapter.open();
		Currentuser = util.getCurrentUser();
		adapter.close();

	}

	public Object getChild(int groupPosition, int childPosition) {
		return mapCollection.get(cmt.get(groupPosition)).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		reply = (CommentInFroum) getChild(groupPosition, childPosition);
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

		reportReply = (ImageView) convertView
				.findViewById(R.id.reportImagereply);
		adapter.open();

		// final CommentInFroum comment = cmt.get(groupPosition);
		Users y = adapter.getUserbyid(reply.getUserid());
		userId = y.getId();
		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		ReplyerPic.setLayoutParams(lp);

		if (y.getImagePath() == null) {
			ReplyerPic.setImageResource(R.drawable.no_img_profile);
			ReplyerPic.setLayoutParams(lp);

		} else {

			// byte[] byteImageProfile = y.getImage();

			Bitmap bmp = BitmapFactory.decodeFile(y.getImagePath());

			// Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
			// byteImageProfile.length);

			ReplyerPic.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));

			ReplyerPic.setLayoutParams(lp);

		}

		reportReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int d = (int) getGroupId(groupPosition);
				final CommentInFroum w = (CommentInFroum) getChild(d,
						childPosition);
				if (w != null) {
					itemId = w.getId();
					userIdsender = w.getUserid();
					description = w.getDesk();

				}

				if (util.getCurrentUser() != null) {

					if (util.getCurrentUser().getId() == userIdsender) {

						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("حذف");

					} else {
						menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("گزارش تخلف");
					}

				} else {
					menuItems = new ArrayList<String>();

					menuItems.clear();
					menuItems.add("کپی");
				}

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);

				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(description);

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (util.getCurrentUser() != null)
								util.reportAbuse(userIdsender, 5, itemId,
										description, w.getFroumid());
							else
								Toast.makeText(context, "ابتدا باید وارد شوید",
										0).show();
						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null
									&& util.getCurrentUser().getId() == userIdsender)
								deleteItems(itemId);
							else {

								Toast.makeText(context, "", 0).show();
							}
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);
				// /////////////////////////
				// int i = 0;
				// int u = 0;
				// String t = "";
				// // برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
				// // شود
				//
				// int d = (int) getGroupId(groupPosition);
				// CommentInFroum w = (CommentInFroum) getChild(d,
				// childPosition);
				// if (w != null) {
				// i = w.getId();
				// u = w.getUserid();
				// t = w.getDesk();
				// }
				// Toast.makeText(context, "id = " + i + "Userid = " + u, 0)
				// .show();
				// // //////////////////////////
				//
				// DialogLongClick dia = new DialogLongClick(context, 5, u, i,
				// f,
				// t);
				//
				// WindowManager.LayoutParams lp = new
				// WindowManager.LayoutParams();
				// lp.copyFrom(dia.getWindow().getAttributes());
				// lp.width = (int) (util.getScreenwidth() / 1.5);
				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				// ;
				// dia.show();
				//
				// dia.getWindow().setAttributes(lp);
				// dia.getWindow().setBackgroundDrawable(
				// new ColorDrawable(android.graphics.Color.TRANSPARENT));

			}
		});

		ReplyerPic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		mainReply.setText(reply.getDesk());
		dateReply.setText(util.getPersianDate(reply.getDatetime()));
		nameReplyer.setText(y.getName());
		adapter.close();

		notifyDataSetChanged();
		return convertView;
	}

	public int getChildrenCount(int groupPosition) {
		if (mapCollection.get(cmt.get(groupPosition)) != null)
			return mapCollection.get(cmt.get(groupPosition)).size();
		return 0;
	}

	public Object getGroup(int groupPosition) {
		return cmt.get(groupPosition);
	}

	public int getGroupCount() {
		return cmt.size();
	}

	public long getGroupId(int groupPosition) {
		return groupPosition;
	}

	public View getGroupView(final int groupPosition, final boolean isExpanded,
			View convertView, ViewGroup parent) {

		adapter.open();
		if (groupPosition <= cmt.size())
			comment = cmt.get(groupPosition);
		final Users x = adapter.getUserbyid(comment.getUserid());
		// userId= x.getId();
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

		countLike = (TextView) convertView.findViewById(R.id.countCommentFroum);
		countdisLike = (TextView) convertView
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

		reportComment = (ImageView) convertView.findViewById(R.id.reportImage);

		// end find view

		// start set variable
		adapter.open();
		Currentuser = util.getCurrentUser();

		if (Currentuser == null) {
			imglikeComment.setImageResource((R.drawable.positive_off));
			imgdislikeComment.setImageResource((R.drawable.negative_off));
		} else {
			if (adapter.isUserLikedComment(Currentuser.getId(),
					comment.getId(), 1)) {
				imglikeComment.setImageResource((R.drawable.positive));
			} else {
				imglikeComment.setImageResource((R.drawable.positive_off));

			}
			if (adapter.isUserLikedComment(Currentuser.getId(),
					comment.getId(), 0)) {
				imgdislikeComment.setImageResource((R.drawable.negative));
			} else {
				imgdislikeComment.setImageResource((R.drawable.negative_off));

			}

		}

		mainComment.setText(comment.getDesk());
		dateCommenter.setText(util.getPersianDate(comment.getDatetime()));
		// if (adapter.getCountOfReplyInFroum(froumID, comment.getId()) == 0) {
		// LinearLayout lrr = (LinearLayout) convertView
		// .findViewById(R.id.linearShowcountofRepply);
		// lrr.setVisibility(View.GONE);
		//
		// } else
		countOfReply.setText(adapter.getCountOfReplyInFroum(froumID,
				comment.getId()).toString());

		if (x != null) {
			Bitmap bmp = null;
			nameCommenter.setText(x.getName());
			if (x.getImagePath() == null) {
				profileImage.setImageResource(R.drawable.no_img_profile);
			} else {

				// byte[] byteImageProfile = x.getImage();
				if (x.getImagePath() != null)
					bmp = BitmapFactory.decodeFile(x.getImagePath());
				if (bmp != null)

					profileImage.setImageBitmap(/*
												 * Utility.getRoundedCornerBitmap
												 * (
												 */bmp/* , 50) */);
			}
		}
		RelativeLayout rl = (RelativeLayout) convertView
				.findViewById(R.id.icon_header_comment_froum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = util.getScreenwidth() / 7;
		lp.height = util.getScreenwidth() / 7;
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		profileImage.setLayoutParams(lp);
		profileImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				final CommentInFroum comment = cmt.get(groupPosition);
				final Users x = adapter.getUserbyid(comment.getUserid());
				userId = x.getId();

				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

		// end... this code for set image of profile
		int c = 0;

		for (CommentInFroum listItem : cmt) {
			if (mainComment.getText().toString().equals(listItem.getDesk())) {

				c = listItem.getId();

			}
		}
		countLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeFroum(c,
				1)));
		countdisLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeFroum(
				c, 0)));

		adapter.close();

		// end set variable

		notifyDataSetChanged();

		imgdislikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View t) {

				adapter.open();
				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {

					flag = false;
					RelativeLayout parentlayout = (RelativeLayout) t
							.getParent().getParent();
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumDislike = parentlayout
							.findViewById(R.id.countdislikecommentFroum);
					TextView txtdislike = (TextView) viewnumDislike;

					int id = 0;

					for (CommentInFroum listItem : cmt) {
						if (txtMaincmt.getText().toString()
								.equals(listItem.getDesk())) {

							GlobalId = id = listItem.getId();

						}
					}

					// send to database

					if (adapter.isUserLikedComment(Currentuser.getId(), id, 0)) {

						/*
						 * start >>>>> delete dislike from server
						 */

						params = new LinkedHashMap<String, String>();
						if (context != null) {

							deleting = new Deleting(context);
							deleting.delegate = ExpandableCommentFroum.this;

							params.put("TableName", "LikeInComment");

							params.put("UserId",
									String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(0));
							params.put("CommentId", String.valueOf(id));

							deleting.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
									"", "لطفا منتظر بمانید...", true);
						}
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

					} else {
						if (adapter.isUserLikedComment(Currentuser.getId(), id,
								1)) {
							Toast.makeText(
									context,
									"شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						} else {

							/*
							 * start >>>>> save dislike to server
							 */
							if (context != null) {

								date = new ServerDate(context);
								date.delegate = ExpandableCommentFroum.this;
								date.execute("");
							}
							/*
							 * end >>>>> save dislike to server
							 */

						}
						adapter.close();
					}
				}
			}
		});

		imglikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					flag = true;
					adapter.open();

					// // peyda kardan id comment sabt shode

					RelativeLayout parentlayout = (RelativeLayout) v
							.getParent().getParent();
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumlike = parentlayout
							.findViewById(R.id.countCommentFroum);
					TextView txtlike = (TextView) viewnumlike;

					int cmtId = 0;

					for (CommentInFroum listItem : cmt) {
						if (txtMaincmt.getText().toString()
								.equals(listItem.getDesk())) {

							GlobalId = cmtId = listItem.getId();
						}
					}

					// send to database

					if (adapter.isUserLikedComment(Currentuser.getId(), cmtId,
							1)) {
						/*
						 * start >>>>> delete like from server
						 */

						params = new LinkedHashMap<String, String>();

						if (context != null) {

							deleting = new Deleting(context);
							deleting.delegate = ExpandableCommentFroum.this;

							params.put("TableName", "LikeInComment");

							params.put("UserId",
									String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(1));
							params.put("CommentId", String.valueOf(cmtId));

							deleting.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
									"", "لطفا منتظر بمانید...", true);
						}
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
						 * end >>> delete like from server
						 */

					} else {

						if (adapter.isUserLikedComment(Currentuser.getId(),
								cmtId, 0)) {
							Toast.makeText(
									context,
									"شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						} else {

							/*
							 * start : save like to server
							 */

							// ///////////

							date = new ServerDate(context);
							date.delegate = ExpandableCommentFroum.this;
							date.execute("");

							// ///////////////

							/*
							 * end : save to server
							 */

						}
					}
					adapter.close();
				}

			}
		});

		addreply.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View m) {

				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					adapter.open();
					RelativeLayout parentlayout = (RelativeLayout) m
							.getParent().getParent();
					View view = parentlayout.findViewById(R.id.peygham);
					TextView x = (TextView) view;
					String item = x.getText().toString();
					int commentid = 0;
					for (CommentInFroum listItem : cmt) {
						if (item.equals(listItem.getDesk())) {

							commentid = listItem.getId();
						}
					}

					f.CommentId(commentid);
					f.groupPosition(groupPosition);
					util.ReplyLayout((Activity) context, mainComment.getText()
							.toString(), true);

					// final SharedPreferences groupId = context
					// .getSharedPreferences("Id", 0);
					//
					// groupId.edit().putInt("main_Id", groupPosition).commit();
					//
					// DialogcmtInfroum dialog = new DialogcmtInfroum(f,
					// commentid, context, froumID,
					// R.layout.dialog_addcomment, 3);
					// dialog.show();

					adapter.close();
				}
			}
		});

		reportComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int d = (int) getGroupId(groupPosition);
				final CommentInFroum w = (CommentInFroum) getGroup(d);
				if (w != null) {
					itemId = w.getId();
					userIdsender = w.getUserid();
					description = w.getDesk();

				}

				if (util.getCurrentUser() != null) {

					adapter.open();
					int countReply = adapter.getCountOfReplyInFroum(froumID,
							comment.getId());
					adapter.close();

					if (util.getCurrentUser().getId() == userIdsender) {

						if (countReply == 0) {
							menuItems = new ArrayList<String>();
							menuItems.clear();
							menuItems.add("کپی");
							menuItems.add("حذف");
						} else {

							menuItems = new ArrayList<String>();
							menuItems.clear();
							menuItems.add("کپی");

						}

					} else {
						menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("گزارش تخلف");
					}

				} else {
					menuItems = new ArrayList<String>();

					menuItems.clear();
					menuItems.add("کپی");
				}

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);

				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(description);

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (util.getCurrentUser() != null)
								util.reportAbuse(userIdsender, 5, itemId,
										description, w.getFroumid());
							else
								Toast.makeText(context, "ابتدا باید وارد شوید",
										0).show();
						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null
									&& util.getCurrentUser().getId() == userIdsender)
								deleteItems(itemId);
							else {

								Toast.makeText(context, "", 0).show();
							}
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

				// ///////////////////////////////////////////

				// int u = 0;
				// int i = 0;
				// String t = "";
				//
				// int dd = (int) getGroupId(groupPosition);
				// CommentInFroum ww = (CommentInFroum) getGroup(dd);
				// if (ww != null) {
				// u = ww.getUserid();
				// t = ww.getDesk();
				// }
				// adapter.open();
				//
				// if (adapter.getCountOfReplyInFroum(froumID, comment.getId())
				// > 0) {
				// i = -1;
				//
				// DialogLongClick dia = new DialogLongClick(context, 5, u, i,
				// f, t);
				// dia.show();
				//
				// } else {
				//
				// // برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
				// // شود
				//
				// int d = (int) getGroupId(groupPosition);
				// CommentInFroum w = (CommentInFroum) getGroup(d);
				// if (w != null) {
				// i = w.getId();
				// u = w.getUserid();
				// t = ww.getDesk();
				//
				// }
				//
				// // //////////////////////////
				//
				// DialogLongClick dia = new DialogLongClick(context, 5, u, i,
				// f, t);
				// WindowManager.LayoutParams lp = new
				// WindowManager.LayoutParams();
				// lp.copyFrom(dia.getWindow().getAttributes());
				// lp.width = (int) (util.getScreenwidth() / 1.5);
				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				// ;
				// dia.show();
				//
				// dia.getWindow().setAttributes(lp);
				// dia.getWindow().setBackgroundDrawable(
				// new ColorDrawable(
				// android.graphics.Color.TRANSPARENT));
				// }
				// adapter.close();

				// }
			}

		});

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mExpandableListView.setSelectedGroup(groupPosition);

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

	public boolean hasStableIds() {
		return true;
	}

	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

	@Override
	public void processFinish(String output) {
		if (IsDeleteing == true) {

			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

			adapter.open();
			adapter.deleteOnlyCommentFroum(itemId);
			adapter.close();

			f.updateList();

		} else {
			if (!"".equals(output)
					&& output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				int id = -1;
				try {
					id = Integer.valueOf(output);

					adapter.open();

					if (flag) {

						/*
						 * save like in database device
						 */

						if (adapter.isUserLikedComment(Currentuser.getId(),
								GlobalId, 1)) {
							adapter.deleteLikeFromCommentInFroum(GlobalId,
									Currentuser.getId(), 1);

							notifyDataSetChanged();
							if (ringProgressDialog != null) {
								ringProgressDialog.dismiss();

							}

						} else {
							adapter.InsertLikeCommentFroumToDatabase(id,
									Currentuser.getId(), 1, GlobalId,
									serverDate);

							notifyDataSetChanged();
							if (ringProgressDialog != null) {
								ringProgressDialog.dismiss();

							}

						}
					} else {
						/*
						 * save dislike in database device
						 */

						if (adapter.isUserLikedComment(Currentuser.getId(),
								GlobalId, 0)) {
							adapter.deleteLikeFromCommentInFroum(GlobalId,
									Currentuser.getId(), 0);
							notifyDataSetChanged();
							if (ringProgressDialog != null) {
								ringProgressDialog.dismiss();

							}

						} else {
							adapter.InsertLikeCommentFroumToDatabase(id,
									Currentuser.getId(), 0, GlobalId,
									serverDate);

							notifyDataSetChanged();
							if (ringProgressDialog != null) {
								ringProgressDialog.dismiss();

							}

						}
					}

					adapter.close();

				} catch (NumberFormatException e) {
					serverDate = output;

					if (flag == true) {
						params = new LinkedHashMap<String, String>();
						if (context != null) {

							saving = new Saving(context);
							saving.delegate = ExpandableCommentFroum.this;

							params.put("TableName", "LikeInComment");

							params.put("UserId",
									String.valueOf(Currentuser.getId()));
							params.put("IsLike", String.valueOf(1));
							params.put("CommentId", String.valueOf(GlobalId));
							params.put("ModifyDate", serverDate);
							params.put("IsUpdate", "0");
							params.put("Date", serverDate);

							params.put("Id", "0");

							saving.execute(params);

							ringProgressDialog = ProgressDialog.show(context,
									"", "لطفا منتظر بمانید...", true);
						}
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

						notifyDataSetChanged();

					} else {

						params = new LinkedHashMap<String, String>();

						if (context != null) {

							saving = new Saving(context);
							saving.delegate = ExpandableCommentFroum.this;

							params.put("TableName", "LikeInComment");

							params.put("UserId",
									String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(0));
							params.put("CommentId", String.valueOf(GlobalId));
							params.put("ModifyDate", serverDate);
							params.put("Date", serverDate);

							params.put("IsUpdate", "0");
							params.put("Id", "0");

							saving.execute(params);
						}
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

						notifyDataSetChanged();

					}

					// Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT)
					// .show();
				}
			}

		}

	}

	public void deleteItems(int itemId) {

		params = new LinkedHashMap<String, String>();

		deleting = new Deleting(context);
		deleting.delegate = ExpandableCommentFroum.this;

		params.put("TableName", "CommentInFroum");
		params.put("ID", String.valueOf(itemId));

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
		IsDeleteing = true;

	}

	// @Override
	// public void CommProcessFinish(String output) {
	//
	// if (ringProgressDialog != null) {
	// ringProgressDialog.dismiss();
	// }
	//
	// adapter.open();
	// adapter.deleteOnlyCommentFroum(itemId);
	// adapter.close();
	//
	// f.updateList();
	//
	// }
}
