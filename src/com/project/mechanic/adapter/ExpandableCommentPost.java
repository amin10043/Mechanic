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
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.CommentInPost;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogLongClick;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.fragment.PostFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

public class ExpandableCommentPost extends BaseExpandableListAdapter implements AsyncInterface {

	Context context;
	private Map<CommentInPost, List<CommentInPost>> mapCollection;
	private ArrayList<CommentInPost> cmt;
	DataBaseAdapter adapter;
	Utility util;
	PostFragment f;
	int postID, userid, GlobalId, userId, iid;
	Users Currentuser, uu;;
	boolean flag;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	TextView countLike, countdisLike;
	// ProgressDialog ringProgressDialog;
	ImageView reportComment, reportReply;
	String serverDate = "";
	ServerDate date;
	CommentInPost reply;
	CommentInPost comment;
	List<String> menuItems;
	int itemId, userIdsender;;
	String description;
	boolean IsDeleteing, typeReport; // if(typeReport==true) comment else report
	Post po;
	com.project.mechanic.entity.Object object;
	int lastExpandedPosition = 0;
	int posGroup, posChild = -1;

	public ExpandableCommentPost(Context context, ArrayList<CommentInPost> laptops,
			Map<CommentInPost, List<CommentInPost>> mapCollection, PostFragment f, int postID) {
		this.context = context;
		this.mapCollection = mapCollection;
		this.cmt = laptops;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.f = f;
		this.postID = postID;
		adapter.open();
		Currentuser = util.getCurrentUser();
		po = adapter.getPostItembyid(postID);
		object = adapter.getObjectbyid(po.getObjectId());
		adapter.close();

	}

