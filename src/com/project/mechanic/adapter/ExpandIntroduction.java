//package com.project.mechanic.adapter;
//
//import java.util.ArrayList;
//import java.util.LinkedHashMap;
//import java.util.List;
//import java.util.Map;
//
//import android.app.Activity;
//import android.app.ProgressDialog;
//import android.content.Context;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentTransaction;
//import android.view.LayoutInflater;
//import android.view.MenuItem;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup;
//import android.widget.BaseExpandableListAdapter;
//import android.widget.ExpandableListView;
//import android.widget.ImageButton;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//import android.widget.PopupMenu;
//import android.widget.PopupMenu.OnMenuItemClickListener;
//import android.widget.RelativeLayout;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import com.project.mechanic.MainActivity;
//import com.project.mechanic.R;
//import com.project.mechanic.StaticValues;
//import com.project.mechanic.entity.CommentInObject;
//import com.project.mechanic.entity.Users;
//import com.project.mechanic.fragment.InformationUser;
//import com.project.mechanic.fragment.IntroductionFragment;
//import com.project.mechanic.inter.AsyncInterface;
//import com.project.mechanic.model.DataBaseAdapter;
//import com.project.mechanic.service.Deleting;
//import com.project.mechanic.service.Saving;
//import com.project.mechanic.utility.Utility;
//
//public class ExpandIntroduction extends BaseExpandableListAdapter implements AsyncInterface {
//
//	Context context;
//	private Map<CommentInObject, List<CommentInObject>> mapCollection;
//	private ArrayList<CommentInObject> CommentList;
//	DataBaseAdapter adapter;
//	Utility util;
//	Fragment f;
//	int ObjectID, userid;
//	Users Currentuser;
//	ProgressDialog ringProgressDialog;
//	boolean flag;
//
//	int GlobalId;
//	int userId;
//
//	Saving saving;
//	Deleting deleting;
//	Map<String, String> params;
//	ImageView reportComment, reportReply;
//	int itemId, userIdsender;;
//	String content;
//	List<String> menuItems;
//	boolean IsDeleteing;
//
//	public ExpandIntroduction(Context context, ArrayList<CommentInObject> CommentList,
//			Map<CommentInObject, List<CommentInObject>> mapCollection, Fragment introductionFragment,
//			int ObjectId) {
//
//		this.context = context;
//		this.mapCollection = mapCollection;
//		this.CommentList = CommentList;
//		adapter = new DataBaseAdapter(context);
//		util = new Utility(context);
//		this.f = introductionFragment;
//		this.ObjectID = ObjectId;
//		adapter.open();
//		Currentuser = util.getCurrentUser();
//		adapter.close();
//	}
//
//	@Override
//	public Object getChild(int groupPosition, int childPosition) {
//		return mapCollection.get(CommentList.get(groupPosition)).get(childPosition);
//
//	}
//
//	@Override
//	public long getChildId(int groupPosition, int childPosition) {
//		return childPosition;
//	}
//
//	@Override
//	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
//			ViewGroup parent) {
//		CommentInObject reply = (CommentInObject) getChild(groupPosition, childPosition);
//		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//		if (convertView == null) {
//			convertView = inflater.inflate(R.layout.row_child_item, null);
//		}
//		TextView mainReply = (TextView) convertView.findViewById(R.id.reply_txt_child);
//
//		TextView dateReply = (TextView) convertView.findViewById(R.id.date_replyed);
//		TextView nameReplyer = (TextView) convertView.findViewById(R.id.name_replyed);
//
//		ImageButton ReplyerPic = (ImageButton) convertView.findViewById(R.id.icon_reply_comment);
//
//		reportReply = (ImageView) convertView.findViewById(R.id.reportImagereply);
//		adapter.open();
//
//		final CommentInObject comment = CommentList.get(groupPosition);
//		Users y = adapter.getUserbyid(comment.getUserid());
//
//		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.main_icon_reply);
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());
//
//		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
//		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
//		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		lp.setMargins(5, 5, 5, 5);
//		if (y.getImagePath() == null) {
//			ReplyerPic.setBackgroundResource(R.drawable.circle_drawable);
//			ReplyerPic.setImageResource(R.drawable.no_img_profile);
//			ReplyerPic.setLayoutParams(lp);
//
//		} else {
//
//			Bitmap bmp = BitmapFactory.decodeFile(y.getImagePath());
//
//			ReplyerPic.setImageBitmap(Utility.getclip(bmp));
//
//			ReplyerPic.setLayoutParams(lp);
//		}
//
//		reportReply.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				int id = (int) getGroupId(groupPosition);
//				CommentInObject co = (CommentInObject) getChild(id, childPosition);
//				if (co != null) {
//					itemId = co.getId();
//					userIdsender = co.getUserid();
//					content = co.getDescription();
//				}
//
//				if (util.getCurrentUser() != null) {
//
//					if (util.getCurrentUser().getId() == userIdsender) {
//
//						menuItems = new ArrayList<String>();
//						menuItems.clear();
//						menuItems.add("کپی");
//						menuItems.add("حذف");
//
//					} else {
//						menuItems = new ArrayList<String>();
//
//						menuItems.clear();
//						menuItems.add("کپی");
//						menuItems.add("گزارش تخلف");
//					}
//
//				} else {
//					menuItems = new ArrayList<String>();
//
//					menuItems.clear();
//					menuItems.add("کپی");
//				}
//
//				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
//				popupMenu.show();
//
//				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {
//
//					@Override
//					public boolean onMenuItemClick(MenuItem item) {
//
//						if (item.getTitle().equals("کپی")) {
//
//							util.CopyToClipboard(content);
//
//						}
//						if (item.getTitle().equals("گزارش تخلف")) {
//
//							if (util.getCurrentUser() != null){
//								
//							}
////								util.reportAbuse(userIdsender, 7, itemId, content, ObjectID);
//							else
//								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
//						}
//						if (item.getTitle().equals("حذف")) {
//							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender)
//								deleteItems(itemId);
//							else {
//
//								Toast.makeText(context, "", 0).show();
//							}
//						}
//
//						return false;
//					}
//				};
//
//				popupMenu.setOnMenuItemClickListener(menuitem);
//
//				// int i = 0;
//				// int u = 0;
//				// String t = "";
//				// // برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
//				// // شود
//				//
//				// int d = (int) getGroupId(groupPosition);
//				// CommentInObject w = (CommentInObject) getChild(d,
//				// childPosition);
//				// if (w != null) {
//				// i = w.getId();
//				// u = w.getUserid();
//				// t = w.getDescription();
//				// }
//				// Toast.makeText(context, "id = " + i + "Userid = " + u, 0)
//				// .show();
//				//
//				// DialogLongClick dia = new DialogLongClick(context, 7, u, i,
//				// f,
//				// t);
//				// WindowManager.LayoutParams lp = new
//				// WindowManager.LayoutParams();
//				// lp.copyFrom(dia.getWindow().getAttributes());
//				// lp.width = (int) (util.getScreenwidth() / 1.5);
//				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//				// ;
//				// dia.show();
//				//
//				// dia.getWindow().setAttributes(lp);
//				// dia.getWindow().setBackgroundDrawable(
//				// new ColorDrawable(android.graphics.Color.TRANSPARENT));
//			}
//		});
//
//		ReplyerPic.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				adapter.open();
//				CommentInObject comment = CommentList.get(groupPosition);
//				Users y = adapter.getUserbyid(comment.getUserid());
//				userId = y.getId();
//				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
//				InformationUser fragment = new InformationUser();
//				Bundle bundle = new Bundle();
//				bundle.putInt("userId", userId);
//				fragment.setArguments(bundle);
//				trans.replace(R.id.content_frame, fragment);
//				trans.commit();
//
//			}
//		});
//		mainReply.setText(reply.getDescription());
//		dateReply.setText(util.getPersianDate(reply.getDatetime()));
//		nameReplyer.setText(y.getName());
//
//		adapter.close();
//
//		return convertView;
//	}
//
//	@Override
//	public int getChildrenCount(int groupPosition) {
//		if (mapCollection.get(CommentList.get(groupPosition)) != null)
//			return mapCollection.get(CommentList.get(groupPosition)).size();
//		return 0;
//	}
//
//	@Override
//	public Object getGroup(int groupPosition) {
//		return CommentList.get(groupPosition);
//
//	}
//
//	@Override
//	public int getGroupCount() {
//		return CommentList.size();
//
//	}
//
//	@Override
//	public long getGroupId(int groupPosition) {
//		return groupPosition;
//
//	}
//
//	@Override
//	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {
//		adapter.open();
//
//		final CommentInObject comment = CommentList.get(groupPosition);
//		Users x = adapter.getUserbyid(comment.getUserid());
//
//		adapter.close();
//
//		if (convertView == null) {
//			LayoutInflater infalInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//			convertView = infalInflater.inflate(R.layout.row_group_item, null);
//		}
//
//		// start find view
//		final TextView mainComment = (TextView) convertView.findViewById(R.id.peygham);
//
//		TextView nameCommenter = (TextView) convertView.findViewById(R.id.name_froum_profile);
//
//		final TextView countLike = (TextView) convertView.findViewById(R.id.countCommentFroum);
//		final TextView countdisLike = (TextView) convertView.findViewById(R.id.countdislikecommentFroum);
//
//		TextView dateCommenter = (TextView) convertView.findViewById(R.id.date_commented_in_froum);
//
//		TextView countOfReply = (TextView) convertView.findViewById(R.id.numberOfCommentTopic);
//
//		LinearLayout addreply = (LinearLayout) convertView.findViewById(R.id.addCommentToTopic);
//
//		ImageButton profileImage = (ImageButton) convertView.findViewById(R.id.icon_froum_profile);
//
//		reportComment = (ImageView) convertView.findViewById(R.id.reportImage);
//
//		final ImageButton imglikeComment = (ImageButton) convertView.findViewById(R.id.positive_img);
//
//		final ImageButton imgdislikeComment = (ImageButton) convertView.findViewById(R.id.negative_img);
//
//		final ExpandableListView mExpandableListView = (ExpandableListView) parent;
//
//		// end find view
//
//		// start set variable
//		adapter.open();
//		Currentuser = util.getCurrentUser();
//
//		if (Currentuser == null) {
//			imglikeComment.setImageResource((R.drawable.positive_off));
//			imgdislikeComment.setImageResource((R.drawable.negative_off));
//		} else {
//			if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), comment.getId(), 1)) {
//				imglikeComment.setImageResource((R.drawable.positive));
//			} else {
//				imglikeComment.setImageResource((R.drawable.positive_off));
//
//			}
//			if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), comment.getId(), 0)) {
//				imgdislikeComment.setImageResource((R.drawable.negative));
//			} else {
//				imgdislikeComment.setImageResource((R.drawable.negative_off));
//
//			}
//
//		}
//		profileImage.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				adapter.open();
//
//				CommentInObject comment = CommentList.get(groupPosition);
//				Users x = adapter.getUserbyid(comment.getUserid());
//				userId = x.getId();
//
//				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
//				InformationUser fragment = new InformationUser();
//				Bundle bundle = new Bundle();
//				bundle.putInt("userId", userId);
//				fragment.setArguments(bundle);
//				trans.replace(R.id.content_frame, fragment);
//				trans.commit();
//
//			}
//		});
//		mainComment.setText(comment.getDescription());
//		if (x == null) {
//			// ERROR ! the users that comment here not exist yet!!!
//		} else {
//			nameCommenter.setText(x.getName());
//		}
//		// dateCommenter.setText(util.getPersianDate(comment.getDatetime()));
//
//		countOfReply.setText(adapter.getCountOfReplyBrandPage(ObjectID, comment.getId()).toString());
//
//		int com = 0;
//		for (CommentInObject listItem : CommentList) {
//			if (mainComment.getText().toString().equals(listItem.getDescription())) {
//
//				com = listItem.getId();
//
//			}
//		}
//		countdisLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(com, 0)));
//		countLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(com, 1)));
//		// if (adapter.getCountOfReplyInObject(ObjectID, comment.getId()) == 0)
//		// {
//		// LinearLayout lrr = (LinearLayout) convertView
//		// .findViewById(R.id.linearShowcountofRepply);
//		// lrr.setVisibility(View.GONE);
//		//
//		// } else
//		// countOfReply.setText(adapter.getCountOfReplyInObject(ObjectID,
//		// comment.getId()).toString());
//
//		// countLike.setText(comment.getId());
//		// countdisLike.setText(comment.getFroumid());
//
//		// start... this code for set image of profile
//		adapter.open();
//		if (x.getImagePath() == null) {
//			profileImage.setBackgroundResource(R.drawable.circle_drawable);
//			;
//			profileImage.setImageResource(R.drawable.no_img_profile);
//		} else {
//
//			Bitmap bmp = BitmapFactory.decodeFile(x.getImagePath());
//
//			profileImage.setImageBitmap(Utility.getclip(bmp));
//		}
//		adapter.close();
//		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.icon_header_comment_froum);
//		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());
//
//		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
//		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
//		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
//		lp.setMargins(5, 5, 5, 5);
//		profileImage.setLayoutParams(lp);
//
//		// end... this code for set image of profile
//
//		adapter.close();
//
//		// end set variable
//
//		imgdislikeComment.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// khandan meghdar ghabli tedad like ha
//				// tabdil be int
//				// ezafe kardan 1 vahed be meghdar ghabli
//				// tabdil be String
//				adapter.open();
//
//				if (Currentuser == null) {
//					Toast.makeText(context, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
//					return;
//				} else {
//
//					flag = false;
//
//					RelativeLayout parentlayout = (RelativeLayout) v.getParent().getParent();
//					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
//					TextView txtMaincmt = (TextView) viewMaincmt;
//
//					View viewnumlike = parentlayout.findViewById(R.id.countCommentFroum);
//					TextView txtlike = (TextView) viewnumlike;
//
//					int CommentId = 0;
//
//					for (CommentInObject listItem : CommentList) {
//						if (txtMaincmt.getText().toString().equals(listItem.getDescription())) {
//
//							GlobalId = CommentId = listItem.getId();
//
//						}
//					}
//
//					// send to database
//
//					if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), CommentId, 0)) {
//
//						/*
//						 * start >>>>> delete dislike from server
//						 */
//
//						params = new LinkedHashMap<String, String>();
//						if (context != null) {
//
//							deleting = new Deleting(context);
//							deleting.delegate = ExpandIntroduction.this;
//
//							params.put("TableName", "LikeInCommentObject");
//
//							params.put("UserId", String.valueOf(Currentuser.getId()));
//
//							params.put("IsLike", String.valueOf(0));
//							params.put("CommentId", String.valueOf(CommentId));
//
//							deleting.execute(params);
//						}
//						ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);
//
//						ringProgressDialog.setCancelable(true);
//
//						new Thread(new Runnable() {
//
//							@Override
//							public void run() {
//
//								try {
//
//									Thread.sleep(10000);
//
//								} catch (Exception e) {
//
//								}
//							}
//						}).start();
//
//						/*
//						 * end >>>>> delete dislike from server
//						 */
//
//						// countdisLike.setText(String.valueOf(adapter
//						// .NumberOfLikeOrDisLikeBrandPage(CommentId, 0)));
//						//
//						// imgdislikeComment
//						// .setImageResource(R.drawable.negative_off);
//
//					} else {
//
//						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), CommentId, 1))
//							Toast.makeText(context, "شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
//									Toast.LENGTH_SHORT).show();
//						else {
//
//							params = new LinkedHashMap<String, String>();
//
//							if (context != null) {
//
//								saving = new Saving(context);
//								saving.delegate = ExpandIntroduction.this;
//
//								params.put("TableName", "LikeInCommentObject");
//
//								params.put("UserId", String.valueOf(Currentuser.getId()));
//
//								params.put("IsLike", String.valueOf(0));
//								params.put("CommentId", String.valueOf(CommentId));
//
//								params.put("IsUpdate", "0");
//								params.put("Id", "0");
//
//								saving.execute(params);
//
//								ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);
//							}
//							ringProgressDialog.setCancelable(true);
//
//							new Thread(new Runnable() {
//
//								@Override
//								public void run() {
//
//									try {
//
//										Thread.sleep(10000);
//
//									} catch (Exception e) {
//
//									}
//								}
//							}).start();
//
//							/*
//							 * end >>>>> save dislike to server
//							 */
//
//							notifyDataSetChanged();
//
//						}
//
//						// adapter.InsertLikeCommentFromObject(
//						// Currentuser.getId(), 0, CommentId);
//						// countdisLike.setText(String.valueOf(adapter
//						// .NumberOfLikeOrDisLikeBrandPage(CommentId,
//						// 0)));
//						// imgdislikeComment
//						// .setImageResource(R.drawable.negative);
//						// Toast.makeText(context, "دیس لایک اضافه شد", 0)
//						// .show();
//
//						// }
//					}
//				}
//			}
//		});
//		imglikeComment.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// khandan meghdar ghabli tedad like ha
//				// tabdil be int
//				// ezafe kardan 1 vahed be meghdar ghabli
//				// tabdil be String
//				adapter.open();
//				flag = true;
//
//				if (Currentuser == null) {
//					Toast.makeText(context, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
//					return;
//				} else {
//
//					RelativeLayout parentlayout = (RelativeLayout) v.getParent().getParent();
//					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
//					TextView txtMaincmt = (TextView) viewMaincmt;
//
//					View viewnumlike = parentlayout.findViewById(R.id.countCommentFroum);
//					TextView txtlike = (TextView) viewnumlike;
//
//					int CommentId = 0;
//
//					for (CommentInObject listItem : CommentList) {
//						if (txtMaincmt.getText().toString().equals(listItem.getDescription())) {
//
//							GlobalId = CommentId = listItem.getId();
//
//						}
//					}
//
//					// send to database
//
//					if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), CommentId, 1)) {
//
//						params = new LinkedHashMap<String, String>();
//						if (context != null) {
//
//							deleting = new Deleting(context);
//							deleting.delegate = ExpandIntroduction.this;
//
//							params.put("TableName", "LikeInCommentObject");
//
//							params.put("UserId", String.valueOf(Currentuser.getId()));
//
//							params.put("IsLike", String.valueOf(1));
//							params.put("CommentId", String.valueOf(CommentId));
//
//							deleting.execute(params);
//						}
//						ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);
//
//						ringProgressDialog.setCancelable(true);
//
//						new Thread(new Runnable() {
//
//							@Override
//							public void run() {
//
//								try {
//
//									Thread.sleep(10000);
//
//								} catch (Exception e) {
//
//								}
//							}
//						}).start();
//
//						// adapter.deleteLikeCommentBrandPage(CommentId,
//						// Currentuser.getId(), 1);
//						// countLike.setText(String.valueOf(adapter
//						// .NumberOfLikeOrDisLikeBrandPage(CommentId, 1)));
//						//
//						// imglikeComment
//						// .setImageResource(R.drawable.positive_off);
//						// Toast.makeText(context, "لایک پاک شد", 0).show();
//
//					} else {
//						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), CommentId, 0))
//							Toast.makeText(context, "شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
//									Toast.LENGTH_SHORT).show();
//						else {
//
//							params = new LinkedHashMap<String, String>();
//
//							if (context != null) {
//
//								saving = new Saving(context);
//								saving.delegate = ExpandIntroduction.this;
//
//								params.put("TableName", "LikeInCommentObject");
//
//								params.put("UserId", String.valueOf(Currentuser.getId()));
//
//								params.put("IsLike", String.valueOf(1));
//								params.put("CommentId", String.valueOf(CommentId));
//
//								params.put("IsUpdate", "0");
//								params.put("Id", "0");
//
//								saving.execute(params);
//							}
//							ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);
//
//							ringProgressDialog.setCancelable(true);
//
//							new Thread(new Runnable() {
//
//								@Override
//								public void run() {
//
//									try {
//
//										Thread.sleep(10000);
//
//									} catch (Exception e) {
//
//									}
//								}
//							}).start();
//
//							// adapter.InsertLikeCommentFromObject(id,
//							// Currentuser.getId(), 1, CommentId);
//							// countLike.setText(String.valueOf(adapter
//							// .NumberOfLikeOrDisLikeBrandPage(CommentId,
//							// 1)));
//							// imglikeComment
//							// .setImageResource(R.drawable.positive);
//							// Toast.makeText(context, "لایک اضافه شد",
//							// 0).show();
//						}
//
//					}
//
//				}
//			}
//		});
//
//		addreply.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View m) {
//
//				adapter.open();
//
//				if (Currentuser == null) {
//					Toast.makeText(context, "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
//					return;
//				} else {
//
//					RelativeLayout parentlayout = (RelativeLayout) m.getParent().getParent();
//					View view = parentlayout.findViewById(R.id.peygham);
//					TextView x = (TextView) view;
//					String item = x.getText().toString();
//					int commentid = 0;
//					// String dd = comment.getDescription();
//					for (CommentInObject test : CommentList) {
//						if (item.equals(test.getDescription())) {
//							commentid = test.getId();
//							break;
//						}
//					}
//					((IntroductionFragment) f).CommentId(commentid);
//					((IntroductionFragment) f).groupPosition(groupPosition);
//					util.ReplyLayout((Activity) context, mainComment.getText().toString(), true);
//
//					// DialogcmtInobject dialog = new DialogcmtInobject(f,
//					// context, R.layout.dialog_addcomment, ObjectID,
//					// commentid);
//					// dialog.show();
//
//					adapter.close();
//				}
//			}
//		});
//
//		reportComment.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				int id = (int) getGroupId(groupPosition);
//				CommentInObject co = (CommentInObject) getGroup(id);
//				if (co != null) {
//					itemId = co.getId();
//					userIdsender = co.getUserid();
//					content = co.getDescription();
//				}
//
//				if (util.getCurrentUser() != null) {
//
//					adapter.open();
//					int countReply = adapter.getCountOfReplyInObject(ObjectID, comment.getId());
//					adapter.close();
//
//					if (util.getCurrentUser().getId() == userIdsender) {
//
//						if (countReply == 0) {
//							menuItems = new ArrayList<String>();
//							menuItems.clear();
//							menuItems.add("کپی");
//							menuItems.add("حذف");
//						} else {
//
//							menuItems = new ArrayList<String>();
//							menuItems.clear();
//							menuItems.add("کپی");
//
//						}
//
//					} else {
//						menuItems = new ArrayList<String>();
//
//						menuItems.clear();
//						menuItems.add("کپی");
//						menuItems.add("گزارش تخلف");
//					}
//
//				} else {
//					menuItems = new ArrayList<String>();
//
//					menuItems.clear();
//					menuItems.add("کپی");
//				}
//
//				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
//				popupMenu.show();
//
//				OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {
//
//					@Override
//					public boolean onMenuItemClick(MenuItem item) {
//
//						if (item.getTitle().equals("کپی")) {
//
//							util.CopyToClipboard(content);
//
//						}
//						if (item.getTitle().equals("گزارش تخلف")) {
//
//							if (util.getCurrentUser() != null){
//								
//							}
////								util.reportAbuse(userIdsender, 7, itemId, content, ObjectID);
//							else
//								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
//						}
//						if (item.getTitle().equals("حذف")) {
//							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender)
//								deleteItems(itemId);
//							else {
//
//								Toast.makeText(context, "", 0).show();
//							}
//						}
//
//						return false;
//					}
//				};
//
//				popupMenu.setOnMenuItemClickListener(menuitem);
//				// int u = 0;
//				// int i = 0;
//				// String t = "";
//				//
//				// adapter.open();
//				//
//				// if (adapter.getCountOfReplyBrandPage(ObjectID,
//				// comment.getId()) > 0) {
//				// i = -1;
//				// int dd = (int) getGroupId(groupPosition);
//				// CommentInObject ww = (CommentInObject) getGroup(dd);
//				// if (ww != null) {
//				// u = ww.getUserid();
//				// t = ww.getDescription();
//				// }
//				// adapter.close();
//				//
//				// DialogLongClick dia = new DialogLongClick(context, 7, u, i,
//				// f, t);
//				// dia.show();
//				//
//				// } else {
//				//
//				// // برای پیدا کردن آی دی هر سطر از کد های این قسمت استفاده می
//				// // شود
//				//
//				// int d = (int) getGroupId(groupPosition);
//				// CommentInObject w = (CommentInObject) getGroup(d);
//				// if (w != null) {
//				// i = w.getId();
//				// u = w.getUserid();
//				// t = w.getDescription();
//				//
//				// }
//				//
//				// // //////////////////////////
//				//
//				// DialogLongClick dia = new DialogLongClick(context, 7, u, i,
//				// f, t);
//				// WindowManager.LayoutParams lp = new
//				// WindowManager.LayoutParams();
//				// lp.copyFrom(dia.getWindow().getAttributes());
//				// lp.width = (int) (util.getScreenwidth() / 1.5);
//				// lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
//				// ;
//				// dia.show();
//				//
//				// dia.getWindow().setAttributes(lp);
//				// dia.getWindow().setBackgroundDrawable(
//				// new ColorDrawable(
//				// android.graphics.Color.TRANSPARENT));
//				// }
//			}
//		});
//
//		convertView.setOnClickListener(new View.OnClickListener() {
//
//			@Override
//			public void onClick(View arg0) {
//				if (isExpanded) {
//					mExpandableListView.collapseGroup(groupPosition);
//					notifyDataSetChanged();
//
//				} else
//					mExpandableListView.expandGroup(groupPosition);
//				notifyDataSetChanged();
//
//			}
//		});
//		mainComment.setTypeface(null, Typeface.BOLD);
//
//		return convertView;
//	}
//
//	@Override
//	public boolean hasStableIds() {
//		return true;
//	}
//
//	@Override
//	public boolean isChildSelectable(int groupPosition, int childPosition) {
//		return true;
//	}
//
//	@Override
//	public void processFinish(String output) {
//
//		if (IsDeleteing == true) {
//
//			adapter.open();
//			adapter.deleteOnlyCommentObject(itemId);
//			adapter.close();
//
//			if (ringProgressDialog != null) {
//				ringProgressDialog.dismiss();
//			}
//			// ((IntroductionFragment) f).updateList();
//		} else {
//
//			if (!"".equals(output) && output != null && !(output.contains("Exception") || output.contains("java"))) {
//				int id = -1;
//				try {
//					id = Integer.valueOf(output);
//
//					adapter.open();
//					if (flag) {
//
//						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), GlobalId, 1)) {
//
//							adapter.deleteLikeCommentBrandPage(GlobalId, Currentuser.getId(), 1);
//							notifyDataSetChanged();
//							if (ringProgressDialog != null) {
//								ringProgressDialog.dismiss();
//
//							}
//						} else {
//
//							adapter.InsertLikeCommentFromObject(id, Currentuser.getId(), 1, GlobalId);
//							notifyDataSetChanged();
//							if (ringProgressDialog != null) {
//								ringProgressDialog.dismiss();
//
//							}
//
//						}
//
//					} else {
//						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), GlobalId, 0)) {
//
//							adapter.deleteLikeCommentBrandPage(GlobalId, Currentuser.getId(), 0);
//							notifyDataSetChanged();
//							if (ringProgressDialog != null) {
//								ringProgressDialog.dismiss();
//
//							}
//						} else {
//							adapter.InsertLikeCommentFromObject(id, Currentuser.getId(), 0, GlobalId);
//							notifyDataSetChanged();
//							if (ringProgressDialog != null) {
//								ringProgressDialog.dismiss();
//
//							}
//						}
//
//					}
//				} catch (Exception e) {
//					Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
//				}
//			}
//		}
//
//	}
//
//	public void deleteItems(int itemId) {
//
//		params = new LinkedHashMap<String, String>();
//
//		deleting = new Deleting(context);
//		deleting.delegate = ExpandIntroduction.this;
//
//		params.put("TableName", "CommentInObject");
//		params.put("Id", String.valueOf(itemId));
//
//		deleting.execute(params);
//
//		ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);
//
//		ringProgressDialog.setCancelable(true);
//		new Thread(new Runnable() {
//
//			@Override
//			public void run() {
//
//				try {
//
//					Thread.sleep(10000);
//
//				} catch (Exception e) {
//
//				}
//			}
//		}).start();
//
//		IsDeleteing = true;
//
//	}
//
//}

