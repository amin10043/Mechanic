package com.project.mechanic.adapter;

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
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.FroumFragment;
import com.project.mechanic.fragment.FroumWithoutComment;
import com.project.mechanic.fragment.FroumtitleFragment;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

@SuppressLint("SimpleDateFormat")
public class FroumtitleListadapter extends ArrayAdapter<Froum> implements AsyncInterface, CommInterface {

	Context context;
	List<Froum> mylist;
	DataBaseAdapter adapter;
	Utility util;
	Users CurrentUser;
	// PersianDate todayDate;
	// String currentDate;
	LinearLayout LikeTitle;
	// int ItemId;
	int froumNumber;
	TextView countLikeFroum;
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
	int itemId, userIdsender;
	List<String> menuItems = new ArrayList<String>();;

	public FroumtitleListadapter(Context context, int resource, List<Froum> objects, Fragment fragment) {
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

		convertView = myInflater.inflate(R.layout.raw_froumtitle, parent, false);

		Parent = parent;
		final TextView txt1 = (TextView) convertView.findViewById(R.id.rowtitlepaper);
		final TextView txt2 = (TextView) convertView.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		TextView countcommentfroum = (TextView) convertView.findViewById(R.id.countCommentInEveryTopic);
		TextView dateTopic = (TextView) convertView.findViewById(R.id.datetopicinFroum);
		countLikeFroum = (TextView) convertView.findViewById(R.id.countLikeInFroumTitle);
		ImageView profileImg = (ImageView) convertView.findViewById(R.id.iconfroumtitle);
		LinearLayout commenttitle = (LinearLayout) convertView.findViewById(R.id.l1cm);
		LikeTitle = (LinearLayout) convertView.findViewById(R.id.liketitleTopic);

		report = (ImageView) convertView.findViewById(R.id.reportImage);

		Froum person1 = mylist.get(position);

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
		txt1.setText(person1.getTitle());
		if (person1.getDescription() != null && !"".equals(person1.getDescription())){
//			if (person1.getDescription().length() > 50)
//				txt2.setText(person1.getDescription().substring(0, 50) + " ...");
//			else
				txt2.setText(person1.getDescription());
		}else
		{
			txt2.setVisibility(View.GONE);
		}
		adapter.open();

		if (x != null)
			txt3.setText(x.getName());
		countcommentfroum.setText(adapter.CommentInFroum_count(person1.getId()).toString());
		countLikeFroum.setText(adapter.LikeInFroum_count(person1.getId()).toString());
		adapter.close();

		dateTopic.setText(util.getPersianDate(person1.getDate()));

		String item = txt2.getText().toString();

		// int sizeDescription = item.length();
		// String subItem;
		// subItem = item.subSequence(0, sizeDescription - 4).toString();

		int ItemId = 0;
		for (Froum listItem : mylist) {
			if (item.equals(listItem.getDescription())) {
				froumNumber = ItemId = listItem.getId();
			}
		}
		adapter.open();

		countLikeFroum.setText(adapter.LikeInFroum_count(ItemId).toString());

		if (CurrentUser == null) {
			LikeTitle.setBackgroundResource(R.drawable.like_froum_off);

		} else {
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumNumber)) {
				LikeTitle.setBackgroundResource(R.drawable.like_froum_on);
			} else
				LikeTitle.setBackgroundResource(R.drawable.like_froum_off);
		}
		adapter.close();

		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.topicTitleFroum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / 3.8);
		lp.height = (int) (util.getScreenwidth() / 3.8);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.addRule(RelativeLayout.CENTER_VERTICAL);
		lp.setMargins(10, 10, 10, 10);

		if (x != null) {

			if (x.getImagePath() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);

				profileImg.setLayoutParams(lp);
			} else {

				// // byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeFile(x.getImagePath());
				if (bmp != null)
					profileImg.setImageBitmap(Utility.getclip(bmp));

				profileImg.setLayoutParams(lp);
			}
		}
		// ////////////////////////////////////
		profileImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				Froum person1 = mylist.get(position);
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
					RelativeLayout parent = (RelativeLayout) parentlayout.getParent().getParent();
					int id = ((Integer) parent.getTag());
					froumNumber = id;
					date = new ServerDate(context);
					date.delegate = FroumtitleListadapter.this;
					date.execute("");
					ringProgressDialog = ProgressDialog.show(context, "", "لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
				}
			}

		});
		final SharedPreferences abc = context.getSharedPreferences("Id", 0);
		final SharedPreferences froumId = context.getSharedPreferences("Id", 0);

		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// final int ItemId ;
				final String t;
				ListView listView = (ListView) v.getParent().getParent().getParent();
				int position = listView.getPositionForView(v);
				Froum f = getItem(position);
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
									util.sendMessage("Froum");
								else
									Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
							}

							if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
								adapter.open();
								addToFavorite(util.getCurrentUser().getId(), 1, itemId);
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
								if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender)
									deleteItems(itemId);
								else {

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
				int ItemId = 0;
				int position = 0;
				ListView listView = (ListView) v.getParent();
				if (listView != null)
					position = listView.getPositionForView(v);
				Froum f = getItem(position);
				if (f != null) {
					ItemId = f.getId();
				}
				// تا اینجا

				adapter.open();
				adapter.SetSeen("Froum", ItemId, "1");
				adapter.close();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				FroumFragment fragment = new FroumFragment();
				trans.setCustomAnimations(R.anim.pull_in_left, R.anim.push_out_right);
				Bundle bundle = new Bundle();

				bundle.putString("Id", String.valueOf(ItemId));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				abc.edit().putInt("main_Id", 1).commit();
				abc.edit().putInt("Froum_List_Id", ((ListView) parent).getFirstVisiblePosition()).commit();
				froumId.edit().putInt("main_Id", ItemId).commit();

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
				for (Froum listItem : mylist) {
					if (subItem.equals(listItem.getDescription())) {
						// check authentication and authorization
						ItemId = listItem.getId();
					}
				}

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				FroumFragment fragment = new FroumFragment();
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
			LinearLayout parentLayout = (LinearLayout) Parent.findViewWithTag(froumNumber);
			LinearLayout likeTitle = (LinearLayout) parentLayout.findViewById(R.id.liketitleTopic);

			adapter.open();
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumNumber)) {
				adapter.deleteLikeFromFroum(CurrentUser.getId(), froumNumber);

				likeTitle.setBackgroundResource(R.drawable.like_froum_off);

			} else {
				adapter.insertLikeInFroumToDb(id, CurrentUser.getId(), froumNumber, serverDate, 0);

				likeTitle.setBackgroundResource(R.drawable.like_froum_on);
			}

			TextView likeCountFroum = (TextView) likeTitle.findViewById(R.id.countLikeInFroumTitle);
			likeCountFroum.setText(adapter.LikeInFroum_count(froumNumber).toString());

			adapter.close();
			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

		} catch (NumberFormatException ex) {
			if (output != null && !(output.contains("Exception") || output.contains("java"))) {
				adapter.open();
				if (adapter.isUserLikedFroum(CurrentUser.getId(), froumNumber)) {
					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(context);
					deleting.delegate = FroumtitleListadapter.this;

					params.put("TableName", "LikeInFroum");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(froumNumber));

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
					saving.delegate = FroumtitleListadapter.this;

					params.put("TableName", "LikeInFroum");

					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("FroumId", String.valueOf(froumNumber));
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

	public void deleteItems(int itemId) {

		ServiceComm service = new ServiceComm(context);
		service.delegate = FroumtitleListadapter.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("DeletingRecord", "DeletingRecord");

		items.put("tableName", "Froum");
		items.put("Id", String.valueOf(itemId));

		service.execute(items);

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
	}

	@Override
	public void CommProcessFinish(String output) {

		adapter.open();

		adapter.deleteFroumTitle(itemId);
		adapter.deleteCommentFroum(itemId);
		adapter.deleteLikeFroum(itemId);

		adapter.close();

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}

		((FroumtitleFragment) fragment).updateView();

	}

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(context, " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}
}