	public Object getChild(int groupPosition, int childPosition) {
		return mapCollection.get(cmt.get(groupPosition)).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		reply = (CommentInPost) getChild(groupPosition, childPosition);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_child_item, null);
		}

		TextViewEx mainReply = (TextViewEx) convertView.findViewById(R.id.reply_txt_child);

		TextView dateReply = (TextView) convertView.findViewById(R.id.date_replyed);
		TextView nameReplyer = (TextView) convertView.findViewById(R.id.name_replyed);

		ImageButton ReplyerPic = (ImageButton) convertView.findViewById(R.id.icon_reply_comment);

		reportReply = (ImageView) convertView.findViewById(R.id.reportImagereply);

		// final CommentInFroum comment = cmt.get(groupPosition);
		adapter.open();
		Users y = adapter.getUserbyid(reply.getUserId());
		adapter.close();

		userId = y.getId();
		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());
		rl.setPadding(10, 10, 10, 10);
		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// lp.setMargins(10, 10, 10, 10);
		// ReplyerPic.setScaleType(ScaleType.FIT_XY);
		ReplyerPic.setLayoutParams(lp);

		if (isAdmin(reply.getUserId()) == true) {

			adapter.open();
			object = adapter.getObjectbyid(po.getObjectId());
			adapter.close();

			nameReplyer.setText(object.getName());

			if (object.getImagePath2() != null) {

				try {
					Bitmap bmp = BitmapFactory.decodeFile(object.getImagePath2());

					ReplyerPic.setImageBitmap(Utility.getclip(bmp));

					ReplyerPic.setLayoutParams(lp);
				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}

			} else {
				ReplyerPic.setBackgroundResource(R.drawable.circle_drawable);

				ReplyerPic.setImageResource(R.drawable.no_img_profile);
			}

		} else {

			nameReplyer.setText(y.getName());

			if (y.getImagePath() == null) {
				ReplyerPic.setImageResource(R.drawable.no_img_profile);
				ReplyerPic.setLayoutParams(lp);

			} else {

				// byte[] byteImageProfile = y.getImage();

				try {

					Bitmap bmp = BitmapFactory.decodeFile(y.getImagePath());

					ReplyerPic.setImageBitmap(Utility.getclip(bmp));

					ReplyerPic.setLayoutParams(lp);

				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}

			}

			nameReplyer.setTypeface(util.SetFontIranSans());
		}

		reportReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// int i = 0;
				// int u = 0;
				// String t = "";
				// برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
				// شود

				int d = (int) getGroupId(groupPosition);
				final CommentInPost w = (CommentInPost) getChild(d, childPosition);
				if (w != null) {
					itemId = w.getId();
					userIdsender = w.getUserId();
					description = w.getDesc();
				}

				if (util.getCurrentUser() != null) {

					if (util.getCurrentUser().getId() == userIdsender || isAdmin(util.getCurrentUser().getId())) {

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
				popupMenu.show();

				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(description);

						}

						if (item.getTitle().equals("گزارش تخلف")) {

							if (util.getCurrentUser() != null)

								util.reportAbuse(userIdsender, StaticValues.TypeReportReplayPost, itemId, description,
										w.getPostId(), 0);
							else
								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
						}

						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender) {
								posGroup = groupPosition;
								posChild = childPosition;
								deleteItems(itemId);
								typeReport = false;
							} else {

								Toast.makeText(context, "", 0).show();
							}
						}

						return false;
					}
				};
				popupMenu.setOnMenuItemClickListener(menuitem);

				// Toast.makeText(context, "id = " + i + "Userid = " + u,
				// 0).show();
				// //////////////////////////

				// DialogLongClick dia = new DialogLongClick(context, 5, u, i,
				// f, t);
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
				// dia.getWindow().setBackgroundDrawable(new
				// ColorDrawable(android.graphics.Color.TRANSPARENT));

			}
		});

		ReplyerPic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});
		mainReply.setText(reply.getDesc(), true);
		mainReply.setTypeface(util.SetFontIranSans());
		dateReply.setText(util.getPersianDate(reply.getDate()));

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

	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {

		adapter.open();
		if (groupPosition <= cmt.size())
			comment = cmt.get(groupPosition);
		final Users x = adapter.getUserbyid(comment.getUserId());
		// userId= x.getId();
		adapter.close();

		if (convertView == null) {
			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = infalInflater.inflate(R.layout.row_group_item, null);
		}

		// start find view
		final TextViewEx mainComment = (TextViewEx) convertView.findViewById(R.id.peygham);

		TextView nameCommenter = (TextView) convertView.findViewById(R.id.name_froum_profile);

		countLike = (TextView) convertView.findViewById(R.id.countCommentFroum);
		countdisLike = (TextView) convertView.findViewById(R.id.countdislikecommentFroum);

		TextView dateCommenter = (TextView) convertView.findViewById(R.id.date_commented_in_froum);

		TextView countOfReply = (TextView) convertView.findViewById(R.id.numberOfCommentTopic);

		LinearLayout addreply = (LinearLayout) convertView.findViewById(R.id.cmffff);

		ImageButton profileImage = (ImageButton) convertView.findViewById(R.id.icon_froum_profile);

		final ImageButton imglikeComment = (ImageButton) convertView.findViewById(R.id.positive_img);

		final ImageButton imgdislikeComment = (ImageButton) convertView.findViewById(R.id.negative_img);

		final ExpandableListView mExpandableListView = (ExpandableListView) parent;

		reportComment = (ImageView) convertView.findViewById(R.id.reportImage);

		// end find view

		// start set variable
		adapter.open();
		Currentuser = util.getCurrentUser();

		if (Currentuser == null) {
			imglikeComment.setBackgroundResource((R.drawable.positive_off));
			imgdislikeComment.setBackgroundResource((R.drawable.negative_off));
		} else {
			if (adapter.isUserLikedCommentPost(Currentuser.getId(), comment.getId(), 1)) {
				imglikeComment.setBackgroundResource((R.drawable.positive));
			} else {
				imglikeComment.setBackgroundResource((R.drawable.positive_off));

			}
			if (adapter.isUserLikedCommentPost(Currentuser.getId(), comment.getId(), 0)) {
				imgdislikeComment.setBackgroundResource((R.drawable.negative));
			} else {
				imgdislikeComment.setBackgroundResource((R.drawable.negative_off));

			}

		}

		adapter.close();

		mainComment.setText(comment.getDesc(), true);
		dateCommenter.setText(util.getPersianDate(comment.getDate()));
		// if (adapter.getCountOfReplyInFroum(froumID, comment.getId()) == 0) {
		// LinearLayout lrr = (LinearLayout) convertView
		// .findViewById(R.id.linearShowcountofRepply);
		// lrr.setVisibility(View.GONE);
		//
		// } else

		if (x != null) {

			if (isAdmin(comment.getUserId()) == true) {
				adapter.open();
				object = adapter.getObjectbyid(po.getObjectId());
				adapter.close();

				nameCommenter.setText(object.getName());

				if (object.getImagePath2() != null) {

					try {
						Bitmap bmp = BitmapFactory.decodeFile(object.getImagePath2());

						profileImage.setImageBitmap(Utility.getclip(bmp));

					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}

				} else {
					profileImage.setBackgroundResource(R.drawable.circle_drawable);

					profileImage.setImageResource(R.drawable.no_img_profile);
				}

			} else {
				nameCommenter.setText(x.getName());

				if (x.getImagePath() == null) {
					profileImage.setBackgroundResource(R.drawable.circle_drawable);

					profileImage.setImageResource(R.drawable.no_img_profile);
				} else {

					// byte[] byteImageProfile = x.getImage();

					try {
						Bitmap bmp = BitmapFactory.decodeFile(x.getImagePath());

						profileImage.setImageBitmap(Utility.getclip(bmp));
					} catch (OutOfMemoryError e) {
						e.printStackTrace();
					}

				}
			}
			
			nameCommenter.setTypeface(util.SetFontIranSans());

		}
		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.icon_header_comment_froum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());
		rl.setPadding(10, 10, 10, 10);
		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
