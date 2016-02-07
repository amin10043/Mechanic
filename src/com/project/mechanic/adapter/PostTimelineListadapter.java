package com.project.mechanic.adapter;

import java.io.File;
import java.util.ArrayList;
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
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.PostTimeline;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogShowImage;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.fragment.PostFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

@SuppressLint("SimpleDateFormat")
public class PostTimelineListadapter extends ArrayAdapter<PostTimeline> implements AsyncInterface {

	Context context;
	List<PostTimeline> mylist;
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

	LinearLayout.LayoutParams LNImageShowParams;

	int itemId, userIdsender;
	List<String> menuItems = new ArrayList<String>();
	ImageView likeIcon;

	public PostTimelineListadapter(Context context, int resource, List<PostTimeline> objects, Fragment fragment) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fragment = fragment;
	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.raw_posttitle, parent, false);

		Parent = parent;
		// final TextView txt1 = (TextView) convertView
		// .findViewById(R.id.rowtitlepaper);
		final TextView txt2 = (TextView) convertView.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		TextView countcommentpost = (TextView) convertView.findViewById(R.id.countCommentInEveryTopic);
		TextView dateTopic = (TextView) convertView.findViewById(R.id.datetopicinFroum);
		TextView PostID = (TextView) convertView.findViewById(R.id.PostID);
		countLikePost = (TextView) convertView.findViewById(R.id.txtNumofLike_CmtFroum);
		ImageView profileImg = (ImageView) convertView.findViewById(R.id.iconfroumtitle);

		final ImageView postImage = (ImageView) convertView.findViewById(R.id.postImage);

		LinearLayout commenttitle = (LinearLayout) convertView.findViewById(R.id.l1cm);
		LikeTitle = (LinearLayout) convertView.findViewById(R.id.LikeTopicLinear);

		LinearLayout LinearImageShow = (LinearLayout) convertView.findViewById(R.id.LNImageShow);

		report = (ImageView) convertView.findViewById(R.id.reportImage);
		likeIcon = (ImageView) convertView.findViewById(R.id.likeIcon);

		PostTimeline person1 = mylist.get(position);

		// txt1.setTypeface(util.SetFontCasablanca());
		txt2.setTypeface(util.SetFontCasablanca());

		adapter.open();
		// Object x = adapter.getObjectbyid(person1.getObjectId());
		adapter.close();

		CurrentUser = util.getCurrentUser();

		if (person1.getPostseenBefore() > 0) {
			// txt1.setTextColor(Color.GRAY);
			txt2.setTextColor(Color.GRAY);
			txt3.setTextColor(Color.GRAY);
			dateTopic.setTextColor(Color.GRAY);

		}

		PostID.setText(person1.getPostId() + "");

		// if (!person1.getTitle().isEmpty()) {
		// txt1.setText(person1.getTitle());
		// txt1.setVisibility(View.VISIBLE);
		// }
		if (!person1.getPostDescription().isEmpty()) {
			if (person1.getPostDescription().length() > 100)
				txt2.setText(person1.getPostDescription().substring(0, 100) + " ...");
			else
				txt2.setText(person1.getPostDescription());
			txt2.setVisibility(View.VISIBLE);
		}
		
