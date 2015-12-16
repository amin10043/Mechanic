package com.project.mechanic.adapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.fragment.InformationUser;
import com.project.mechanic.fragment.PaperFragment;
import com.project.mechanic.fragment.PersianDate;
import com.project.mechanic.fragment.TitlepaperFragment;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
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

public class PapertitleListAdapter extends ArrayAdapter<Paper> implements AsyncInterface, CommInterface {

	Context context;
	List<Paper> mylist;
	DataBaseAdapter adapter;
	private TextView NumofComment;
	private TextView NumofLike;
	private TextView DateView;
	int id;
	PersianDate p;
	Utility util;
	LinearLayout likePaper;
	Users currentUser;
	int userId;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	String serverDate = "";
	ServerDate date;
	int paperNumber;
	ProgressDialog ringProgressDialog;
	LinearLayout commentBtn;
	Fragment fr;
	ImageView report;
	List<String> menuItems;
	int itemId, userIdsender;

	public PapertitleListAdapter(Context context, int resource, List<Paper> objects, Fragment fr) {
		super(context, resource, objects);
		this.context = context;
		this.mylist = objects;
		adapter = new DataBaseAdapter(context);
		util = new Utility(context);
		p = new PersianDate();
		this.fr = fr;

	}

	@SuppressLint("ViewHolder")
	@Override
	public View getView(final int position, View convertView, final ViewGroup parent) {

		LayoutInflater myInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		convertView = myInflater.inflate(R.layout.raw_froumtitle, parent, false);

		// start find view

		final TextView txt1 = (TextView) convertView.findViewById(R.id.rowtitlepaper);
		final TextView txt2 = (TextView) convertView.findViewById(R.id.rowdescriptionpaper);
		TextView txt3 = (TextView) convertView.findViewById(R.id.authorname);
		NumofComment = (TextView) convertView.findViewById(R.id.countCommentInEveryTopic);
		NumofLike = (TextView) convertView.findViewById(R.id.countLikeInFroumTitle);
		DateView = (TextView) convertView.findViewById(R.id.datetopicinFroum);
		ImageView iconProile = (ImageView) convertView.findViewById(R.id.iconfroumtitle);

		likePaper = (LinearLayout) convertView.findViewById(R.id.liketitleTopic);

		commentBtn = (LinearLayout) convertView.findViewById(R.id.l1cm);
		report = (ImageView) convertView.findViewById(R.id.reportImage);

		// end find view
		adapter.open();
		currentUser = util.getCurrentUser();

		final Paper person1 = mylist.get(position);

		if (person1.getSeenBefore() > 0) {
			txt1.setTextColor(Color.GRAY);
			txt2.setTextColor(Color.GRAY);
			txt3.setTextColor(Color.GRAY);
			DateView.setTextColor(Color.GRAY);

		}
		
		

		Users x = adapter.getUserbyid(person1.getUserId());
		// userId=x.getId();
		RelativeLayout rl = (RelativeLayout) convertView.findViewById(R.id.topicTitleFroum);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / 3.8);
		lp.height = (int) (util.getScreenwidth() / 3.8);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(10, 10, 10, 10);

		if (x != null) {
			String ImagePath = x.getImagePath();
			if (ImagePath == null) {
				iconProile.setImageResource(R.drawable.no_img_profile);
				iconProile.setLayoutParams(lp);

			} else {

				// byte[] byteImg = x.getImage();
				Bitmap bmp = BitmapFactory.decodeFile(ImagePath);
				if (bmp != null)
					iconProile.setImageBitmap(Utility.getclip(bmp));

				iconProile.setLayoutParams(lp);
				adapter.close();
			}
			DateView.setText(util.getPersianDate(person1.getDate()));
			txt3.setText(x.getName());
		}
		adapter.close();
		iconProile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				Paper person1 = mylist.get(position);
				Users x = adapter.getUserbyid(person1.getUserId());
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
		txt1.setText(person1.getTitle());
		if (person1.getContext() != null && !"".equals((person1.getContext()))) {

//			if (person1.getContext().length() > 50)
//				txt2.setText(person1.getContext().substring(0, 50) + " ... ");
//			else
				txt2.setText(person1.getContext());

		} else
			txt2.setVisibility(View.GONE);
		txt1.setTypeface(util.SetFontCasablanca());
		txt2.setTypeface(util.SetFontCasablanca());
		
		
//		txt2.post(new Runnable() {
//			
//			@Override
//			public void run() {
//				int countline = txt2.getLineCount();
//		        
//				if (countline>1){
//					txt2.setText(person1.getContext());
//
//					//txt2.setMaxLines(1);
//					
//					
//				}
//
//					
//			}
//		});
		
			
		

		String item = txt1.getText().toString();
		for (Paper listItem : mylist) {
			if (item.equals(listItem.getTitle())) {
				paperNumber = listItem.getId();
			}
		}
		
		///////////////////
		
		

		adapter.open();

		if (currentUser == null) {
			likePaper.setBackgroundResource(R.drawable.like_froum_off);

		} else {
			if (adapter.isUserLikedPaper(currentUser.getId(), paperNumber)) {
				likePaper.setBackgroundResource(R.drawable.like_froum_on);
			} else
				likePaper.setBackgroundResource(R.drawable.like_froum_off);
		}

		NumofComment.setText(adapter.CommentInPaper_count(person1.getId()).toString());

		NumofLike.setText(adapter.LikeInPaper_count(person1.getId()).toString());
		adapter.close();