//		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		lp.setMargins(10, 10, 10, 10);
		profileImage.setLayoutParams(lp);
		profileImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				final CommentInPost comment = cmt.get(groupPosition);
				final Users x = adapter.getUserbyid(comment.getUserId());
				userId = x.getId();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
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

		for (CommentInPost listItem : cmt) {
			if (mainComment.getText().toString().equals(listItem.getDesc())) {

				c = listItem.getId();

			}
		}
		adapter.open();
		countOfReply.setText(adapter.getCountOfReplyInPost(postID, comment.getId()).toString());
		countLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikePost(c, 1)));
		countdisLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikePost(c, 0)));
		adapter.close();

		// end set variable

		notifyDataSetChanged();

		imgdislikeComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View t) {

				adapter.open();
				if (Currentuser == null) {
					Toast.makeText(context, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
					return;
				} else {

					flag = false;
					LinearLayout parentlayout = (LinearLayout) t.getParent().getParent().getParent().getParent();
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumDislike = parentlayout.findViewById(R.id.countdislikecommentFroum);
					TextView txtdislike = (TextView) viewnumDislike;

					int id = 0;

					ImageView dis = (ImageView) parentlayout.findViewById(R.id.negative_img);
					TextView txtDis = (TextView) parentlayout.findViewById(R.id.countdislikecommentFroum);

					dis.setBackgroundResource((R.drawable.negative));

					for (CommentInPost listItem : cmt) {
						if (txtMaincmt.getText().toString().equals(listItem.getDesc())) {

							GlobalId = id = listItem.getId();

						}
					}

					// send to database

					if (adapter.isUserLikedCommentPost(Currentuser.getId(), id, 0)) {

						/*
						 * start >>>>> delete dislike from server
						 */

						params = new LinkedHashMap<String, String>();
						if (context != null) {

							deleting = new Deleting(context);
							deleting.delegate = ExpandableCommentPost.this;

							params.put("TableName", "LikeInCommentPost");

							params.put("UserId", String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(0));
							params.put("CommentId", String.valueOf(id));

							deleting.execute(params);

							// ringProgressDialog = ProgressDialog.show(context,
							// "", "لطفا منتظر بمانید...", true);
						}
						// ringProgressDialog.setCancelable(true);
						//
						// new Thread(new Runnable() {
						//
						// @Override
						// public void run() {
						//
						// try {
						//
						// Thread.sleep(10000);
						//
						// } catch (Exception e) {
						//
						// }
						// }
						// }).start();

						/*
						 * end >>>>> delete dislike from server
						 */

					} else {
						if (adapter.isUserLikedCommentPost(Currentuser.getId(), id, 1)) {
							Toast.makeText(context, "شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						} else {

							/*
							 * start >>>>> save dislike to server
							 */
							if (context != null) {

								date = new ServerDate(context);
								date.delegate = ExpandableCommentPost.this;
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
					Toast.makeText(context, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
					return;
				} else {
					flag = true;
					adapter.open();

					// // peyda kardan id comment sabt shode

					LinearLayout parentlayout = (LinearLayout) v.getParent().getParent().getParent().getParent();

					ImageView li = (ImageView) parentlayout.findViewById(R.id.positive_img);
					li.setBackgroundResource((R.drawable.positive));

					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumlike = parentlayout.findViewById(R.id.countCommentFroum);
					TextView txtlike = (TextView) viewnumlike;

					int cmtId = 0;

					for (CommentInPost listItem : cmt) {
						if (txtMaincmt.getText().toString().equals(listItem.getDesc())) {

							GlobalId = cmtId = listItem.getId();
						}
					}

					// send to database

					if (adapter.isUserLikedCommentPost(Currentuser.getId(), cmtId, 1)) {
						/*
						 * start >>>>> delete like from server
						 */

						params = new LinkedHashMap<String, String>();

						if (context != null) {

							deleting = new Deleting(context);
							deleting.delegate = ExpandableCommentPost.this;

							params.put("TableName", "LikeInCommentPost");

							params.put("UserId", String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(1));
							params.put("CommentId", String.valueOf(cmtId));

							deleting.execute(params);

							// ringProgressDialog = ProgressDialog.show(context,
							// "", "لطفا منتظر بمانید...", true);
						}
						// ringProgressDialog.setCancelable(true);
						//
						// new Thread(new Runnable() {
						//
						// @Override
						// public void run() {
						//
						// try {
						//
						// Thread.sleep(10000);
						//
						// } catch (Exception e) {
						//
						// }
						// }
						// }).start();

						/*
						 * end >>> delete like from server
						 */

					} else {

						if (adapter.isUserLikedCommentPost(Currentuser.getId(), cmtId, 0)) {
							Toast.makeText(context, "شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						} else {

							/*
							 * start : save like to server
							 */

							// ///////////

							date = new ServerDate(context);
							date.delegate = ExpandableCommentPost.this;
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
					Toast.makeText(context, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
					return;
				} else {
					adapter.open();
					LinearLayout parentlayout = (LinearLayout) m.getParent().getParent();
					View view = parentlayout.findViewById(R.id.peygham);
					TextView x = (TextView) view;
					String item = x.getText().toString();
					int commentid = 0;
					for (CommentInPost listItem : cmt) {
						if (item.equals(listItem.getDesc())) {

							commentid = listItem.getId();
						}
					}

					f.CommentId(commentid);
					f.groupPosition(groupPosition);
					util.ReplyLayout((Activity) context, mainComment.getText().toString(), true);

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
			public void onClick(View cc) {

				userIdsender = 0;
				itemId = 0;
				description = "";

				int dd = (int) getGroupId(groupPosition);
				final CommentInPost ww = (CommentInPost) getGroup(dd);
				if (ww != null) {
					userIdsender = ww.getUserId();
					description = ww.getDesc();
					itemId = ww.getId();

				}

				if (util.getCurrentUser() != null) {

					// adapter.open();
					// int countReply = adapter.getCountOfReplyInPost(postID,
					// comment.getId());
					// adapter.close();

					if (util.getCurrentUser().getId() == userIdsender || isAdmin(util.getCurrentUser().getId())) {

						// if (countReply == 0) {
						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("حذف");
						// }
						// else {
						//
						// menuItems = new ArrayList<String>();
						// menuItems.clear();
						// menuItems.add("کپی");
						//
						// }
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

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, cc);
				popupMenu.show();

				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

					@Override
					public boolean onMenuItemClick(MenuItem item) {

						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(description);

						}

						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender) {
								posGroup = groupPosition;

								deleteItems(itemId);
								typeReport = true;
							} else {

								Toast.makeText(context, "", 0).show();
							}
						}
						if (item.getTitle().equals("گزارش تخلف")) {

							if (util.getCurrentUser() != null)

								util.reportAbuse(userIdsender, StaticValues.TypeReportCommentPost, itemId, description,
										ww.getPostId(), 0);
							else
								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
						}

						return false;
					}
				};
				popupMenu.setOnMenuItemClickListener(menuitem);

				//
				//
				//
				// if (adapter.getCountOfReplyInPost(postID, comment.getId()) >
				// 0) {
				// itemId = -1;
				//
				// DialogLongClick dia = new DialogLongClick(context, 5,
				// userIdsender, itemId, f, description);
				// dia.show();
				//
				// } else {
				//
				// // برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
				// // شود
				//
				// int d = (int) getGroupId(groupPosition);
				// CommentInPost w = (CommentInPost) getGroup(d);
				// if (w != null) {
				// itemId = w.getId();
				// userIdsender = w.getUserId();
				// description = ww.getDesc();
				//
				// }
				//
				// // //////////////////////////
				//
				// DialogLongClick dia = new DialogLongClick(context, 5,
				// userIdsender, itemId, f, description);
				// WindowManager.LayoutParams lp = new
				// WindowManager.LayoutParams();
				// lp.copyFrom(dia.getWindow().getAttributes());
				// lp.width = (int) (util.getScreenwidth() / 1.5);
				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
				// ;
				// dia.show();
				//
				// dia.getWindow().setAttributes(lp);
				// dia.getWindow().setBackgroundDrawable(new
				// ColorDrawable(android.graphics.Color.TRANSPARENT));
				// }
				// adapter.close();

			}

		});

		convertView.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				mExpandableListView.setSelectedGroup(groupPosition);

				if (isExpanded) {
					mExpandableListView.collapseGroup(groupPosition);

				} else {
					mExpandableListView.expandGroup(groupPosition);
					mExpandableListView.setSelectedChild(groupPosition, 0, true);

					if (lastExpandedPosition != -1 && groupPosition != lastExpandedPosition) {
						mExpandableListView.collapseGroup(lastExpandedPosition);
					}
					lastExpandedPosition = groupPosition;

				}

			}

		});
		mainComment.setTypeface(util.SetFontIranSans());

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

		if (util.checkError(output) == false) {

			if (IsDeleteing == true) {

				// if (ringProgressDialog != null) {
				// ringProgressDialog.dismiss();
				// }
				adapter.open();

				if (typeReport == true) {

					adapter.deleteOnlyCommentPost(itemId);
					adapter.deleteLikeInCommentPost(itemId);
					adapter.deleteReplyPost(itemId);

				} else {
					adapter.deleteOnlyReplyPost(itemId);

					// f.expanding(groupPosition);

				}

				adapter.close();

				f.expanding(posGroup, posChild, false);

			} else {

				if (!"".equals(output) && output != null
						&& !(output.contains("Exception") || output.contains("java"))) {

					int id = -1;
					try {
						id = Integer.valueOf(output);

						adapter.open();

						if (flag) {

							/*
							 * save like in database device
							 */

							if (adapter.isUserLikedCommentPost(Currentuser.getId(), GlobalId, 1)) {
								adapter.deleteLikeFromCommentInPost(GlobalId, Currentuser.getId(), 1);

								notifyDataSetChanged();
								// if (ringProgressDialog != null) {
								// ringProgressDialog.dismiss();
								//
								// }

							} else {
								adapter.InsertLikeCommentPostToDatabase(id, Currentuser.getId(), 1, GlobalId,
										serverDate);

								notifyDataSetChanged();
								// if (ringProgressDialog != null) {
								// ringProgressDialog.dismiss();
								//
								// }

							}
						} else {
							/*
							 * save dislike in database device
							 */

							if (adapter.isUserLikedCommentPost(Currentuser.getId(), GlobalId, 0)) {
								adapter.deleteLikeFromCommentInPost(GlobalId, Currentuser.getId(), 0);
								notifyDataSetChanged();
								// if (ringProgressDialog != null) {
								// ringProgressDialog.dismiss();
								//
								// }

							} else {
								adapter.InsertLikeCommentPostToDatabase(id, Currentuser.getId(), 0, GlobalId,
										serverDate);

								notifyDataSetChanged();
								// if (ringProgressDialog != null) {
								// ringProgressDialog.dismiss();
								//
								// }

							}
						}

						adapter.close();

					} catch (NumberFormatException e) {
						serverDate = output;

						if (flag == true) {
							params = new LinkedHashMap<String, String>();
							if (context != null) {

								saving = new Saving(context);
								saving.delegate = ExpandableCommentPost.this;

								params.put("TableName", "LikeInCommentPost");

								params.put("UserId", String.valueOf(Currentuser.getId()));
								params.put("IsLike", String.valueOf(1));
								params.put("CommentId", String.valueOf(GlobalId));
								params.put("ModifyDate", serverDate);
								params.put("IsUpdate", "0");
								params.put("Date", serverDate);

								params.put("Id", "0");

								saving.execute(params);

								// ringProgressDialog =
								// ProgressDialog.show(context,
								// "", "لطفا منتظر بمانید...", true);
							}
							// ringProgressDialog.setCancelable(true);
							//
							// new Thread(new Runnable() {
							//
							// @Override
							// public void run() {
							//
							// try {
							//
							// Thread.sleep(10000);
							//
							// } catch (Exception e) {
							//
							// }
							// }
							// }).start();

							notifyDataSetChanged();

						} else {

							params = new LinkedHashMap<String, String>();

							if (context != null) {

								saving = new Saving(context);
								saving.delegate = ExpandableCommentPost.this;

								params.put("TableName", "LikeInCommentPost");

								params.put("UserId", String.valueOf(Currentuser.getId()));

								params.put("IsLike", String.valueOf(0));
								params.put("CommentId", String.valueOf(GlobalId));
								params.put("ModifyDate", serverDate);
								params.put("Date", serverDate);

								params.put("IsUpdate", "0");
								params.put("Id", "0");

								saving.execute(params);
							}
							// ringProgressDialog = ProgressDialog.show(context,
							// "",
							// "لطفا منتظر بمانید...", true);

							// ringProgressDialog.setCancelable(true);
							//
							// new Thread(new Runnable() {
							//
							// @Override
							// public void run() {
							//
							// try {
							//
							// Thread.sleep(10000);
							//
							// } catch (Exception e) {
							//
							// }
							// }
							//
							// }).start();

							notifyDataSetChanged();

						}

						// Toast.makeText(context, "خطا در ثبت",
						// Toast.LENGTH_SHORT)
						// .show();
					}
				}
			}
		}
	}

	public void deleteItems(int itemId) {

		params = new LinkedHashMap<String, String>();

		deleting = new Deleting(context);
		deleting.delegate = ExpandableCommentPost.this;

		params.put("TableName", "CommentInPost");
		params.put("Id", String.valueOf(itemId));

		deleting.execute(params);

		// ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر
		// بمانید...", true);
		//
		// ringProgressDialog.setCancelable(true);
		// new Thread(new Runnable() {
		//
		// @Override
		// public void run() {
		//
		// try {
		//
		// Thread.sleep(10000);
		//
		// } catch (Exception e) {
		//
		// }
		// }
		// }).start();
		IsDeleteing = true;

	}

	private boolean isAdmin(int userId) {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = false;

		adapter.open();
		List<SubAdmin> subAdminList = adapter.getAdmin(po.getObjectId());
		adapter.close();

		boolean fl1 = false;

		List<Integer> Ids = new ArrayList<Integer>();
		if (subAdminList.size() > 0)
			for (int i = 0; i < subAdminList.size(); i++) {
				Ids.add(subAdminList.get(i).getUserId());
			}

		for (int j = 0; j < Ids.size(); j++) {
			if (userId == Ids.get(j))
				fl1 = true;
		}
		// if (Currentuser == null)
		// isSave = false;
		// else

		if (fl1 == true || userId == object.getUserId())
			isSave = true;

		return isSave;

	}

}
