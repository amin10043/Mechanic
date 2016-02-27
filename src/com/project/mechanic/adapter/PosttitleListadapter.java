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
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.DialogShowImage;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.fragment.IntroductionFragment;
import com.project.mechanic.fragment.PostFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.interfaceServer.DateFromServerForLike;
import com.project.mechanic.interfaceServer.DeleteLikeFromServer;
import com.project.mechanic.interfaceServer.LikeFromServer;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.DeletingLike;
import com.project.mechanic.server.SavingLike;
import com.project.mechanic.server.ServerDateForLike;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.utility.Utility;

@SuppressLint("SimpleDateFormat")
public class PosttitleListadapter extends ArrayAdapter<Post> implements DateFromServerForLike, DeleteLikeFromServer,
		LikeFromServer/* AsyncInterface, CommInterface */ {

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
	// ProgressDialog ringProgressDialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	String serverDate = "";
	ServerDate date;
	int userId;
	Fragment fragment;
	ImageView report;
	View Parent;
	LinearLayout parentLike;
	LinearLayout.LayoutParams LNImageShowParams;

	int itemId, userIdsender;
	List<String> menuItems = new ArrayList<String>();

	ImageView likeIcon;
	int positionBrand;

	@Override
	public int getCount() {

		return mylist.size();
	}

	public PosttitleListadapter(Context context, int resource, List<Post> objects, Fragment fragment,
			int positionBrand) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		this.fragment = fragment;
		this.positionBrand = positionBrand;
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

		TextView countVisit = (TextView) convertView.findViewById(R.id.countVisit);

		Post person1 = mylist.get(position);
		convertView.setTag(person1.getId());

		// txt1.setTypeface(util.SetFontCasablanca());
		txt2.setTypeface(util.SetFontCasablanca());

		adapter.open();
		Users x = adapter.getUserbyid(person1.getUserId());
		adapter.close();
		countVisit.setText(person1.getCountView() + "");

		CurrentUser = util.getCurrentUser();

		if (person1.getSeenBefore() > 0) {
			// txt1.setTextColor(Color.GRAY);
			txt2.setTextColor(Color.GRAY);
			txt3.setTextColor(Color.GRAY);
			dateTopic.setTextColor(Color.GRAY);

		}

		adapter.open();
		if (CurrentUser != null)
			if (adapter.isUserLikedPost(CurrentUser.getId(), person1.getId())) {

				likeIcon.setBackgroundResource(R.drawable.like_froum_off);

			} else {

				likeIcon.setBackgroundResource(R.drawable.like_froum_on);
			}
		adapter.close();

		PostID.setText(person1.getId() + "");

		// if (!person1.getTitle().isEmpty()) {
		// txt1.setText(person1.getTitle());
		// txt1.setVisibility(View.VISIBLE);
		// }
		if (!person1.getDescription().isEmpty()) {
			if (person1.getDescription().length() > 100)
				txt2.setText(person1.getDescription().substring(0, 100) + "...");
			else
				txt2.setText(person1.getDescription());
			txt2.setVisibility(View.VISIBLE);
		}

		if (person1.getPhoto() != null) {

			File imgFile = new File(person1.getPhoto());

			if (imgFile.exists()) {

				LNImageShowParams = new LinearLayout.LayoutParams(LinearImageShow.getLayoutParams());
				LNImageShowParams.height = util.getScreenwidth();
				LNImageShowParams.width = util.getScreenwidth();
				LNImageShowParams.setMargins(0, 0, 0, 10);
				postImage.setLayoutParams(LNImageShowParams);

				Bitmap myBitmap = BitmapFactory.decodeFile(person1.getPhoto());
				postImage.setImageBitmap(myBitmap);
				postImage.setVisibility(View.VISIBLE);
			}
		}

		TextView idposttxt = (TextView) convertView.findViewById(R.id.PostID);
		String pId = idposttxt.getText().toString();

		int piid = Integer.parseInt(pId);

		adapter.open();
		final Post po = adapter.getPostItembyid(piid);
		Object obj = adapter.getObjectbyid(po.getObjectId());

//		if (x != null)
			txt3.setText(obj.getName());
		countcommentpost.setText(adapter.CommentInPost_count(person1.getId()).toString());
		countLikePost.setText(adapter.LikeInPost_count(person1.getId()).toString());
		adapter.close();

		dateTopic.setText(util.getPersianDate(person1.getDate()));

		String item = txt2.getText().toString();
		//
		// int sizeDescription = item.length();
		// String subItem;
		// subItem = item.subSequence(0, sizeDescription - 4).toString();

		int ItemId = 0;
		for (Post listItem : mylist) {
			if (item.equals(listItem.getDescription())) {
				postNumber = ItemId = listItem.getId();
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
		if (obj != null) {

			if (obj.getImagePath2() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);
			} else {
				// byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeFile(obj.getImagePath2());
				profileImg.setImageBitmap(Utility.getclip(bmp));

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
					parentLike = (LinearLayout) parentlayout.getParent().getParent();
					int id = ((Integer) parentLike.getTag());

					postNumber = id;
					ServerDateForLike date = new ServerDateForLike(context);
					date.delegate = PosttitleListadapter.this;
					date.execute("");

					adapter.open();

					ImageView li = (ImageView) parentLike.findViewById(R.id.likeIcon);
					TextView txt = (TextView) parentLike.findViewById(R.id.txtNumofLike_CmtFroum);
					int count = adapter.LikeInPost_count(postNumber);
					li.setBackgroundResource(R.drawable.like_froum_on);
					txt.setText(String.valueOf(count + 1));

					adapter.close();

					// ringProgressDialog = ProgressDialog.show(context, "",
					// "لطفا منتظر بمانید...", true);
					//
					// ringProgressDialog.setCancelable(true);
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
				Post f = getItem(position - 1);
				if (f != null) {
					userIdsender = f.getUserId();
					t = f.getDescription();
					itemId = f.getId();

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
									util.sendMessage("Post");
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

							return false;
						}
					};

					popupMenu.setOnMenuItemClickListener(menuitem);

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
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(ItemId));
				bundle.putInt("positionPost", position);
				bundle.putInt("positionBrand", positionBrand);
				bundle.putString("ObjectId", String.valueOf(po.getObjectId()));
				fragment.setArguments(bundle);

				/*
				 * bundle.putString("Id", String.valueOf(idPost));
				 * fragment.setArguments(bundle);
				 */
				trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack("IntroductionFragment");
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

				trans.replace(R.id.content_frame, fragment);
				trans.addToBackStack(null);
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

		// Parent = convertView;
		return convertView;
	}

	// @Override
	// public void processFinish(String output) {
	//
	// if (ringProgressDialog != null) {
	// ringProgressDialog.dismiss();
	// }
	// try {
	// int id = Integer.valueOf(output);
	// LinearLayout parentLayout = (LinearLayout)
	// Parent.findViewWithTag(postNumber);
	// ImageView likeIcon = (ImageView)
	// parentLayout.findViewById(R.id.likeIcon);
	//
	// adapter.open();
	// if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
	// adapter.deleteLikeFromPost(CurrentUser.getId(), postNumber);
	// likeIcon.setBackgroundResource(R.drawable.like_froum_off);
	//
	// } else {
	// adapter.insertLikeInPostToDb(id, CurrentUser.getId(), postNumber,
	// serverDate, 0);
	//
	// likeIcon.setBackgroundResource(R.drawable.like_froum_on);
	// }
	// adapter.close();
	//
	// TextView likeCountPost = (TextView)
	// parentLayout.findViewById(R.id.txtNumofLike_CmtFroum);
	//
	// adapter.open();
	// likeCountPost.setText(adapter.LikeInPost_count(postNumber).toString());
	// adapter.close();
	//
	// if (ringProgressDialog != null) {
	// ringProgressDialog.dismiss();
	// }
	//
	// } catch (NumberFormatException ex) {
	// if (output != null && !(output.contains("Exception") ||
	// output.contains("java"))) {
	// adapter.open();
	// if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {
	// params = new LinkedHashMap<String, String>();
	// deleting = new Deleting(context);
	// deleting.delegate = PosttitleListadapter.this;
	//
	// params.put("TableName", "LikeInPost");
	// params.put("UserId", String.valueOf(CurrentUser.getId()));
	// params.put("PostId", String.valueOf(postNumber));
	//
	// deleting.execute(params);
	//
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
	// } else {
	// params = new LinkedHashMap<String, String>();
	// saving = new Saving(context);
	// saving.delegate = PosttitleListadapter.this;
	//
	// params.put("TableName", "LikeInPost");
	//
	// params.put("UserId", String.valueOf(CurrentUser.getId()));
	// params.put("PostId", String.valueOf(postNumber));
	// params.put("CommentId", "0");
	// params.put("Date", output);
	// params.put("ModifyDate", output);
	// params.put("IsUpdate", "0");
	// params.put("Id", "0");
	//
	// serverDate = output;
	//
	// saving.execute(params);
	//
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
	//
	// // countLikeFroum.setText(adapter
	// // .LikeInFroum_count(ItemId).toString());
	// }
	// adapter.close();
	//
	// } else {
	// Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور",
	// Toast.LENGTH_SHORT).show();
	// }
	// }
	//
	// catch (
	//
	// Exception e)
	//
	// {
	//
	// Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
	// }
	//
	// }

	// public void deleteItems(int itemId) {
	//
	// ServiceComm service = new ServiceComm(context);
	// service.delegate = PosttitleListadapter.this;
	// Map<String, String> items = new LinkedHashMap<String, String>();
	// items.put("DeletingRecord", "DeletingRecord");
	//
	// items.put("tableName", "Post");
	// items.put("Id", String.valueOf(itemId));
	//
	// service.execute(items);
	//
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
	// }

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(context, " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}

	// @Override
	// public void CommProcessFinish(String output) {
	//
	// if (!output.contains("exception")) {
	//
	// if (ringProgressDialog != null) {
	// ringProgressDialog.dismiss();
	// }
	//
	// adapter.open();
	//
	// adapter.deletePostTitle(itemId);
	// adapter.deleteCommentPost(itemId);
	//
	// adapter.close();
	//
	// ((IntroductionFragment) fragment).fillListView();
	// ((IntroductionFragment) fragment).setCountPost();
	//
	// }
	//
	// }

	@Override
	public void resultDateLike(String output) {

		if (util.checkError(output) == false) {

			serverDate = output;

			adapter.open();

			if (adapter.isUserLikedPost(CurrentUser.getId(), postNumber)) {

				params = new LinkedHashMap<String, String>();
				DeletingLike deleting = new DeletingLike(context);
				deleting.delegate = PosttitleListadapter.this;

				params.put("TableName", "LikeInPost");
				params.put("UserId", String.valueOf(CurrentUser.getId()));
				params.put("PostId", String.valueOf(postNumber));

				deleting.execute(params);

			} else {

				params = new LinkedHashMap<String, String>();
				SavingLike saving = new SavingLike(context);
				saving.delegate = PosttitleListadapter.this;

				params.put("TableName", "LikeInPost");

				params.put("UserId", String.valueOf(CurrentUser.getId()));
				params.put("PostId", String.valueOf(postNumber));
				params.put("CommentId", "0");
				params.put("Date", serverDate);
				params.put("ModifyDate", serverDate);

				params.put("IsUpdate", "0");
				params.put("Id", "0");

				saving.execute(params);

			}

			adapter.close();

		} else {
			util.showErrorToast();

		}
	}

	@Override
	public void resultDeleteLike(String output) {

		if (util.checkError(output) == false) {

			int i = -1;

			try {
				i = Integer.valueOf(output);
				LinearLayout parentLayout = (LinearLayout) Parent.findViewWithTag(postNumber);
				ImageView likeIcon = (ImageView) parentLayout.findViewById(R.id.likeIcon);
				TextView likeCountPost = (TextView) parentLayout.findViewById(R.id.txtNumofLike_CmtFroum);
				adapter.open();

				adapter.deleteLikeFromPost(CurrentUser.getId(), postNumber);
				likeIcon.setBackgroundResource(R.drawable.like_froum_off);

				likeCountPost.setText(adapter.LikeInPost_count(postNumber).toString());

				adapter.close();

			} catch (Exception e) {
				util.showErrorToast();

			}

		} else {
			util.showErrorToast();

		}

	}

	@Override
	public void resultLike(String output) {

		if (util.checkError(output) == false) {

			int likeId = -1;

			try {
				LinearLayout parentLayout = (LinearLayout) ((View) parentLike.getParent()).findViewWithTag(postNumber);
				ImageView likeIcon = (ImageView) parentLayout.findViewById(R.id.likeIcon);
				TextView likeCountPost = (TextView) parentLayout.findViewById(R.id.txtNumofLike_CmtFroum);

				likeId = Integer.valueOf(output);
				adapter.open();

				adapter.insertLikeInPostToDb(likeId, CurrentUser.getId(), postNumber, serverDate, 0);
				likeIcon.setBackgroundResource(R.drawable.like_froum_on);
				likeCountPost.setText(adapter.LikeInPost_count(postNumber).toString());

				adapter.close();

			} catch (Exception e) {
				util.showErrorToast();
			}

		} else {
			util.showErrorToast();

		}

	}
}