		final SharedPreferences abc = context.getSharedPreferences("Id", 0);
		likePaper.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (currentUser == null) {
					Toast.makeText(context, "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					String item = txt1.getText().toString();
					for (Paper listItem : mylist) {
						if (item.equals(listItem.getTitle())) {
							// check authentication and authorization
							paperNumber = listItem.getId();
						}
					}
					date = new ServerDate(context);
					date.delegate = PapertitleListAdapter.this;
					date.execute("");
				}

			}
		});
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final String t;
				ListView listView = (ListView) v.getParent().getParent().getParent();
				int position = listView.getPositionForView(v);
				Paper p = getItem(position);
				if (p != null) {
					userIdsender = p.getUserId();
					t = p.getContext();
					itemId = p.getId();

					if (util.getCurrentUser() != null) {
						if (util.getCurrentUser().getId() == userIdsender) {

							menuItems = new ArrayList<String>();
							menuItems.clear();
							menuItems.add("ارسال پیام");
							menuItems.add("کپی");
							menuItems.add("حذف");

						} else {
							menuItems = new ArrayList<String>();

							menuItems.clear();
							menuItems.add("ارسال پیام");
							menuItems.add("افزودن به علاقه مندی ها");
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

							if (item.getTitle().equals("ارسال پیام")) {

								if (util.getCurrentUser() != null)
									util.sendMessage("Paper");
								else
									Toast.makeText(context, "ابتدا باید وارد شوید", 0).show();
							}

							if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
								adapter.open();
								addToFavorite(util.getCurrentUser().getId(), 2, itemId);
								adapter.close();
							}
							if (item.getTitle().equals("کپی")) {

								util.CopyToClipboard(t);

							}
							if (item.getTitle().equals("گزارش تخلف")) {

								if (util.getCurrentUser() != null)
									util.reportAbuse(userIdsender, 2, itemId, t, 0);
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

							return false;
						}
					};

					popupMenu.setOnMenuItemClickListener(menuitem);

					// if (currentUser == null) {
					// // Toast.makeText(context, "ابتدا باید وارد شوید",
					// 0).show();
					// } else {
					//
					// int i = 0;
					// int us = 0;
					// String t = "";
					//
					// int d = (int) getItemId(position);
					// Paper w = getItem(d);
					// if (w != null) {
					// i = w.getId();
					// us = w.getUserId();
					// t = w.getContext();
					// }
					//
					// DialogLongClick dia = new DialogLongClick(context, 2, us,
					// i, fr, t);
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

				// LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();

				for (Paper listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);

				abc.edit().putInt("Froum_List_Id", ((ListView) parent).getFirstVisiblePosition()).commit();

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				adapter.open();
				adapter.SetSeen("Paper", id, "1");
				adapter.close();
				
				
				//////////////////////
//				Rect bounds = new Rect();
//				Paint paint = new Paint();
//				paint.getTextBounds(txt2.getText().toString(), 0, txt2.getText().toString().length(), bounds);
//
//				int width = (int) Math.ceil( bounds.width());
				
				
				

			}

		});
		commentBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// LinearLayout parentlayout = (LinearLayout) v;

				String item = txt1.getText().toString();

				for (Paper listItem : mylist) {
					if (item.equals(listItem.getTitle())) {
						// check authentication and authorization
						id = listItem.getId();
					}
				}
				Toast.makeText(context, "send = " + id, Toast.LENGTH_SHORT).show();

				FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(id));
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			}

		});

		return convertView;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(String output) {

		try {
			Integer.valueOf(output);
			adapter.open();
			if (adapter.isUserLikedPaper(currentUser.getId(), paperNumber)) {
				adapter.deleteLikeFromPaper(currentUser.getId(), paperNumber);
				likePaper.setBackgroundResource(R.drawable.like_froum_off);

				NumofLike.setText(adapter.LikeInPaper_count(paperNumber).toString());
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
			} else {
				adapter.insertLikeInPaperToDb(currentUser.getId(), paperNumber, serverDate);
				likePaper.setBackgroundResource(R.drawable.like_froum_on);
				NumofLike.setText(adapter.LikeInPaper_count(paperNumber).toString());
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
			}
			adapter.close();

		} catch (NumberFormatException e) {
			if (output != null && !(output.contains("Exception") || output.contains("java"))) {
				adapter.open();
				if (currentUser == null) {
					Toast.makeText(context, "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else if (adapter.isUserLikedPaper(currentUser.getId(), paperNumber)) {
					adapter.open();

					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(context);
					deleting.delegate = PapertitleListAdapter.this;

					params.put("TableName", "LikeInPaper");
					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("PaperId", String.valueOf(paperNumber));

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

					adapter.close();

				} else {
					adapter.open();
					params = new LinkedHashMap<String, String>();
					saving = new Saving(context);
					saving.delegate = PapertitleListAdapter.this;

					params.put("TableName", "LikeInPaper");

					params.put("UserId", String.valueOf(currentUser.getId()));
					params.put("PaperId", String.valueOf(paperNumber));
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

					adapter.close();
				}
				adapter.close();
			} else {
				Toast.makeText(context, "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
			}
		} catch (Exception e) {

			Toast.makeText(context, "خطا در ثبت", Toast.LENGTH_SHORT).show();
		}
	}

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(context, " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(context, "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}

	public void deleteItems(int itemId) {

		ServiceComm service = new ServiceComm(context);
		service.delegate = PapertitleListAdapter.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("DeletingRecord", "DeletingRecord");

		items.put("tableName", "Paper");
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

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		adapter.open();

		adapter.deletePaperTitle(itemId);
		adapter.deleteCommentPaper(itemId);

		adapter.close();

		TitlepaperFragment fr = new TitlepaperFragment();

		FragmentTransaction trans = ((MainActivity) context).getSupportFragmentManager().beginTransaction();

		trans.replace(R.id.content_frame, fr);
		trans.addToBackStack(null);
		trans.commit();
		fr.updateView(context);

		// ((PaperFragment) fr).updateView();
	}
}