package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.PopupMenu.OnMenuItemClickListener;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.entity.SubAdmin;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.FixedPostFragment;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

public class ExpandIntroduction extends BaseExpandableListAdapter
		implements AsyncInterface/* , CommInterface */ {

	Context context;
	private Map<CommentInObject, List<CommentInObject>> mapCollection;
	private ArrayList<CommentInObject> cmt;
	DataBaseAdapter adapter;
	Utility util;
	Fragment f;
	int objectId, userid, GlobalId, userId, iid;
	Users Currentuser, uu;;
	boolean flag;
	int lastExpandedPosition = 0;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	TextView countLike, countdisLike;
	ImageView reportComment, reportReply;
	String serverDate = "";
	ServerDate date;
	CommentInObject reply;
	CommentInObject comment;
	List<String> menuItems;
	int itemId, userIdsender;
	String description;
	boolean IsDeleteing;
	com.project.mechanic.entity.Object object;
	String tagClicked = "";
	int posGroup, posChild = -1;

	boolean commentOrReply; // true >> comment false >> reply

	public ExpandIntroduction(Context context, ArrayList<CommentInObject> laptops,
			Map<CommentInObject, List<CommentInObject>> mapCollection, Fragment f, int objectId) {
		this.context = context;
		this.mapCollection = mapCollection;
		this.cmt = laptops;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.f = f;
		this.objectId = objectId;
		Currentuser = util.getCurrentUser();

	}

	public Object getChild(int groupPosition, int childPosition) {
		return mapCollection.get(cmt.get(groupPosition)).get(childPosition);
	}

	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		reply = (CommentInObject) getChild(groupPosition, childPosition);
		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.row_child_item, null);
		}

		TextViewEx mainReply = (TextViewEx) convertView.findViewById(R.id.reply_txt_child);

		TextView dateReply = (TextView) convertView.findViewById(R.id.date_replyed);
		TextView nameReplyer = (TextView) convertView.findViewById(R.id.name_replyed);

		ImageButton ReplyerPic = (ImageButton) convertView.findViewById(R.id.icon_reply_comment);

		reportReply = (ImageView) convertView.findViewById(R.id.reportImagereply);
		adapter.open();

		// final CommentInFroum comment = cmt.get(groupPosition);
		Users y = adapter.getUserbyid(reply.getUserid());
		userId = y.getId();
		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.main_icon_reply);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// lp.setMargins(5, 5, 5, 5);
		ReplyerPic.setLayoutParams(lp);

		if (y.getImagePath() == null) {

			ReplyerPic.setBackgroundResource(R.drawable.circle_drawable);

			ReplyerPic.setImageResource(R.drawable.no_img_profile);
			ReplyerPic.setLayoutParams(lp);

		} else {

			// byte[] byteImageProfile = y.getImage();

			Bitmap bmp = BitmapFactory.decodeFile(y.getImagePath());

			// Bitmap bmp = BitmapFactory.decodeByteArray(byteImageProfile, 0,
			// byteImageProfile.length);

			ReplyerPic.setImageBitmap(Utility.getclip(bmp));

			ReplyerPic.setLayoutParams(lp);

		}

		reportReply.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				int d = (int) getGroupId(groupPosition);
				final CommentInObject w = (CommentInObject) getChild(d, childPosition);
				if (w != null) {
					itemId = w.getId();
					userIdsender = w.getUserid();
					description = w.getDescription();

				}

				if (util.getCurrentUser() != null) {

					if (util.getCurrentUser().getId() == userIdsender || isAdmin(Currentuser.getId())) {

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

								util.reportAbuse(userIdsender, StaticValues.TypeReportReplyFroum, itemId, description,
										w.getObjectid(), childPosition , -1);
							else
								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
						}
						if (item.getTitle().equals("حذف")) {
							posGroup = groupPosition;
							posChild = childPosition;
							deleteItems(itemId);
							commentOrReply = false;

						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

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
		mainReply.setText(reply.getDescription(), true);
		dateReply.setText(util.getPersianDate(reply.getDatetime()));
		mainReply.setTypeface(util.SetFontIranSans());
		adapter.open();
		object = adapter.getObjectbyid(objectId);
		adapter.close();

		if (isAdmin(reply.getUserid()) == true) {

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
		}

		// nameReplyer.setText(y.getName());
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

	public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, ViewGroup parent) {

		adapter.open();
		if (groupPosition <= cmt.size())
			comment = cmt.get(groupPosition);
		final Users x = adapter.getUserbyid(comment.getUserid());
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

		LinearLayout addreply = (LinearLayout) convertView.findViewById(R.id.addCommentToTopic);

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
			if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), comment.getId(), 1)) {
				imglikeComment.setBackgroundResource((R.drawable.positive));
			} else {
				imglikeComment.setBackgroundResource((R.drawable.positive_off));

			}
			if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), comment.getId(), 0)) {
				imgdislikeComment.setBackgroundResource((R.drawable.negative));
			} else {
				imgdislikeComment.setBackgroundResource((R.drawable.negative_off));

			}

		}

		////////////////////////////////////////////////

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.icon_header_comment_froum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageCommentAndReply);

		adapter.open();
		object = adapter.getObjectbyid(objectId);
		adapter.close();

		if (isAdmin(comment.getUserid()) == true) {

			nameCommenter.setText(object.getName());

			if (object.getImagePath2() != null) {

				try {
					Bitmap bmp = BitmapFactory.decodeFile(object.getImagePath2());

					profileImage.setImageBitmap(Utility.getclip(bmp));

					profileImage.setLayoutParams(lp);
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
				profileImage.setImageResource(R.drawable.no_img_profile);
				profileImage.setLayoutParams(lp);

			} else {

				// byte[] byteImageProfile = y.getImage();

				try {

					Bitmap bmp = BitmapFactory.decodeFile(x.getImagePath());

					profileImage.setImageBitmap(Utility.getclip(bmp));

					profileImage.setLayoutParams(lp);

				} catch (OutOfMemoryError e) {
					e.printStackTrace();
				}

			}
		}

		///////////////////

		mainComment.setText(comment.getDescription() , true);
		dateCommenter.setText(util.getPersianDate(comment.getDatetime()));
		// if (adapter.getCountOfReplyInFroum(froumID, comment.getId()) == 0) {
		// LinearLayout lrr = (LinearLayout) convertView
		// .findViewById(R.id.linearShowcountofRepply);
		// lrr.setVisibility(View.GONE);
		//
		// } else
		adapter.open();
		countOfReply.setText(adapter.getCountOfReplyBrandPage(objectId, comment.getId()).toString());
		adapter.close();
		// if (x != null) {
		// Bitmap bmp = null;
		// nameCommenter.setText(x.getName());
		// if (x.getImagePath() == null) {
		//
		// // profileImage.setBackgroundResource(R.drawable.circle_drawable);
		// profileImage.setImageResource(R.drawable.no_img_profile);
		// } else {
		//
		// if (x.getImagePath() != null)
		// bmp = BitmapFactory.decodeFile(x.getImagePath());
		// if (bmp != null)
		//
		// profileImage.setImageBitmap(Utility.getclip(bmp));
		// }
		// }

		// lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		// lp.setMargins(5, 5, 10, 5);
		// profileImage.setLayoutParams(lp);
		profileImage.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				final CommentInObject comment = cmt.get(groupPosition);
				final Users x = adapter.getUserbyid(comment.getUserid());
				userId = x.getId();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				adapter.close();

			}
		});

		// end... this code for set image of profile
		int c = 0;

		for (CommentInObject listItem : cmt) {
			if (mainComment.getText().toString().equals(listItem.getDescription())) {

				c = listItem.getId();

			}
		}
		adapter.open();

		countLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(c, 1)));
		countdisLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(c, 0)));

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

					for (CommentInObject listItem : cmt) {
						if (txtMaincmt.getText().toString().equals(listItem.getDescription())) {

							GlobalId = id = listItem.getId();

						}
					}

					// send to database

					if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), id, 0)) {

						/*
						 * start >>>>> delete dislike from server
						 */

						params = new LinkedHashMap<String, String>();
						if (context != null) {

							deleting = new Deleting(context);
							deleting.delegate = ExpandIntroduction.this;

							params.put("TableName", "LikeInCommentObject");

							params.put("UserId", String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(0));
							params.put("CommentId", String.valueOf(id));

							deleting.execute(params);

						}

						/*
						 * end >>>>> delete dislike from server
						 */

					} else {
						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), id, 1)) {
							Toast.makeText(context, "شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						} else {

							/*
							 * start >>>>> save dislike to server
							 */
							if (context != null) {

								date = new ServerDate(context);
								date.delegate = ExpandIntroduction.this;
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
					View viewMaincmt = parentlayout.findViewById(R.id.peygham);
					TextView txtMaincmt = (TextView) viewMaincmt;

					View viewnumlike = parentlayout.findViewById(R.id.countCommentFroum);
					TextView txtlike = (TextView) viewnumlike;

					int cmtId = 0;

					for (CommentInObject listItem : cmt) {
						if (txtMaincmt.getText().toString().equals(listItem.getDescription())) {

							GlobalId = cmtId = listItem.getId();
						}
					}

					// send to database

					if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), cmtId, 1)) {
						/*
						 * start >>>>> delete like from server
						 */

						params = new LinkedHashMap<String, String>();

						if (context != null) {

							deleting = new Deleting(context);
							deleting.delegate = ExpandIntroduction.this;

							params.put("TableName", "LikeInCommentObject");

							params.put("UserId", String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(1));
							params.put("CommentId", String.valueOf(cmtId));

							deleting.execute(params);

						}

						/*
						 * end >>> delete like from server
						 */

					} else {

						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), cmtId, 0)) {
							Toast.makeText(context, "شما قبلا نظرتان را در این مورد این مطلب بیان کردید",
									Toast.LENGTH_SHORT).show();
						} else {

							/*
							 * start : save like to server
							 */

							// ///////////

							date = new ServerDate(context);
							date.delegate = ExpandIntroduction.this;
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
					LinearLayout parentlayout = (LinearLayout) m.getParent().getParent().getParent();
					View view = parentlayout.findViewById(R.id.peygham);
					TextView x = (TextView) view;
					String item = x.getText().toString();
					int commentid = 0;
					for (CommentInObject listItem : cmt) {
						if (item.equals(listItem.getDescription())) {

							commentid = listItem.getId();
						}
					}

					((FixedPostFragment) f).CommentId(commentid);
					((FixedPostFragment) f).groupPosition(groupPosition);
					((FixedPostFragment) f).childPosition(getChildrenCount(groupPosition));

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
			public void onClick(View v) {

				int d = (int) getGroupId(groupPosition);
				final CommentInObject w = (CommentInObject) getGroup(d);
				if (w != null) {
					itemId = w.getId();
					userIdsender = w.getUserid();
					description = w.getDescription();

				}

				if (util.getCurrentUser() != null) {

					// adapter.open();
					// int countReply = adapter.getCountOfReplyInFroum(objectId,
					// comment.getId());
					// adapter.close();

					if (util.getCurrentUser().getId() == userIdsender || isAdmin(Currentuser.getId())) {

						// if (countReply == 0) {
						menuItems = new ArrayList<String>();
						menuItems.clear();
						menuItems.add("کپی");
						menuItems.add("حذف");
						// } else {
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

								util.reportAbuse(userIdsender, StaticValues.TypeReportFixedPost, itemId, description,
										w.getObjectid(), groupPosition , -1);
							else
								Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
						}
						if (item.getTitle().equals("حذف")) {
							posGroup = groupPosition;

							deleteItems(itemId);
							commentOrReply = true;

						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

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
		if (IsDeleteing == true) {

			adapter.open();

			if (commentOrReply == true) {

				adapter.deleteOnlyCommentObject(itemId);
				adapter.deleteReplyCommentInObject(itemId);

			} else {
				adapter.deleteOnlyCommentObject(itemId);

			}
			adapter.close();

			((FixedPostFragment) f).expanding(posGroup, posChild, false);

		} else {
			if (!"".equals(output) && output != null && !(output.contains("Exception") || output.contains("java"))) {

				int id = -1;
				try {
					id = Integer.valueOf(output);

					adapter.open();

					if (flag) {

						/*
						 * save like in database device
						 */

						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), GlobalId, 1)) {
							adapter.deleteLikeCommentBrandPage(GlobalId, Currentuser.getId(), 1);

							notifyDataSetChanged();

						} else {
							adapter.InsertLikeCommentFromObject(id, Currentuser.getId(), 1, GlobalId, serverDate);

							notifyDataSetChanged();

						}

						countLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(id, 1)));
						countdisLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(id, 0)));
					} else {
						/*
						 * save dislike in database device
						 */

						if (adapter.isUserLikedCommentBrandPage(Currentuser.getId(), GlobalId, 0)) {
							adapter.deleteLikeCommentBrandPage(GlobalId, Currentuser.getId(), 0);
							notifyDataSetChanged();

						} else {
							adapter.InsertLikeCommentFromObject(id, Currentuser.getId(), 0, GlobalId, serverDate);

							notifyDataSetChanged();

						}

						countLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(id, 1)));
						countdisLike.setText(String.valueOf(adapter.NumberOfLikeOrDisLikeBrandPage(id, 0)));
					}

					adapter.close();

				} catch (NumberFormatException e) {
					serverDate = output;

					if (flag == true) {
						params = new LinkedHashMap<String, String>();
						if (context != null) {

							saving = new Saving(context);
							saving.delegate = ExpandIntroduction.this;

							params.put("TableName", "LikeInCommentObject");

							params.put("UserId", String.valueOf(Currentuser.getId()));
							params.put("IsLike", String.valueOf(1));
							params.put("CommentId", String.valueOf(GlobalId));
							params.put("ModifyDate", serverDate);
							params.put("IsUpdate", "0");
							params.put("Date", serverDate);

							params.put("Id", "0");

							saving.execute(params);

						}

						notifyDataSetChanged();

					} else {

						params = new LinkedHashMap<String, String>();

						if (context != null) {

							saving = new Saving(context);
							saving.delegate = ExpandIntroduction.this;

							params.put("TableName", "LikeInComment");

							params.put("UserId", String.valueOf(Currentuser.getId()));

							params.put("IsLike", String.valueOf(0));
							params.put("CommentId", String.valueOf(GlobalId));
							params.put("ModifyDate", serverDate);
							params.put("Date", serverDate);

							params.put("IsUpdate", "0");
							params.put("Id", "0");

							saving.execute(params);
						}

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
		deleting.delegate = ExpandIntroduction.this;

		params.put("TableName", "CommentInObject");
		params.put("Id", String.valueOf(itemId));

		deleting.execute(params);

		IsDeleteing = true;

	}

	private boolean isAdmin(int userId) {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = false;

		adapter.open();
		List<SubAdmin> subAdminList = adapter.getAdmin(objectId);
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