//		TextView des = (TextView)convertView.findViewById(R.id.des);

		
		
		if (!person1.getPostPhoto().isEmpty()) {
			

			File imgFile = new File(person1.getPostPhoto());

			if (imgFile.exists()) {

				LNImageShowParams = new LinearLayout.LayoutParams(LinearImageShow.getLayoutParams());
				LNImageShowParams.height = util.getScreenwidth();
				LNImageShowParams.width = util.getScreenwidth();
				 LNImageShowParams.setMargins(0, 10, 0, 10);
				postImage.setLayoutParams(LNImageShowParams);

				Bitmap myBitmap = BitmapFactory.decodeFile(person1.getPostPhoto());
				postImage.setImageBitmap(myBitmap);
				postImage.setVisibility(View.VISIBLE);
//				des.setVisibility(View.GONE);
//				txt2.setVisibility(View.VISIBLE);

			}
		}else{
			
//			des.setVisibility(View.VISIBLE);
//			des.setText(person1.getPostDescription());
//			txt2.setVisibility(View.GONE);


		}

		adapter.open();

		if (person1.getObjectName() != null)
			txt3.setText(person1.getObjectName());
		countcommentpost.setText(adapter.CommentInPost_count(person1.getPostUserId()).toString());

		countLikePost.setText(adapter.LikeInPost_count(postNumber).toString());
		adapter.close();

		dateTopic.setText(util.getPersianDate(person1.getPostDate()));

		String item = txt2.getText().toString();
		//
		// int sizeDescription = item.length();
		// String subItem;
		// subItem = item.subSequence(0, sizeDescription - 4).toString();

		int ItemId = 0;
		for (PostTimeline listItem : mylist) {
			if (item.equals(listItem.getPostDescription())) {
				postNumber = ItemId = listItem.getPostId();
			}
		}
		adapter.open();
		countLikePost.setText(adapter.LikeInPost_count(ItemId).toString());

		if (CurrentUser == null) {
			likeIcon.setBackgroundResource(R.drawable.like_froum_off);

		} else {
			if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
				likeIcon.setBackgroundResource(R.drawable.like_froum_on);
			} else
				likeIcon.setBackgroundResource(R.drawable.like_froum_off);
		}
		adapter.close();

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.topicTitleFroum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImagePostTimelineFragmentPage);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImagePostTimelineFragmentPage);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		if (person1 != null) {

			if (person1.getObjectImagePath2().isEmpty()) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);
			} else {
				// byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeFile(person1.getObjectImagePath2());
				profileImg.setImageBitmap(Utility.getRoundedCornerBitmap(bmp, 50));

				profileImg.setLayoutParams(lp);
			}
		}
		// ////////////////////////////////////
		profileImg.setOnClickListener(new View.OnClickListener() {

			public void onClick(View arg0) {
				adapter.open();
				PostTimeline person1 = mylist.get(position);
				Users x = adapter.getUserbyid(person1.getPostUserId());
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

		LikeTitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CurrentUser == null) {
					Toast.makeText(context, "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					LinearLayout parentlayout = (LinearLayout) v;
					LinearLayout parent = (LinearLayout) parentlayout.getParent().getParent();
					Integer id =  Integer.parseInt((String) parent.getTag());
					postNumber = id;
					date = new ServerDate(context);
					date.delegate = PostTimelineListadapter.this;
					date.execute("");
					ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
				}
			}

		});
		final SharedPreferences abc = context.getSharedPreferences("Id", 0);
		final SharedPreferences postId = context.getSharedPreferences("Id", 0);

		// report.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		//
		// if (CurrentUser == null) {
		// Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
		// } else {
		//
		// // int ItemId = 0;
		// // String t = "";
		// // ListView listView = (ListView) v.getParent().getParent()
		// // .getParent().getParent();
		// // int position = listView.getPositionForView(v);
		// // Post f = getItem(position);
		// // if (f != null) {
		// // ItemId = f.getUserId();
		// // t = f.getDescription();
		// // }
		// //
		// // DialogLongClick dia = new DialogLongClick(context, 1,
		// // ItemId, f.getId(), fragment, t);
		// // Toast.makeText(context, ItemId + "", 0).show();
		// //
		// // WindowManager.LayoutParams lp = new
		// // WindowManager.LayoutParams();
		// // lp.copyFrom(dia.getWindow().getAttributes());
		// // lp.width = (int) (util.getScreenwidth() / 1.5);
		// // lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		// //
		// // dia.show();
		// //
		// // dia.getWindow().setAttributes(lp);
		// // dia.getWindow().setBackgroundDrawable(
		// // new ColorDrawable(
		// // android.graphics.Color.TRANSPARENT));
		// }
		// }
		// });

		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// final int ItemId ;
				final String t;
				ListView listView = (ListView) v.getParent().getParent().getParent().getParent();
				int position = listView.getPositionForView(v);
				PostTimeline f = getItem(position - 1);
				if (f != null) {
					userIdsender = f.getPostUserId();
					t = f.getPostDescription();
					itemId = f.getPostUserId();

					if (util.getCurrentUser() != null) {
						if (util.getCurrentUser().getId() == userIdsender) {

							menuItems.clear();
							menuItems.add("ارسال پیام");
							menuItems.add("کپی");
							menuItems.add("حذف");

						} else {
							// menuItems = new ArrayList<String>();

							menuItems.clear();
							menuItems.add("ارسال پیام");
							menuItems.add("افزودن به علاقه مندی ها");
							menuItems.add("کپی");
							menuItems.add("گزارش تخلف");
						}
					} else {
						// menuItems = new ArrayList<String>();

						menuItems.clear();
						menuItems.add("کپی");
					}

					final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
					popupMenu.show();

					OnMenuItemClickListener menuitem = new OnMenuItemClickListener() {

						@Override
						public boolean onMenuItemClick(MenuItem item) {

							if (item.getTitle().equals("ارسال پیام")) {

								if (util.getCurrentUser() != null)
									util.sendMessage("Froum");
								else
									Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
							}

							if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
								adapter.open();
								addToFavorite(util.getCurrentUser().getId(), StaticValues.TypeFavoritePost, itemId);
								adapter.close();
							}
							if (item.getTitle().equals("کپی")) {

								util.CopyToClipboard(t);

							}
							if (item.getTitle().equals("گزارش تخلف")) {

								if (util.getCurrentUser() != null)
									util.reportAbuse(userIdsender, 1, itemId, t, 0);
								else
									Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
							}
							if (item.getTitle().equals("حذف")) {
								if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender) {
									// deleteItems(itemId);
								} else {

									Toast.makeText(context, "", 0).show();
								}
							}

							// switch (item.getItemId()) {
							// case 0: {
							// if (util.getCurrentUser() != null)
							// util.sendMessage("Froum");
							// else
							// Toast.makeText(context,
							// "ابتدا باید وارد شوید", 0)
							// .show();
							//
							// break;
							// }
							// case 1: {
							// if (util.getCurrentUser() != null)
							// util.addToFavorite(1, itemId);
							// else
							// Toast.makeText(context,
							// "ابتدا باید وارد شوید", 0)
							// .show();
							// break;
							// }
							// case 2: {
							//
							// util.CopyToClipboard(t);
							// break;
							// }
							// case 3: {
							// if (util.getCurrentUser() != null)
							// util.reportAbuse(itemId, 1,
							// userIdsender, t);
							// else
							// Toast.makeText(context,
							// "ابتدا باید وارد شوید", 0)
							// .show();
							// break;
							// }
							// case 4: {
							// if (util.getCurrentUser() != null
							// && util.getCurrentUser().getId() == userIdsender)
							// deleteItems(itemId);
							// else {
							//
							// Toast.makeText(context, "", 0).show();
							// break;
							// }
							// }
							// default:
							// break;
							// }

							return false;
						}
					};

					popupMenu.setOnMenuItemClickListener(menuitem);

					// }
					//
					// DialogLongClick dia = new DialogLongClick(context, 1,
					// ItemId, f.getId(), fragment, t);
					// Toast.makeText(context, ItemId + "", 0).show();
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
					// new ColorDrawable(
					// android.graphics.Color.TRANSPARENT));
					// }
				}
			}
		});

		convertView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// در تمام صفحات از این کد ها استفاده شود
				// int ItemId = 0;
				// ListView listView = (ListView) v.getParent();
				// int position = listView.getPositionForView(v);
				// Post f = getItem(position);
				// if (f != null) {
				// ItemId = f.getId();
				// }
				// تا اینجا

				TextView PostID = (TextView) v.findViewById(R.id.PostID);
				String TextPostID = PostID.getText().toString();
				int ItemId = Integer.parseInt(TextPostID);

				adapter.open();
				adapter.SetSeen("Post", ItemId, "1");
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				PostFragment fragment = new PostFragment();
				trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				/*
				 * bundle.putString("Id", String.valueOf(idPost));
				 * fragment.setArguments(bundle);
				 */

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 2).commit();

				// FragmentTransaction trans = ((MainActivity) context)
				// .getSupportFragmentManager().beginTransaction();
				// PostWithoutComment fragment = new PostWithoutComment();
				// trans.setCustomAnimations(R.anim.pull_in_left,
				// R.anim.push_out_right);
				// Bundle bundle = new Bundle();
				//
				// bundle.putString("Id", String.valueOf(ItemId));
				// fragment.setArguments(bundle);
				//
				// trans.replace(R.id.content_frame, fragment);
				// trans.commit();
				// abc.edit().putInt("main_Id", 1).commit();
				// abc.edit()
				// .putInt("Post_List_Id",
				// ((ListView) parent).getFirstVisiblePosition())
				// .commit();
				// postId.edit().putInt("main_Id", ItemId).commit();

			}
		});
		commenttitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// LinearLayout parentlayout = (LinearLayout) v;
				//
				// String item = txt2.getText().toString();
				// ;
				// int sizeDescription = item.length();
				// String subItem;
				// subItem = item.subSequence(0, sizeDescription -
				// 4).toString();
				// int ItemId = 0;
				// for (Post listItem : mylist) {
				// if (subItem.equals(listItem.getDescription())) {
				// // check authentication and authorization
				// ItemId = listItem.getId();
				// }
				// }

				TextView PostID = (TextView) v.findViewById(R.id.PostID);
				String TextPostID = PostID.getText().toString();
				int ItemId = Integer.parseInt(TextPostID);

				// int ItemId = 0;
				// ListView listView = (ListView) v.getParent().getParent()
				// .getParent();
				// int position = listView.getPositionForView(v);
				// Post f = getItem(position);
				// if (f != null) {
				// ItemId = f.getId();
				// }

				adapter.open();
				adapter.SetSeen("Post", ItemId, "1");
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				PostFragment fragment = new PostFragment();
				trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
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

		postImage.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {

				if (postImage.getDrawable() != null) {
					Bitmap bitmapHeader = ((BitmapDrawable) postImage.getDrawable()).getBitmap();
					byte[] ImageConvertedToByte = Utility.CompressBitmap(bitmapHeader);
					// Bitmap image=((BitmapDrawable)
					// postImage.getDrawable()).getBitmap();
					// Drawable myDrawable = postImage.getDrawable();
					DialogShowImage showImageDialog = new DialogShowImage(context, ImageConvertedToByte, "");
					showImageDialog.show();
				}
			}
		});

		convertView.setTag(person1.getPostId() + "");
		// Parent = convertView;
		
		TextView countVisit = (TextView)convertView.findViewById(R.id.countVisit);
		adapter.open();
		Post pos = adapter.getPostItembyid(person1.getPostId());
		countVisit.setText(pos.getCountView()+"");
		adapter.close();
		
		
		
		return convertView;
	}

	@Override
	public void processFinish(String output) {

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		try {
			int id = Integer.valueOf(output);
//			LinearLayout parentLayout = (LinearLayout) Parent.findViewWithTag(postNumber);
//			LinearLayout likeTitle = (LinearLayout) parentLayout.findViewById(R.id.liketitleTopic);

			adapter.open();
			if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
				adapter.deleteLikeFromPost(CurrentUser.getId(), postNumber);

				likeIcon.setBackgroundResource(R.drawable.like_froum_off);

			} else {
				adapter.insertLikeInPostToDb(id, CurrentUser.getId(), postNumber, serverDate, 0);

				likeIcon.setBackgroundResource(R.drawable.like_froum_on);
			}

//			TextView countLikePost = (TextView)v.findViewById(R.id.countLikeInFroumTitle);
			countLikePost.setText(adapter.LikeInPost_count(postNumber).toString());

			adapter.close();
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

		} catch (NumberFormatException ex) {
			if (output != null && !(output.contains("Exception") || output.contains("java"))) {
				adapter.open();
				if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(context);
					deleting.delegate = PostTimelineListadapter.this;

					params.put("TableName", "LikeInPost");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("PostId", String.valueOf(postNumber));

					deleting.execute(params);

					ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

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
					saving.delegate = PostTimelineListadapter.this;

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

					ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

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
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
			}
		}

		catch (Exception e) {

			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

	// public void deleteItems(int itemId) {
	//
	// ServiceComm service = new ServiceComm(context);
	// service.delegate = FroumtitleListadapter.this;
	// Map<String, String> items = new LinkedHashMap<String, String>();
	// items.put("DeletingRecord", "DeletingRecord");
	//
	// items.put("tableName", "Froum");
	// items.put("Id", String.valueOf(itemId));
	//
	// service.execute(items);
	//
	// ringProgressDialog = ProgressDialog.show(context, "",
	// "لطفا منتظر بمانید...", true);
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
	// }

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(context, " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}
}
