package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.ExpandableCommentFroum;
import com.project.mechanic.entity.CommentInFroum;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.LikeInFroum;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.inter.VisitSaveInterface;
import com.project.mechanic.interfaceServer.GetAllCommentInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetAllCommentById;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingVisit;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;
import com.project.mechanic.view.TextViewEx;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ir.noghteh.JustifiedTextView;

public class FroumFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, CommInterface, VisitSaveInterface, GetAllCommentInterface {

	DataBaseAdapter adapter;
	ExpandableCommentFroum exadapter;

	TextView titletxt, dateTopic, countComment, countLike, nametxt, countvisit;
	LinearLayout /* addComment, */ likeTopic;
	ImageButton sharebtn;
	ImageView profileImg, likeIcon;
	int froumid;
	// RelativeLayout count /* ,commentcounter */;

	Froum topics;
	TextViewEx descriptiontxt;
	DialogcmtInfroum dialog;
	ArrayList<CommentInFroum> commentGroup, ReplyGroup;
	// String currentDate;
	List<String> menuItems;

	Map<CommentInFroum, List<CommentInFroum>> mapCollection;
	ExpandableListView exlistview;

	View header;
	Users CurrentUser, uu;
	int IDcurrentUser, itemId;
	// PersianDate date;
	Utility util;
	int id, gp;
	Users user;
	int userId, commentId = 0;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	// ProgressDialog ringProgressDialog;

	String serverDate = "";
	ServerDate date;

	ArrayList<Integer> missedIds;
	boolean check = false;
	int controller = 0;
	int iid;

	ServiceComm service;
	UpdatingImage updating;
	Map<String, String> maps;
	boolean LikeOrComment; // like == true & comment == false
	boolean flag; // true == update picture & false == delete

