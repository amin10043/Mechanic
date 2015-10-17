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
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.PaperListAdapter;
import com.project.mechanic.entity.CommentInPaper;
import com.project.mechanic.entity.LikeInPaper;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Deleting;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.utility.Utility;

public class PaperFragment extends Fragment implements AsyncInterface {

	DataBaseAdapter adapter;
	int paperID;
	LinearLayout btnAddcmt;
	LinearLayout Like;
	TextView NumofLike, NumofComment, txttitle, txttitleDes, txtname, txtdate;
	DialogcmtInPaper dialog;

	ListView lst;
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
	RelativeLayout countLike;
	int userId;

	String serverDate = "";
	ServerDate date;
	ProgressDialog ringProgressDialog;

	Saving saving;
	Deleting deleting;
	Map<String, String> params;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.fragment_paper, null);
		header = getActivity().getLayoutInflater().inflate(
				R.layout.header_expandable, null);
		util = new Utility(getActivity());

		CurrentUser = util.getCurrentUser();

		btnAddcmt = (LinearLayout) header.findViewById(R.id.addCommentToTopic);
		Like = (LinearLayout) header.findViewById(R.id.LikeTopicLinear);
		lstNews = (ListView) view.findViewById(R.id.listViewnewspaper);
		NumofComment = (TextView) header
				.findViewById(R.id.numberOfCommentTopic);
		NumofLike = (TextView) header.findViewById(R.id.txtNumofLike_CmtFroum);
		txttitle = (TextView) header.findViewById(R.id.title_topic);
		txttitleDes = (TextView) header.findViewById(R.id.description_topic);
		txtdate = (TextView) header.findViewById(R.id.date_cc);
		txtname = (TextView) header.findViewById(R.id.name_cc);
		countLike = (RelativeLayout) header.findViewById(R.id.countLike);

		icon = (ImageView) header.findViewById(R.id.iconfroumtitle);
		sharebtn = (ImageView) header.findViewById(R.id.sharefroumicon);

		adapter = new DataBaseAdapter(getActivity());
		adapter.open();

		paperID = Integer.valueOf(getArguments().getString("Id"));

		if (CurrentUser == null
				|| !adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
			Like.setBackgroundResource(R.drawable.like_off);
			countLike.setBackgroundResource(R.drawable.count_like_off);
		} else {

			Like.setBackgroundResource(R.drawable.like_on);
			countLike.setBackgroundResource(R.drawable.count_like);

		}
		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

		NumofLike.setText(adapter.LikeInPaper_count(paperID).toString());

		mylist = adapter.getCommentInPaperbyPaperid(paperID);

		final Paper p = adapter.getPaperItembyid(paperID);
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
		PaperListadapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, mylist, PaperFragment.this);

		lstNews.addHeaderView(header);
		lstNews.setAdapter(PaperListadapter);

		txtname.setText(u.getName());

		txttitle.setText(p.getTitle());
		txttitleDes.setText(p.getContext());
		txtdate.setText(util.getPersianDate(p.getDate()));

		LinearLayout rl = (LinearLayout) header
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
				icon.setImageBitmap(bitmap);
				icon.setLayoutParams(lp);

			}
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
		adapter.close();
		icon.setOnClickListener(new View.OnClickListener() {

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

		// if (mylist != null && !mylist.isEmpty()) {
		// // lstNews.addHeaderView(header);
		// lstNews = (ListView) view.findViewById(R.id.listViewnewspaper);
		// PaperListadapter = new PaperListAdapter(getActivity(),
		// R.layout.raw_papercmt, subList, PaperFragment.this);
		//
		// lstNews.setAdapter(PaperListadapter);
		//
		// }

		final SharedPreferences realizeIdPaper = getActivity()
				.getSharedPreferences("Id", 0);
		int destinyId = realizeIdPaper.getInt("main_Id", -1);

		if (destinyId == 1378) {
			// lstNews.setSelection(PaperListadapter.getCount() - 1);

			updateView();
		}

		countLike.setOnClickListener(new OnClickListener() {

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

		Like.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;

				} else {

					date = new ServerDate(getActivity());
					date.delegate = PaperFragment.this;
					date.execute("");
				}

			}
		});

		btnAddcmt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					dialog = new DialogcmtInPaper(PaperFragment.this,
							getActivity(), R.layout.dialog_addcomment, paperID,
							2);
					dialog.show();

				}

			}
		});

		ImageView report = (ImageView) view.findViewById(R.id.reportImage);
		report.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0)
							.show();
				} else {

					DialogLongClick dia = new DialogLongClick(getActivity(), 2,
							p.getUserId(), p.getId(), PaperFragment.this, p
									.getContext());
					WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
					lp.copyFrom(dia.getWindow().getAttributes());
					lp.width = (int) (util.getScreenwidth() / 1.5);
					lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
					;
					dia.show();

					dia.getWindow().setAttributes(lp);
					dia.getWindow().setBackgroundDrawable(
							new ColorDrawable(
									android.graphics.Color.TRANSPARENT));
				}
			}
		});

		util.ShowFooterAgahi(getActivity(), true, 4);

		return view;

	}

	// private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	//
	// if (isCancelled()) {
	// return null;
	// }
	//
	// // Simulates a background task
	// try {
	// Thread.sleep(5000);
	// } catch (InterruptedException e) {
	// }
	// //
	// // for (int i = 0; i < mNames.length; i++)
	// // mListItems.add(mNames[i]);
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	//
	// // We need notify the adapter that the data have been changed
	// ((BaseAdapter) PaperListadapter).notifyDataSetChanged();
	//
	// // Call onLoadMoreComplete when the LoadMore task, has finished
	// ((PullAndLoadListView) lstNews).onLoadMoreComplete();
	//
	// super.onPostExecute(result);
	// }
	//
	// @Override
	// protected void onCancelled() {
	// // Notify the loading more operation has finished
	// ((PullAndLoadListView) lstNews).onLoadMoreComplete();
	// }
	// }

	// private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {
	//
	// @Override
	// protected Void doInBackground(Void... params) {
	//
	// if (isCancelled()) {
	// return null;
	// }
	//
	// // Simulates a background task
	// try {
	// Thread.sleep(1000);
	// } catch (InterruptedException e) {
	// }
	//
	// // for (int i = 0; i < mAnimals.length; i++)
	// // mListItems.addFirst(mAnimals[i]);
	//
	// return null;
	// }
	//
	// @Override
	// protected void onPostExecute(Void result) {
	//
	// // We need notify the adapter that the data have been changed
	// ((BaseAdapter) PaperListadapter).notifyDataSetChanged();
	//
	// // Call onLoadMoreComplete when the LoadMore task, has finished
	// ((PullAndLoadListView) lstNews).onRefreshComplete();
	//
	// super.onPostExecute(result);
	// }
	//
	// }

	public void updateView() {
		adapter.open();
		subList = adapter.getCommentInPaperbyPaperid(paperID);
		NumofComment.setText(adapter.CommentInPaper_count(paperID).toString());

		lstNews = (PullAndLoadListView) view
				.findViewById(R.id.listViewnewspaper);

		PaperListadapter = new PaperListAdapter(getActivity(),
				R.layout.raw_papercmt, subList, PaperFragment.this);

		lstNews.setAdapter(PaperListadapter);
		lstNews.setSelection(15);
		Toast.makeText(getActivity(), subList.size() - 1 + "", 0).show();

		adapter.close();
	}

	@Override
	public void processFinish(String output) {

		int id = -1;

		try {
			id = Integer.valueOf(output);
			adapter.open();
			if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
				adapter.deleteLikeFromPaper(CurrentUser.getId(), paperID);
				Like.setBackgroundResource(R.drawable.like_off);
				countLike.setBackgroundResource(R.drawable.count_like_off);

				NumofLike
						.setText(adapter.LikeInPaper_count(paperID).toString());
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
			} else {
				adapter.insertLikeInPaperToDb(CurrentUser.getId(), paperID,
						serverDate);
				Like.setBackgroundResource(R.drawable.like_on);
				countLike.setBackgroundResource(R.drawable.count_like);

				NumofLike
						.setText(adapter.LikeInPaper_count(paperID).toString());
				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}
			}
			adapter.close();

		} catch (NumberFormatException e) {
			if (output != null
					&& !(output.contains("Exception") || output
							.contains("java"))) {
				adapter.open();
				if (adapter.isUserLikedPaper(CurrentUser.getId(), paperID)) {
					adapter.open();
					// int c = adapter.LikeInFroum_count(ItemId) - 1;
					// countLikeFroum.setText(String.valueOf(c));

					params = new LinkedHashMap<String, String>();
					deleting = new Deleting(getActivity());
					deleting.delegate = PaperFragment.this;

					params.put("TableName", "LikeInPaper");
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("PaperId", String.valueOf(paperID));

					deleting.execute(params);

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

					// countLikeFroum.setText(adapter
					// .LikeInFroum_count(ItemId).toString());

					adapter.close();
				}
				adapter.close();
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
}
