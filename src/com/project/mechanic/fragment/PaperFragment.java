package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.PaperListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.entity.Visit;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.VisitSaveInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.SavingVisit;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.PopupMenu.OnMenuItemClickListener;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import ir.noghteh.JustifiedTextView;

public class PaperFragment extends Fragment implements AsyncInterface, CommInterface, VisitSaveInterface {

	DataBaseAdapter adapter;
	int paperID;
	// LinearLayout btnAddcmt;
	LinearLayout Like;
	TextView NumofLike, NumofComment, txttitle, txtname, txtdate, countvisit;
	DialogcmtInPaper dialog;
	JustifiedTextView txttitleDes;
	ArrayList<CommentInPaper> mylist;
	PaperListAdapter PaperListadapter;
	int like = 0;
	Utility util;
	View header;
	Users CurrentUser;
	ImageView icon, sharebtn;
	// String currentDate;
	// PersianDate date;
	// int i = 0, j = 9;
	List<CommentInPaper> subList;
	List<CommentInPaper> tempList;
	View view;
	ListView lstNews;
	// RelativeLayout countLike;
	int userId;
	ImageView likeIcon;
	String serverDate = "";
	ServerDate date;
	ProgressDialog ringProgressDialog;
	List<String> menuItems;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;
	boolean LikeOrComment; // like == true & comment == false
	Paper p;
	// boolean saveVisit;
	int idItem, typeId, sender;
	Visit visit;
	int counterVisit = 0;
	boolean isFinish = false, saveVisitFalg;
	String currentTime = "";
	List<Visit> visitList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_paper, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_froum, null);
		util = new Utility(getActivity());

		CurrentUser = util.getCurrentUser();

		// btnAddcmt = (LinearLayout)
		// header.findViewById(R.id.addCommentToTopic);
		Like = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);
		lstNews = (ListView) view.findViewById(R.id.listViewnewspaper);
		NumofComment = (TextView) header.findViewById(R.id.numberOfCommentTopic);
		NumofLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		txttitle = (TextView) header.findViewById(R.id.title_topic);
		txttitleDes = (JustifiedTextView) header.findViewById(R.id.description_topic);
		txtdate = (TextView) header.findViewById(R.id.date_cc);
		TextView time = (TextView) header.findViewById(R.id.timetxt);

		txtname = (TextView) header.findViewById(R.id.name_cc);
		// countLike = (RelativeLayout) header.findViewById(R.id.countLike);

		likeIcon = (ImageView) header.findViewById(R.id.likeIcon);

		icon = (ImageView) header.findViewById(R.id.iconfroumtitle);
		sharebtn = (ImageView) header.findViewById(R.id.sharefroumicon);

		countvisit = (TextView) header.findViewById(R.id.countvisit);

		adapter = new DataBaseAdapter(getActivity());
		adapter.open();

		paperID = Integer.valueOf(getArguments().getString("Id"));

		if (CurrentUser == null || !adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
			likeIcon.setBackgroundResource(R.drawable.like_froum_off);
			// countLike.setBackgroundResource(R.drawable.count_like_off);
		} else {

			likeIcon.setBackgroundResource(R.drawable.like_froum_on);
			// countLike.setBackgroundResource(R.drawable.count_like);

		}
		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

		NumofLike.setText(adapter.LikeInPaper_count(paperID).toString());

		mylist = adapter.getCommentInPaperbyPaperid(paperID);

		p = adapter.getPaperItembyid(paperID);
		Users u = adapter.getUserbyid(p.getUserId());
		// lstNews.addHeaderView(header);
		// lstNews.addHeaderView(header);
		userId = u.getId();
		// if (mylist != null && !mylist.isEmpty()) {
		//
		// if (mylist.size() < j) {
		// j = mylist.size();
		// }
		// List<CommentInPaper> tmpList = mylist.subList(i, j);
		// subList = new ArrayList<CommentInPaper>();
		// for (CommentInPaper T : tmpList) {
		// if (!subList.contains(T))
		// subList.add(T);
		// }
		// }
		PaperListadapter = new PaperListAdapter(getActivity(), R.layout.raw_papercmt, mylist, PaperFragment.this);

		lstNews.addHeaderView(header);
		lstNews.setAdapter(PaperListadapter);

		txtname.setText(u.getName());

		txttitle.setText(p.getTitle());
		txttitleDes.setText(p.getContext());
		txttitleDes.setTypeFace(util.SetFontIranSans());

		txttitleDes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
		txttitleDes.setLineSpacing(15);

		String ddd = util.getPersianDate(p.getDate());
		List<String> dateTime = util.spilitDateTime(ddd);
		txtdate.setText(dateTime.get(0));
		time.setText(dateTime.get(1));

		LinearLayout rl = (LinearLayout) header.findViewById(R.id.imageLinear);

		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(rl.getLayoutParams());

		lp.width = (int) (util.getScreenwidth() / StaticValues.RateImagePaperFragmentPage);
		lp.height = (int) (util.getScreenwidth() / StaticValues.RateImagePaperFragmentPage);
		lp.gravity = Gravity.CENTER_HORIZONTAL;
		lp.setMargins(10, 10, 10, 10);

		Bitmap bitmap;
		String ImagePath = u.getImagePath();
		if (ImagePath != null) {
			bitmap = BitmapFactory.decodeFile(ImagePath);
			if (bitmap != null) {
				icon.setImageBitmap(Utility.getclip(bitmap));
				icon.setLayoutParams(lp);

			}
		}

		adapter.close();

		countvisit.setText(p.getCountView() + "");
		icon.setOnClickListener(new View.OnClickListener() {

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

		// if (mylist != null && !mylist.isEmpty()) {
		// // lstNews.addHeaderView(header);
		// lstNews = (ListView) view.findViewById(R.id.listViewnewspaper);
		// PaperListadapter = new PaperListAdapter(getActivity(),
		// R.layout.raw_papercmt, subList, PaperFragment.this);
		//
		// lstNews.setAdapter(PaperListadapter);
		//
		// }

		RelativeLayout re = (RelativeLayout) header.findViewById(R.id.layoutlayout);
		re.setPadding(0, (util.getScreenwidth() / 8) + 10, 0, 0);

		final SharedPreferences realizeIdPaper = getActivity().getSharedPreferences("Id", 0);
		int destinyId = realizeIdPaper.getInt("main_Id", -1);

		if (destinyId == 1378) {
			// lstNews.setSelection(PaperListadapter.getCount() - 1);

			updateView();
		}

		RelativeLayout para = (RelativeLayout) header.findViewById(R.id.layoutparams);

		RelativeLayout.LayoutParams mnb = new RelativeLayout.LayoutParams(para.getLayoutParams());

		mnb.width = LayoutParams.WRAP_CONTENT;
		mnb.height = LayoutParams.WRAP_CONTENT;
		mnb.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		mnb.setMargins(0, util.getScreenwidth() / 4, 5, 5);

		txttitle.setLayoutParams(mnb);

		//// countLike.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		// adapter.open();
		// ArrayList<LikeInPaper> likedist = adapter
		// .getLikePaperByPaperId(paperID);
		//
		// adapter.close();
		// if (likedist.size() == 0) {
		// Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0)
		// .show();
		// } else {
		// DialogPersonLikedPaper dia = new DialogPersonLikedPaper(
		// getActivity(), paperID, likedist);
		// dia.show();
		// }
		// }
		// });
		checkInternet();

		sharebtn.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
				sharingIntent.setType("text/plain");
				// String shareBody = x.getDescription();
				sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, p.getTitle());
				sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, p.getContext());
				startActivity(Intent.createChooser(sharingIntent, "اشتراک از طریق"));
			}
		});

		Like.setOnLongClickListener(new OnLongClickListener() {

			@Override
			public boolean onLongClick(View arg0) {

				adapter.open();
				ArrayList<LikeInPaper> likedist = adapter.getLikePaperByPaperId(paperID);

				adapter.close();
				if (likedist.size() == 0) {
					Toast.makeText(getActivity(), "لایکی ثبت نشده است", 0).show();
				} else {
					DialogPersonLikedPaper dia = new DialogPersonLikedPaper(getActivity(), paperID, likedist);
					util.setSizeDialog(dia);
				}

				return false;
			}
		});

		Like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();
					return;

				} else {

					date = new ServerDate(getActivity());
					date.delegate = PaperFragment.this;
					date.execute("");

					LikeOrComment = true;

				}

			}
		});

		//// btnAddcmt.setOnClickListener(new View.OnClickListener() {
		//
		// @Override
		// public void onClick(View v) {
		// if (CurrentUser == null) {
		// Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
		// Toast.LENGTH_SHORT).show();
		// return;
		// } else {
		// dialog = new DialogcmtInPaper(PaperFragment.this,
		// getActivity(), R.layout.dialog_addcomment, paperID,
		// 2);
		// dialog.show();
		//
		// }
		//
		// }
		// });

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
							addToFavorite(util.getCurrentUser().getId(), 2, p.getId());
							adapter.close();
						}
						if (item.getTitle().equals("کپی")) {

							util.CopyToClipboard(p.getContext());

						}
						if (item.getTitle().equals("گزارش تخلف")) {

							util.reportAbuse(p.getUserId(), 2, p.getId(), p.getContext(), 0);

						}
						if (item.getTitle().equals("حذف")) {
							if (util.getCurrentUser() != null && util.getCurrentUser().getId() == p.getUserId())
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
				// p.getUserId(), p.getId(), PaperFragment.this, p
				// .getContext());
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
					Toast.makeText(getActivity(), " نظر نمی تواند خالی باشد", 0).show();
				} else {

					date = new ServerDate(getActivity());
					date.delegate = PaperFragment.this;
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

	public void updateView() {
		adapter.open();
		subList = adapter.getCommentInPaperbyPaperid(paperID);
		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

		lstNews = (ListView) view.findViewById(R.id.listViewnewspaper);

		PaperListadapter = new PaperListAdapter(getActivity(), R.layout.raw_papercmt, subList, PaperFragment.this);

		lstNews.setAdapter(PaperListadapter);
		lstNews.setSelection(PaperListadapter.getCount());

		adapter.close();
	}

	@Override
	public void processFinish(String output) {

		Toast.makeText(getActivity(), output, 0).show();

		if (!output.contains("Exception")) {

			if (saveVisitFalg == true) {

				// if (counterVisit == 0)
				currentTime = output;
				sendVisit();

			} else {
				if (isFinish == true) {

					return;
				}
				int id = -1;

				try {
					id = Integer.valueOf(output);

					if (LikeOrComment == true) {

						adapter.open();
						if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
							adapter.deleteLikeFromPaper(CurrentUser.getId(), paperID);
							likeIcon.setBackgroundResource(R.drawable.like_froum_off);
							// countLike.setBackgroundResource(R.drawable.count_like_off);

							NumofLike.setText(adapter.LikeInPaper_count(paperID).toString());
							if (ringProgressDialog != null) {
								ringProgressDialog.dismiss();
							}
						} else {
							adapter.insertLikeInPaperToDb(CurrentUser.getId(), paperID, serverDate);
							likeIcon.setBackgroundResource(R.drawable.like_froum_on);
							// countLike.setBackgroundResource(R.drawable.count_like);

							NumofLike.setText(adapter.LikeInPaper_count(paperID).toString());
							if (ringProgressDialog != null) {
								ringProgressDialog.dismiss();
							}
						}
						adapter.close();

					} else {

						adapter.open();
						adapter.insertCommentInPapertoDb(util.inputComment(getActivity()), paperID, CurrentUser.getId(),
								serverDate);
						adapter.close();

						util.ToEmptyComment(getActivity());

						updateView();

						if (ringProgressDialog != null) {
							ringProgressDialog.dismiss();
						}
					}

				} catch (NumberFormatException e) {
					if (output != null && !(output.contains("Exception") || output.contains("java"))) {

						if (LikeOrComment == true) {

							adapter.open();
							if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
								adapter.open();
								// int c = adapter.LikeInFroum_count(ItemId) -
								// 1;
								// countLikeFroum.setText(String.valueOf(c));

								params = new LinkedHashMap<String, String>();
								deleting = new Deleting(getActivity());
								deleting.delegate = PaperFragment.this;

								params.put("TableName", "LikeInPaper");
								params.put("UserId", String.valueOf(CurrentUser.getId()));
								params.put("PaperId", String.valueOf(paperID));

								deleting.execute(params);

								ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...",
										true);

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
								saving.delegate = PaperFragment.this;

								params.put("TableName", "LikeInPaper");

								params.put("UserId", String.valueOf(CurrentUser.getId()));
								params.put("PaperId", String.valueOf(paperID));
								params.put("Date", output);
								params.put("IsUpdate", "0");
								params.put("Id", "0");

								serverDate = output;

								saving.execute(params);

								ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...",
										true);

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
							saving.delegate = PaperFragment.this;

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

							ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

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
						Toast.makeText(getActivity(), "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
						if (ringProgressDialog != null) {
							ringProgressDialog.dismiss();
						}
					}
				} catch (Exception e) {

					Toast.makeText(getActivity(), "خطا در ثبت", Toast.LENGTH_SHORT).show();
					if (ringProgressDialog != null) {
						ringProgressDialog.dismiss();
					}
				}
			}

		}
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
		service.delegate = PaperFragment.this;
		Map<String, String> items = new LinkedHashMap<String, String>();
		items.put("DeletingRecord", "DeletingRecord");

		items.put("tableName", "Paper");
		items.put("Id", String.valueOf(itemId));

		service.execute(items);

		ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

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

		FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager().beginTransaction();

		trans.replace(R.id.content_frame, fr);
		trans.addToBackStack(null);
		trans.commit();
	}

	private void checkInternet() {

		if (util.getCurrentUser() != null) {

			if (util.isNetworkConnected()) {
				Toast.makeText(getActivity(), "Connected", 0).show();

				ServerDate date = new ServerDate(getActivity());
				date.delegate = PaperFragment.this;
				date.execute("");
				saveVisitFalg = true;

			} else {
				Toast.makeText(getActivity(), "Disconnected", 0).show();

				if (checkUsers() == true) {

					adapter.open();
					adapter.insertVisitToDb(util.getCurrentUser().getId(), StaticValues.TypePaperVisit, p.getId());
					adapter.close();
				}

			}

		}
	}

	private boolean checkUsers() {

		// if isSave = true allow to save visit on server
		// else don't allow to save
		boolean isSave = true;

		if (util.getCurrentUser().getId() == p.getUserId())
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

					userId = vis.getUserId();
					typeId = vis.getTypeId();
					idItem = vis.getObjectId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = PaperFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
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
					saving.delegate = PaperFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(p.getId()));
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
					typeId = StaticValues.TypePaperVisit;
					// int idObj = object.getId();

					params = new LinkedHashMap<String, String>();
					SavingVisit saving = new SavingVisit(getActivity());
					saving.delegate = PaperFragment.this;

					params.put("TableName", "Visit");
					params.put("UserId", String.valueOf(userId));
					params.put("TypeId", String.valueOf(typeId));
					params.put("ObjectId", String.valueOf(p.getId()));
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
	public void saveVisit(String output) {

		if (!output.contains("Exception")) {

			if (isFinish == false) {

				Visit vis = null;

				if (visitList.size() != 0) {

					if (counterVisit < visitList.size()) {

						vis = visitList.get(counterVisit);

						userId = vis.getUserId();
						typeId = vis.getTypeId();
						idItem = vis.getObjectId();

						params = new LinkedHashMap<String, String>();
						SavingVisit saving = new SavingVisit(getActivity());
						saving.delegate = PaperFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(userId));
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
						saving.delegate = PaperFragment.this;

						params.put("TableName", "Visit");
						params.put("UserId", String.valueOf(userId));
						params.put("TypeId", String.valueOf(typeId));
						params.put("ObjectId", String.valueOf(p.getId()));
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
}