	int counterVisit = 0;
	boolean isFinish = false, saveVisitFalg;
	String currentTime = "";
	int idItem, typeId, sender;
	List<Visit> visitList;
	int positionFroum;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceStdataate) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		View view = inflater.inflate(R.layout.fragment_froum, null);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		if (getArguments() != null)
			positionFroum = getArguments().getInt("PositionFroum");

		user = new Users();

		missedIds = new ArrayList<Integer>();

		header = getActivity().getLayoutInflater().inflate(R.layout.header_froum, null);

		// start find view

		titletxt = (TextView) header.findViewById(R.id.title_topic);
		descriptiontxt = (TextViewEx) header.findViewById(R.id.description_topic);

		dateTopic = (TextView) header.findViewById(R.id.date_cc);
		// TextView time = (TextView) header.findViewById(R.id.timetxt);

		countComment = (TextView) header.findViewById(R.id.numberOfCommentTopic);
		countLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		nametxt = (TextView) header.findViewById(R.id.name_cc);

		nametxt.setTypeface(util.SetFontIranSans());

		// addComment = (LinearLayout)
		// header.findViewById(R.id.addCommentToTopic);
		likeTopic = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);

		sharebtn = (ImageButton) header.findViewById(R.id.sharefroumicon);
		profileImg = (ImageView) header.findViewById(R.id.iconfroumtitle);
		exlistview = (ExpandableListView) view.findViewById(R.id.commentlist);

		likeIcon = (ImageView) header.findViewById(R.id.likeIcon);

		countvisit = (TextView) header.findViewById(R.id.countvisit);

		// count = (RelativeLayout) header.findViewById(R.id.countLike);
		// commentcounter = (RelativeLayout) header.findViewById(R.id.cmffff);

		// end find view

		if (getArguments().getString("Id") != null)
			froumid = Integer.valueOf(getArguments().getString("Id"));

		CurrentUser = util.getCurrentUser();
		if (CurrentUser == null) {

		}

		else
			IDcurrentUser = CurrentUser.getId();
		adapter.open();
		topics = adapter.getFroumItembyid(froumid);
		Users u = adapter.getUserbyid(topics.getUserId());
		adapter.close();

		if (u != null) {
			userId = u.getId();

			nametxt.setText(u.getName());
			RelativeLayout rl = (RelativeLayout) header.findViewById(R.id.imageLinear);

			RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(rl.getLayoutParams());

			lp.width = (int) (util.getScreenwidth() / StaticValues.RateImageFroumFragmentPage);
			lp.height = (int) (util.getScreenwidth() / StaticValues.RateImageFroumFragmentPage);
			lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
			// lp.gravity = Gravity.CENTER_HORIZONTAL;
			lp.setMargins(10, 10, 10, 10);
			profileImg.setLayoutParams(lp);

			if (u.getImagePath() == null) {
				profileImg.setImageResource(R.drawable.no_img_profile);
				profileImg.setLayoutParams(lp);

			} else {

				Bitmap bmp = BitmapFactory.decodeFile(u.getImagePath());
				profileImg.setImageBitmap(Utility.getclip(bmp));

				profileImg.setLayoutParams(lp);
			}
		}
		titletxt.setText(topics.getTitle());
		if (topics.getDescription() != null)
			descriptiontxt.setText(topics.getDescription(), true);
		adapter.open();

		countComment.setText(topics.getCountComment() + "");
		countLike.setText(topics.getCountLike() + "");

		adapter.close();

		String ddd = util.getPersianDate(topics.getDate());
		// List<String> dateTime = util.spilitDateTime(ddd);
		dateTopic.setText(ddd);
		// time.setText(dateTime.get(1));
		titletxt.setTypeface(util.SetFontIranSans());
		descriptiontxt.setTypeface(util.SetFontIranSans());
		// descriptiontxt.setPadding(5, 5, 5, 5);
		// descriptiontxt.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		// descriptiontxt.setLineSpacing(15);

		countvisit.setText(topics.getCountView() + "");
		// titletxt.setPadding(0, 20, 0, 0);
		checkInternet();
		profileImg.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
						.beginTransaction();
				InformationUser fragment = new InformationUser();
				Bundle bundle = new Bundle();
				bundle.putInt("userId", userId);
				fragment.setArguments(bundle);
				trans.replace(R.id.content_frame, fragment);
				trans.commit();

			}
		});

		// RelativeLayout re = (RelativeLayout)
		// header.findViewById(R.id.layoutlayout);
		// re.setPadding(0, (util.getScreenwidth() / 8) + 10, 0, 0);

		// addComment.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// if (CurrentUser == null) {
		// Toast.makeText(getActivity(),
		// "برای درج کامنت ابتدا باید وارد شوید",
		// Toast.LENGTH_SHORT).show();
		//
		// } else {
		//
		// dialog = new DialogcmtInfroum(FroumFragment.this, 0,
		// getActivity(), froumid, R.layout.dialog_addcomment,
		// 2);
		// dialog.show();
		// exadapter.notifyDataSetChanged();
		// }
		// }
		// });

		adapter.open();
		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);

		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}

		if (commentGroup != null) {
			Users uId;
			for (int i = 0; i < commentGroup.size(); i++) {

				CommentInFroum cm = commentGroup.get(i);
				int uidd = cm.getUserid();
				uId = adapter.getUserById(uidd);

				if (uId == null) {
					missedIds.add(uidd);
				}

			}

		}
		adapter.close();
		if (missedIds.size() > 0) {
			if (getActivity() != null) {

				// ringProgressDialog = ProgressDialog.show(getActivity(), "",
				// "لطفا منتظر بمانید...", true);
				// ringProgressDialog.setCancelable(true);
				date = new ServerDate(getActivity());
				date.delegate = FroumFragment.this;
				date.execute("");
				check = true;

			}
		}

		exlistview.addHeaderView(header);
		exadapter = new ExpandableCommentFroum(getActivity(), (ArrayList<CommentInFroum>) commentGroup, mapCollection,
				this, froumid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);

		if (CurrentUser == null) {
			likeIcon.setBackgroundResource(R.drawable.like_froum_off);
			// count.setBackgroundResource(R.drawable.count_like_off);
		} else {
			adapter.open();
			if (adapter.isUserLikedFroum(CurrentUser.getId(), froumid)) {
				likeIcon.setBackgroundResource(R.drawable.like_froum_on);
				// count.setBackgroundResource(R.drawable.count_like);

			} else {

				likeIcon.setBackgroundResource(R.drawable.like_froum_off);
				// count.setBackgroundResource(R.drawable.count_like_off);

			}
			adapter.close();

		}
		// count.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// adapter.open();
		// ArrayList<LikeInFroum> likedist = adapter
		// .getLikefroumLikeInFroumByFroumId(froumid);
		//
		// adapter.close();
		// if (likedist.size() == 0) {
		// Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
		// .show();
		// } else {
		// DialogPersonLikedFroum dia = new DialogPersonLikedFroum(
		// getActivity(), froumid, likedist);
		// dia.show();
		// }
		// }
		// });

		// این کد ها برای مشخص شدن مبدا ارسالی برای آپدیت کردن لیست می باشد
		// وقتی کاربر در صفحه فروم بدون کامنت ، نظری ثبت می کند به این صفحه
		// منتقل می شود و این کد ها برای مشخص کردن مبدا و انجام عمل آپدیت کردن
		// استفاده می شود
		SharedPreferences realizeIdComment = getActivity().getSharedPreferences("Id", 0);
		int destinyId = realizeIdComment.getInt("main_Id", -1);
		if (destinyId == 1371) {
			updateList();
		}

		likeTopic.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {

				adapter.open();
				ArrayList<LikeInFroum> likedist = adapter.getLikefroumLikeInFroumByFroumId(froumid);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0).show();
				} else {
					DialogPersonLikedFroum dia = new DialogPersonLikedFroum(getActivity(), froumid, likedist);
					util.setSizeDialog(dia);
				}

				return false;
			}
		});
		likeTopic.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				adapter.open();

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "برای درج لایک ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
				} else {

					// ringProgressDialog = ProgressDialog.show(getActivity(),
					// "", "لطفا منتظر بمانید...", true);
					// ringProgressDialog.setCancelable(true);
					date = new ServerDate(getActivity());
					date.delegate = FroumFragment.this;
					date.execute("");
					LikeOrComment = true;
					saveVisitFalg = false;

				}
				adapter.close();

			}

		});

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				String body = topics.getDescription() + "\n" + " مشاهده کامل گفتگو در: "
						+ "<a href=\"mechanical://SplashActivity\">اینجا</a> ";
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/html");
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, topics.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, Html.fromHtml(body));
				startActivity(Intent.createChooser(sharingIntent, "اشتراک از طریق"));
			}
		});

		ImageView report = (ImageView) view.findViewById(R.id.reportImage);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				final int userIdsender = topics.getUserId();
				final String t = topics.getDescription();
				itemId = topics.getId();

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

							util.sendMessage("Froum");

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

							util.reportAbuse(userIdsender, StaticValues.TypeReportFroumFragment, itemId, t, froumid, 0,
									-1);

						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == userIdsender)
								deleteItems(itemId);
							else {

								Toast.makeText(getActivity(), "", 0).show();
							}
						}

						return false;
					}
				};

				popupMenu.setOnMenuItemClickListener(menuitem);

				// DialogLongClick dia = new DialogLongClick(getActivity(), 1,
				// topics.getUserId(), topics.getId(),
				// FroumFragment.this, topics.getDescription());
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

			}
		});

		if (getActivity() != null) {
			ImageView[] arrayImage = util.inputCommentAndPickFile(getActivity(), true);
			ImageView send = arrayImage[0];

			send.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {

					if ("".equals(util.inputComment(getActivity()))) {
						Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0).show();
					} else {
						if (util.getCurrentUser() != null) {
							date = new ServerDate(getActivity());
							date.delegate = FroumFragment.this;
							date.execute("");
							LikeOrComment = false;
							isFinish = false;
							util.ReplyLayout(getActivity(), "", false);

						}
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

		}

		getAllComment();

		return view;
	}

	@Override
	public void onResume() {

		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN)

					if (keyCode == KeyEvent.KEYCODE_BACK) {

						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();
						FroumtitleFragment fragment = new FroumtitleFragment();

						fragment.setPostionListFroum(positionFroum);

						trans.replace(R.id.content_frame, fragment);

						trans.commit();

						return true;
					}

				return false;
			}
		});
		super.onResume();
	}

	public int getFroumId() {
		return id;
	}

	public int groupPosition(int groupPosition) {
		gp = groupPosition;
		return gp;
	}

	public int getDipsFromPixel(float pixels) {
		// Get the screen's density scale
		final float scale = getResources().getDisplayMetrics().density;
		// Convert the dps to pixels, based on density scale
		return (int) (pixels * scale + 0.5f);
	}

	public void updateList() {
		adapter.open();

		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);

		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInFroum_count(froumid).toString());

		exadapter = new ExpandableCommentFroum(getActivity(), (ArrayList<CommentInFroum>) commentGroup, mapCollection,
				this, froumid);

		exadapter.notifyDataSetChanged();

		exlistview.setAdapter(exadapter);
		exlistview.setSelectedGroup(mapCollection.size() - 1);

		adapter.close();

	}

	public void setcount() {
		countComment.setText(adapter.CommentInFroum_count(froumid).toString());

	}

	public void CommentId(int Id) {
		commentId = Id;
	}

	public void expanding(int groupPosition, int childPosition, boolean TypeAction) {

		adapter.open();
		commentGroup = adapter.getCommentInFroumbyPaperid(froumid, 0);
		mapCollection = new LinkedHashMap<CommentInFroum, List<CommentInFroum>>();

		List<CommentInFroum> reply = null;
		for (CommentInFroum comment : commentGroup) {
			reply = adapter.getReplyCommentbyCommentID(froumid, comment.getId());
			mapCollection.put(comment, reply);
		}

		countComment.setText(adapter.CommentInFroum_count(froumid).toString());

		exadapter = new ExpandableCommentFroum(getActivity(), (ArrayList<CommentInFroum>) commentGroup, mapCollection,
				this, froumid);
		exadapter.notifyDataSetChanged();
		exlistview.setAdapter(exadapter);
		if (groupPosition <= mapCollection.size()) {

			if (TypeAction) {

				exlistview.expandGroup(groupPosition);

				if (childPosition != -1 && childPosition > 0)
					exlistview.setSelectedChild(groupPosition, childPosition, true);
				else
					exlistview.setSelectedGroup(groupPosition);

			} else {

				if (childPosition != -1 && childPosition > 0) {

					exlistview.expandGroup(groupPosition);
					exlistview.setSelectedChild(groupPosition, childPosition - 1, true);

				} else {
					if (groupPosition > 0) {

						exlistview.setSelectedGroup(groupPosition - 1);
					}
				}

			}

		}
		adapter.close();

		// exlistview.setSelectedChild(groupPosition, 1, condition);

	}

	@Override
	public void processFinish(String output) {

		// Toast.makeText(getActivity(), output, 0).show();
		if (!output.contains("Exception")) {

			if (saveVisitFalg == true) {

				if (counterVisit == 0)
					currentTime = output;
				sendVisit();

			} else {
				if (isFinish == true) {

					return;
				}

				adapter.open();
				int id = -1;
				try {
					id = Integer.valueOf(output);

					if (LikeOrComment == true) {
						if (adapter.isUserLikedFroum(CurrentUser.getId(), froumid)) {
							adapter.deleteLikeFromFroum(CurrentUser.getId(), froumid);
							likeIcon.setBackgroundResource(R.drawable.like_froum_off);
							// count.setBackgroundResource(R.drawable.count_like_off);

							countLike.setText(adapter.LikeInFroum_count(froumid).toString());
						} else {
							adapter.insertLikeInFroumToDb(id, CurrentUser.getId(), froumid, serverDate, 0);
							likeIcon.setBackgroundResource(R.drawable.like_froum_on);
							// count.setBackgroundResource(R.drawable.count_like);

							countLike.setText(adapter.LikeInFroum_count(froumid).toString());
						}
					} else {
						adapter.open();

						adapter.insertCommentInFroumtoDb(id, util.inputComment(getActivity()), froumid,
								CurrentUser.getId(), serverDate, commentId);

						adapter.close();
						util.ToEmptyComment(getActivity());
						if (commentId == 0)
							expanding(exadapter.getGroupCount(), -1, true);
						else {
							expanding(gp, exadapter.getChildrenCount(gp), true);

						}

					}
					// if (ringProgressDialog != null) {
					// ringProgressDialog.dismiss();
					// }
				} catch (NumberFormatException ex) {
					if (output != null && !(output.contains("Exception") || output.contains("java"))) {

						serverDate = output;

						if (LikeOrComment == false) {

							if (getActivity() != null) {
								params = new LinkedHashMap<String, String>();

								saving = new Saving(getActivity());
								saving.delegate = FroumFragment.this;

								params.put("TableName", "CommentInFroum");

								params.put("Desk", util.inputComment(getActivity()));
								params.put("FroumId", String.valueOf(froumid));
								params.put("UserId", String.valueOf(CurrentUser.getId()));
								params.put("CommentId", String.valueOf(commentId));

								params.put("Date", output);
								params.put("ModifyDate", output);
								params.put("IsUpdate", "0");
								params.put("Id", "0");

								saving.execute(params);
							}
							// ringProgressDialog =
							// ProgressDialog.show(getActivity(), "", "لطفا
							// منتظر بمانید...", true);
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

						} else {

							if (check == true) {
								getUserFromServer(missedIds, controller);
								return;
							}

							if (adapter.isUserLikedFroum(CurrentUser.getId(), froumid)) {

								params = new LinkedHashMap<String, String>();
								if (getActivity() != null) {

									deleting = new Deleting(getActivity());
									deleting.delegate = FroumFragment.this;

									params.put("TableName", "LikeInFroum");
									params.put("UserId", String.valueOf(CurrentUser.getId()));
									params.put("FroumId", String.valueOf(froumid));

									deleting.execute(params);
								}
							} else {
								params = new LinkedHashMap<String, String>();
								if (getActivity() != null) {

									saving = new Saving(getActivity());
									saving.delegate = FroumFragment.this;

									params.put("TableName", "LikeInFroum");

									params.put("UserId", String.valueOf(CurrentUser.getId()));
									params.put("FroumId", String.valueOf(froumid));
									params.put("CommentId", "0");
									params.put("Date", output);
									params.put("IsUpdate", "0");
									params.put("Id", "0");

									saving.execute(params);
								}
							}
						}
					} else {
						Toast.makeText(getActivity(), "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
						// if (ringProgressDialog != null) {
						// ringProgressDialog.dismiss();
						// }
					}
				}

				catch (Exception e) {

					Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT).show();
					adapter.close();
					// if (ringProgressDialog != null) {
					// ringProgressDialog.dismiss();
					// }
				}
				adapter.close();
			}
		}
	}

	private void getUserFromServer(ArrayList<Integer> missedIds, int controller) {

		if (controller < missedIds.size()) {

			iid = missedIds.get(controller);

			if (getActivity() != null) {

				service = new ServiceComm(getActivity());
				service.delegate = FroumFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getUserById");
				items.put("Id", String.valueOf(iid));
				service.execute(items);
				flag = true;

			}
		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (flag == true) {

			if (!"".equals(output) && output != null
					&& !(output.contains("Exception") || output.contains("java") || output.contains("soap"))) {
				util.parseQuery(output);

				adapter.open();

				uu = adapter.getUserById(iid);

				adapter.close();
				if (getActivity() != null) {

					updating = new UpdatingImage(getActivity());
					updating.delegate = FroumFragment.this;
					maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Users");
					maps.put("Id", String.valueOf(uu.getId()));
					maps.put("fromDate", uu.getImageServerDate());
					updating.execute(maps);
				}
			} else
				Toast.makeText(getActivity(), "خطا در دریافت کاربران", 0).show();
		} else {
			adapter.open();

			adapter.deleteFroumTitle(itemId);
			adapter.deleteCommentFroum(itemId);
			adapter.deleteLikeFroum(itemId);

			adapter.close();

			// if (ringProgressDialog != null) {
			// ringProgressDialog.dismiss();
			// }

			FroumtitleFragment fr = new FroumtitleFragment();

			FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();

			trans.replace(R.id.content_frame, fr);
			trans.addToBackStack(null);
			trans.commit();
		}

	}

	@Override
	public void processFinish(byte[] output) {

		util.CreateFile(output, iid, "Mechanical", "Users", "user", "Users");
		adapter.open();
		adapter.UpdateImageServerDate(iid, "Users", serverDate);
		adapter.close();

		updateList();
		// if (ringProgressDialog != null) {
		// ringProgressDialog.dismiss();
		// }
	}

	public void addToFavorite(int currentUserId, int source, int ItemId) {

		if (adapter.IsUserFavoriteItem(currentUserId, ItemId, source) == true) {
			Toast.makeText(getActivity(), " قبلا در لیست علاقه مندی ها ذخیره شده است ", 0).show();
		} else {
			adapter.insertFavoritetoDb(0, currentUserId, ItemId, source);
			Toast.makeText(getActivity(), "به لیست علاقه مندی ها اضافه شد ", 0).show();
		}
	}

	public void deleteItems(int itemId) {

		ServiceComm service = new ServiceComm(getActivity());
		service.delegate = FroumFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("DeletingRecord", "DeletingRecord");

		items.put("tableName", "Froum");
		items.put("Id", String.valueOf(itemId));

		service.execute(items);

		// ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا
		// منتظر بمانید...", true);
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
	}

	private void checkInternet() {

		if (util.getCurrentUser() != null) {
			if (checkUsers() == true) {

				if (util.isNetworkConnected()) {

					ServerDate date = new ServerDate(getActivity());
					date.delegate = FroumFragment.this;
					date.execute("");
					saveVisitFalg = true;

				} else {

					adapter.open();
					adapter.insertVisitToDb(util.getCurrentUser().getId(), StaticValues.TypeFroumVist, froumid);
					adapter.close();

				}
			}

		}
	}

	private boolean checkUsers() {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = true;

		if (util.getCurrentUser().getId() == topics.getUserId())
			isSave = false;

		return isSave;

	}

	private void sendVisit() {

		if (getActivity() != null) {

			adapter.open();
			visitList = adapter.getAllVisitItems();
			adapter.close();

			Visit vis = null;

			if (visitList.size() != 0) {

				if (counterVisit < visitList.size()) {

					vis = visitList.get(counterVisit);

					sender = vis.getUserId();
					typeId = vis.getTypeId();
					idItem = vis.getObjectId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = FroumFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(sender));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(idItem));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					counterVisit++;

					saveVisitFalg = true;
					// sendVisit();
				} else {

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = FroumFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(sender));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(topics.getId()));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);

					adapter.open();
					adapter.deleteVisit();
					adapter.close();

					saveVisitFalg = false;
					isFinish = true;

				}

			} else {

				if (checkUsers() == true) {
					userId = util.getCurrentUser().getId();
					typeId = StaticValues.TypeFroumVist;
					// int idObj = object.getId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = FroumFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(topics.getId()));
					params.put("ModifyDate", String.valueOf(currentTime));
					params.put("Date", String.valueOf(currentTime));

					params.put("IsUpdate", "0");
					params.put("Id", "0");

					saving.execute(params);
					saveVisitFalg = false;
					isFinish = true;

				}
			}

		}

	}

	@Override
	public void resultSaveVisit(String output) {

		if (!output.contains("Exception")) {
			if (isFinish == false) {
				Visit vis = null;

				if (visitList.size() != 0) {

					if (counterVisit < visitList.size()) {

						vis = visitList.get(counterVisit);

						sender = vis.getUserId();
						typeId = vis.getTypeId();
						idItem = vis.getObjectId();

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = FroumFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(sender));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(idItem));
						params.put("ModifyDate", String.valueOf(currentTime));
						params.put("Date", String.valueOf(currentTime));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

						counterVisit++;

						saveVisitFalg = true;
						// sendVisit();
					} else {

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = FroumFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(sender));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(topics.getId()));
						params.put("ModifyDate", String.valueOf(currentTime));
						params.put("Date", String.valueOf(currentTime));

						params.put("IsUpdate", "0");
						params.put("Id", "0");

						saving.execute(params);

						adapter.open();
						adapter.deleteVisit();
						adapter.close();

						saveVisitFalg = false;
						isFinish = true;

					}

				}
			}
		}

	}

	private void getAllComment() {

		adapter.open();
		Settings settings = adapter.getSettings();
		adapter.close();

		final SharedPreferences currentTime = getActivity().getSharedPreferences("time", 0);

		String time = currentTime.getString("time", "-1");

		GetAllCommentById service = new GetAllCommentById(getActivity());
		service.delegate = FroumFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("tableName", "getAllCommentInFroumById");
		items.put("id", String.valueOf(froumid));
		items.put("fromDate", topics.getDate());
		items.put("endDate", time);
		items.put("isRefresh", "0");

		service.execute(items);

	}

	@Override
	public void ResultComment(String output) {

		if (util.checkError(output) == false) {

			util.parseQuery(output);
			exadapter.notifyDataSetChanged();
		}

	}
}
