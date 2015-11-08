package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.PushNotification.DomainSend;
import com.project.mechanic.adapter.PaperListAdapter;
import com.project.mechanic.adapter.PapertitleListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class PaperWithoutComment extends Fragment implements AsyncInterface,
		CommInterface {

	DataBaseAdapter adapter;
	int paperID;
	LinearLayout addComment, likeTopic;
	TextView titletxt, descriptiontxt, dateTopic, countComment, nametxt,
			countLike, txtname;
	DialogcmtInPaper dialog;

	ListView lst;
	int userId;
	ArrayList<CommentInPaper> mylist;
	PaperListAdapter PaperListadapter;
	int like = 0;
	Utility util;
	Users CurrentUser;
	ImageView profileImg, sharebtn;
	// String currentDate;
	// PersianDate date;
	List<CommentInPaper> subList;
	List<CommentInPaper> tempList;
	PullAndLoadListView lstNews;

	String serverDate = "";
	ServerDate date;
	ProgressDialog ringProgressDialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	List<String> menuItems;

	RelativeLayout countLikeRelative, commentcounter;
	boolean LikeOrComment; // like == true & comment == false
	Paper p;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.froum_without_comment, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		titletxt = (TextView) view.findViewById(R.id.title_topic);
		descriptiontxt = (TextView) view.findViewById(R.id.description_topic);
		dateTopic = (TextView) view.findViewById(R.id.date_cc);

		profileImg = (ImageView) view.findViewById(R.id.iconfroumtitle);
		sharebtn = (ImageButton) view.findViewById(R.id.sharefroumicon);

		likeTopic = (LinearLayout) view.findViewById(R.id.LikeTopicLinear);

		addComment = (LinearLayout) view.findViewById(R.id.addCommentToTopic);

		countComment = (TextView) view.findViewById(R.id.numberOfCommentTopic);

		countLike = (TextView) view.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) view.findViewById(R.id.name_cc);

		countLikeRelative = (RelativeLayout) view
				.findViewById(R.id.countLiketext);
		commentcounter = (RelativeLayout) view.findViewById(R.id.countComment);
		txtname = (TextView) view.findViewById(R.id.name_cc);

		paperID = Integer.valueOf(getArguments().getString("Id"));
		CurrentUser = util.getCurrentUser();

		adapter.open();
		if (CurrentUser == null
				|| !adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
			likeTopic.setBackgroundResource(R.drawable.like_off);
			countLikeRelative.setBackgroundResource(R.drawable.count_like_off);
		} else {

			likeTopic.setBackgroundResource(R.drawable.like_on);
			countLikeRelative.setBackgroundResource(R.drawable.count_like);

		}

		countComment.setText(adapter.CommentInPaper_count(paperID).toString());
		countLike.setText(adapter.LikeInPaper_count(paperID).toString());

		mylist = adapter.getCommentInPaperbyPaperid(paperID);
		p = adapter.getPaperItembyid(paperID);
		Users u = adapter.getUserbyid(p.getUserId());

		if (u != null) {
			userId = u.getId();
			txtname.setText(u.getName());
			LinearLayout rl = (LinearLayout) view
					.findViewById(R.id.profileLinearcommenterinContinue);

			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					rl.getLayoutParams());

			lp.width = util.getScreenwidth() / 7;
			lp.height = util.getScreenwidth() / 7;
			lp.setMargins(5, 5, 5, 5);

			Bitmap bitmap;
			String ImagePath = u.getImagePath();
			if (ImagePath != null) {
				bitmap = BitmapFactory.decodeFile(ImagePath);
				if (bitmap != null) {
					profileImg.setImageBitmap(bitmap);
					profileImg.setLayoutParams(lp);

				}
			}

		}
		if (p != null) {
			titletxt.setText(p.getTitle());
			descriptiontxt.setText(p.getContext());
			dateTopic.setText(util.getPersianDate(p.getDate()));
		}

		if (util.getCurrentUser() != null) {
			if (util.getCurrentUser().getId() != paperID) {
				if (!util.isNetworkConnected()) {
					Toast.makeText(getActivity(), "Flse", Toast.LENGTH_SHORT)
							.show();
					adapter.open();
					adapter.insertVisitToDb(util.getCurrentUser().getId(), 2,
							paperID);
					adapter.close();
				} else if ((util.isNetworkConnected())) {
					Toast.makeText(getActivity(), "True", Toast.LENGTH_SHORT)
							.show();
					adapter.open();
					// ارسال اطلاعات به جدول ویزیت سرور
					// ارسال اطلاعات از جدول ویزیت گوشی به جدول ویزیت سرور
					adapter.deleteVisit();
					adapter.close();
				}
			}
		}
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
		addComment.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// realizeIdPaper.edit().putInt("main_Id", 1378).commit();

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle b = new Bundle();
				b.putString("Id", String.valueOf(paperID));
				fragment.setArguments(b);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();

				// if (CurrentUser == null) {
				// Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
				// Toast.LENGTH_SHORT).show();
				// return;
				// } else {
				// dialog = new DialogcmtInPaper(PaperWithoutComment.this,
				// getActivity(), R.layout.dialog_addcomment, paperID,
				// 1);
				// dialog.show();
				//
				// }

			}
		});

		commentcounter.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(getActivity(), "send = " + paperID,
						Toast.LENGTH_SHORT).show();

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				Bundle bundle = new Bundle();
				bundle.putString("Id", String.valueOf(paperID));
				fragment.setArguments(bundle);

				DialogcmtInPaper dialog = new DialogcmtInPaper(null,
						getActivity(), R.layout.dialog_addcomment, paperID, 3);
				Bundle bundle2 = new Bundle();
				bundle.putString("Id", String.valueOf(paperID));
				fragment.setArguments(bundle);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
			}

		});
		countLikeRelative.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();
				ArrayList<LikeInPaper> likedist = adapter
						.getLikePaperByPaperId(paperID);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
							.show();
				} else {
					DialogPersonLikedPaper dia = new DialogPersonLikedPaper(
							getActivity(), paperID, likedist);
					dia.show();
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
						p.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT,
						p.getContext());
				startActivity(Intent.createChooser(sharingIntent,
						"اشتراک از طریق"));
			}
		});

		likeTopic.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;

				} else {

					date = new ServerDate(getActivity());
					date.delegate = PaperWithoutComment.this;
					date.execute("");
					LikeOrComment = true;

				}

			}
		});

		adapter.close();

		ImageView report = (ImageView) view.findViewById(R.id.reportImage);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (util.getCurrentUser() != null) {
					if (util.getCurrentUser().getId() == p.getUserId()) {

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

							util.sendMessage("Paper");

						}

						if (item.getTitle().equals("افزودن به علاقه مندی ها")) {
							adapter.open();
							addToFavorite(util.getCurrentUser().getId(), 2,
									p.getId());
							adapter.close();
						}
						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(p.getContext());

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							util.reportAbuse(p.getUserId(), 2, p.getId(),
									p.getContext(),0);

						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null
									&& util.getCurrentUser().getId() == p
											.getUserId())
								deleteItems(p.getId());
							else {

								Toast.makeText(getActivity(), "", 0).show();
							}
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

				// if (CurrentUser == null) {
				// Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0)
				// .show();
				// } else {
				//
				// DialogLongClick dia = new DialogLongClick(getActivity(), 2,
				// p.getUserId(), p.getId(), PaperWithoutComment.this,
				// p.getContext());
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

		});

		ImageView send = util.ShowFooterAgahi(getActivity(), true, 8);

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if ("".equals(util.inputComment(getActivity()))) {
					Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0)
							.show();
				} else {

					date = new ServerDate(getActivity());
					date.delegate = PaperWithoutComment.this;
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

	public void setCountComment() {
		adapter.open();
		countComment.setText(adapter.CommentInPaper_count(paperID).toString());

		adapter.close();

	}

	@Override
	public void processFinish(String output) {

		int id = -1;

		try {
			id = Integer.valueOf(output);

			if (LikeOrComment == true) {

				adapter.open();
				if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
					adapter.deleteLikeFromPaper(CurrentUser.getId(), paperID);
					likeTopic.setBackgroundResource(R.drawable.like_off);
					countLikeRelative
							.setBackgroundResource(R.drawable.count_like_off);

					countLike.setText(adapter.LikeInPaper_count(paperID)
							.toString());
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				} else {
					adapter.insertLikeInPaperToDb(CurrentUser.getId(), paperID,
							serverDate);
					likeTopic.setBackgroundResource(R.drawable.like_on);
					countLikeRelative
							.setBackgroundResource(R.drawable.count_like);

					countLike.setText(adapter.LikeInPaper_count(paperID)
							.toString());
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				}
				adapter.close();

			} else {

				adapter.open();
				adapter.insertCommentInPapertoDb(
						util.inputComment(getActivity()), paperID,
						CurrentUser.getId(), serverDate);
				adapter.close();

				util.ToEmptyComment(getActivity());

				final SharedPreferences realizeIdPaper = getActivity()
						.getSharedPreferences("Id", 0);

				realizeIdPaper.edit().putInt("main_Id", 1378).commit();

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				PaperFragment fragment = new PaperFragment();
				trans.setCustomAnimations(R.anim.pull_in_left,
						R.anim.push_out_right);
				Bundle b = new Bundle();
				b.putString("Id", String.valueOf(paperID));
				fragment.setArguments(b);

				trans.replace(R.id.content_frame, fragment);
				trans.commit();
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}

			}

		} catch (NumberFormatException e) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {

				if (LikeOrComment == true) {

					adapter.open();
					if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
						adapter.open();
						// int c = adapter.LikeInFroum_count(ItemId) - 1;
						// countLikeFroum.setText(String.valueOf(c));

						params = new LinkedHashMap<String, String>();
						deleting = new Deleting(getActivity());
						deleting.delegate = PaperWithoutComment.this;

						params.put("TableName", "LikeInPaper");
						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("PaperId", String.valueOf(paperID));

						deleting.execute(params);

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

						adapter.close();

					} else {
						adapter.open();
						params = new LinkedHashMap<String, String>();
						saving = new Saving(getActivity());
						saving.delegate = PaperWithoutComment.this;

						params.put("TableName", "LikeInPaper");

						params.put("UserId",
								String.valueOf(CurrentUser.getId()));
						params.put("PaperId", String.valueOf(paperID));
						params.put("Date", output);
						params.put("IsUpdate", "0");
						params.put("Id", "0");

						serverDate = output;

						saving.execute(params);

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

						// countLikeFroum.setText(adapter
						// .LikeInFroum_count(ItemId).toString());

						adapter.close();
					}
					adapter.close();
				} else {

					params = new LinkedHashMap<String, String>();
					saving = new Saving(getActivity());
					saving.delegate = PaperWithoutComment.this;

					params.put("TableName", "CommentInPaper");

					params.put("Desk", util.inputComment(getActivity()));
					params.put("PaperId", String.valueOf(paperID));
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("IsUpdate", "0");
					params.put("Date", output);
					params.put("ModifyDate", output);

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

				}

			} else {
				Toast.makeText(getActivity(),
						"خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT)
						.show();
			}
		} catch (Exception e) {

			Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT)
					.show();
		}
	}

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(getActivity(),
					" قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(getActivity(), "به لیست علاقه مندی ها اضافه شد ", 0)
					.show();
		}
	}

	public void deleteItems(int itemId) {

		ServiceComm service = new ServiceComm(getActivity());
		service.delegate = PaperWithoutComment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("DeletingRecord", "DeletingRecord");

		items.put("tableName", "Paper");
		items.put("Id", String.valueOf(itemId));

		service.execute(items);

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

	}

	@Override
	public void CommProcessFinish(String output) {

		if (ringProgressDialog != null) {
			ringProgressDialog.dismiss();
		}
		adapter.open();

		adapter.deletePaperTitle(p.getId());
		adapter.deleteCommentPaper(p.getId());

		adapter.close();

		TitlepaperFragment fr = new TitlepaperFragment();

		FragmentTransaction trans = ((MainActivity) getActivity())
				.getSupportFragmentManager().beginTransaction();

		trans.replace(R.id.content_frame, fr);
		trans.addToBackStack(null);
		trans.commit();

	}
}
