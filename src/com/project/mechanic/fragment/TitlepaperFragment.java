package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.PapertitleListAdapter;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Updating;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class TitlepaperFragment extends Fragment implements CommInterface,
		AsyncInterface {
	private ImageButton addtitle;
	private DialogPaperTitle dialog;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Paper> mylist;
	List<Paper> subList;
	List<Paper> tempList;
	PullAndLoadListView lstNews;
	int i = 0, j = 9;
	ListView lst;
	PapertitleListAdapter ListAdapter;
	public static final int DIALOG_FRAGMENT = 1;
	Utility utility;
	Users CurrentUser;
	int mLastFirstVisibleItem = 0;
	FloatingActionButton action;
	ServiceComm service;
	Updating updating;
	Settings setting;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlepaper, null);
		action = (FloatingActionButton) view.findViewById(R.id.fab);

		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		RelativeLayout header = (RelativeLayout) view
				.findViewById(R.id.headerTitleArticle);

		header.setVisibility(View.GONE);

		mdb = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());

		CurrentUser = utility.getCurrentUser();
		mdb.open();
		mylist = mdb.getAllPaper();
		setting = mdb.getSettings();
		mdb.close();

		// for Missed IDS
		String strIdes = getMissedIds();
		if (!"".equals(strIdes)) {
			service = new ServiceComm(getActivity());
			service.delegate = this;
			Map<String, String> items = new LinkedHashMap<String, String>();
			items.put("tableName", "getUserById");
			items.put("Id", strIdes);

			service.execute(items);
		}
		// for Missed IDS

		final FloatingActionButton action = (FloatingActionButton) view
				.findViewById(R.id.fab);
		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null)
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				else {

					dialog = new DialogPaperTitle(getActivity(),
							R.layout.dialog_addtitle, TitlepaperFragment.this);
					dialog.getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
					dialog.show();

				}

			}
		});
		if (CurrentUser == null) {
			addtitle.setImageResource(R.drawable.ic_create_off);
		}

		addtitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
					return;
				} else {
					dialog = new DialogPaperTitle(getActivity(),
							R.layout.dialog_addtitle, TitlepaperFragment.this);
					dialog.show();
				}

			}
		});

		if (mylist != null && !mylist.isEmpty()) {

			lstNews = (PullAndLoadListView) view.findViewById(R.id.lstComment);
			ListAdapter = new PapertitleListAdapter(getActivity(),
					R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
			lstNews.setAdapter(ListAdapter);
			((PullAndLoadListView) lstNews)
					.setOnRefreshListener(new OnRefreshListener() {

						public void onRefresh() {
							// Do work to refresh the list here.

							updating = new Updating(getActivity());
							updating.delegate = TitlepaperFragment.this;
							String[] params = new String[4];
							params[0] = "Paper";
							params[1] = setting.getServerDate_Paper() != null ? setting
									.getServerDate_Paper() : "";
							params[2] = "0";
							params[3] = "5";

							updating.execute(params);
							// REFRESSH !!!!!
						}
					});
			((PullAndLoadListView) lstNews)
					.setOnLoadMoreListener(new OnLoadMoreListener() {

						public void onLoadMore() {
							// Do the work to load more items at the end of list
							// here
							updating = new Updating(getActivity());
							updating.delegate = TitlepaperFragment.this;
							String[] params = new String[4];
							params[0] = "Paper";
							params[1] = setting.getServerDate_Paper() != null ? setting
									.getServerDate_Paper() : "";
							params[2] = "0";
							params[3] = "5";

							updating.execute(params);

						}
					});

			if (getArguments() != null) {

				mLastFirstVisibleItem = getArguments().getInt("Froum_List_Id");
				lstNews.setSelection(mLastFirstVisibleItem);
			}
		}

		if (lstNews != null) {

			lstNews.setOnScrollListener(new OnScrollListener() {

				@Override
				public void onScrollStateChanged(AbsListView arg0, int arg1) {
					switch (arg1) {
					case SCROLL_STATE_FLING:
						action.hide(true);
						break;
					case SCROLL_STATE_TOUCH_SCROLL:
						action.show(true);
						break;
					}

				}

				@Override
				public void onScroll(AbsListView arg0, int arg1, int arg2,
						int arg3) {
				}
			});
		}
		return view;
	}

	private class LoadMoreDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
			}

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// We need notify the adapter that the data have been changed
			((BaseAdapter) ListAdapter).notifyDataSetChanged();
			ListAdapter.notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
			((PullAndLoadListView) lstNews).onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			((PullAndLoadListView) lstNews).onLoadMoreComplete();
		}
	}

	private class PullToRefreshDataTask extends AsyncTask<Void, Void, Void> {

		@Override
		protected Void doInBackground(Void... params) {

			if (isCancelled()) {
				return null;
			}

			// Simulates a background task
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}

			// for (int i = 0; i < mAnimals.length; i++)
			// mListItems.addFirst(mAnimals[i]);

			return null;
		}

		@Override
		protected void onPostExecute(Void result) {

			// We need notify the adapter that the data have been changed
			((BaseAdapter) ListAdapter).notifyDataSetChanged();

			// Call onLoadMoreComplete when the LoadMore task, has finished
			((PullAndLoadListView) lstNews).onRefreshComplete();

			super.onPostExecute(result);
		}

	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();

		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
		ListAdapter.notifyDataSetChanged();
		lstNews = (PullAndLoadListView) view.findViewById(R.id.lstComment);
		lstNews.setAdapter(ListAdapter);

	}

	public String getMissedIds() {
		String strIdes = "";
		mdb.open();
		if (mylist != null) {
			Users uId;
			for (int i = 0; i < mylist.size(); ++i) {
				int uidd = mylist.get(i).getUserId();
				uId = mdb.getUserById(uidd);
				if (uId == null) {
					strIdes += uidd + "-";
				}
			}
		}
		mdb.close();
		return strIdes;
	}

	@Override
	public void CommProcessFinish(String output) {
		if (!"".equals(output)
				&& output != null
				&& !(output.contains("Exception") || output.contains("java") || output
						.contains("soap"))) {
			utility.parseQuery(output);
			updateView();
		} else {
			Toast.makeText(getActivity(), "خطا در بروز رسانی داده های سرور",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void processFinish(String output) {
		((PullAndLoadListView) lstNews).onLoadMoreComplete();
		if (output != null
				&& !(output.contains("Exception") || output.contains("java")
						|| output.contains("SoapFault") || output
							.contains("anyType"))) {
			utility.parseQuery(output);
			mylist.clear();
			mdb.open();
			mylist.addAll(mdb.getAllPaper());
			mdb.close();
			ListAdapter.notifyDataSetChanged();
		}
	}
}
