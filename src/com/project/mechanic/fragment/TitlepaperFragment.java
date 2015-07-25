package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
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
	// PullAndLoadListView lstNews;
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
	// View headerMore, footerMore;
	ProgressDialog ringProgressDialog;
	SwipeRefreshLayout swipeLayout;

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

		lst = (ListView) view.findViewById(R.id.lstComment);

		// headerMore = getActivity().getLayoutInflater().inflate(
		// R.layout.header_load_more, null);
		//
		// footerMore = getActivity().getLayoutInflater().inflate(
		// R.layout.footer_load_more, null);

		final FloatingActionButton action = (FloatingActionButton) view
				.findViewById(R.id.fab);

		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				updating = new Updating(getActivity());
				updating.delegate = TitlepaperFragment.this;
				String[] params = new String[4];
				params[0] = "Paper";
				params[1] = setting.getServerDate_Start_Paper() != null ? setting
						.getServerDate_Start_Paper() : "";
				params[2] = setting.getServerDate_End_Paper() != null ? setting
						.getServerDate_End_Paper() : "";

				updating.execute(params);
			}
		});
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

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

		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);

		// lst.addHeaderView(headerMore);
		// lst.addFooterView(footerMore);
		// //
		// headerMore.setVisibility(View.GONE);
		// footerMore.setVisibility(View.GONE);

		lst.setAdapter(ListAdapter);

		int countList = ListAdapter.getCount();
		Toast.makeText(getActivity(), "تعداد مقالات = " + countList, 0).show();

		lst.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				switch (scrollState) {
				case SCROLL_STATE_TOUCH_SCROLL:
					// headerMore.setVisibility(View.VISIBLE);
					action.setVisibility(View.GONE);
					//
					break;
				//
				case SCROLL_STATE_IDLE:
					// headerMore.setVisibility(View.GONE);
					action.setVisibility(View.VISIBLE);

					break;
				//
				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;

				if (mLastFirstVisibleItem < firstVisibleItem) {
					// Toast.makeText(getActivity(), "down", 0).show();
					Log.i("SCROLLING DOWN", "TRUE");
				}
				if (mLastFirstVisibleItem > firstVisibleItem) {
					// Toast.makeText(getActivity(), "up", 0).show();

					Log.i("SCROLLING UP", "TRUE");
				}
				mLastFirstVisibleItem = firstVisibleItem;

				// if ((lastInScreen == totalItemCount)) {
				// footerMore.setVisibility(View.VISIBLE);
				// }
				// if (lst.getFirstVisiblePosition() == visibleItemCount) {
				// headerMore.setVisibility(View.VISIBLE);
				//
				// }
			}
		});

		// Button otherBtn = (Button) footerMore.findViewById(R.id.otherBtn);

		// otherBtn.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// updating = new Updating(getActivity());
		// updating.delegate = TitlepaperFragment.this;
		// String[] params = new String[4];
		// params[0] = "Paper";
		// params[1] = setting.getServerDate_Start_Paper() != null ? setting
		// .getServerDate_Start_Paper() : "";
		// params[2] = setting.getServerDate_End_Paper() != null ? setting
		// .getServerDate_End_Paper() : "";
		//
		// updating.execute(params);
		//
		// ringProgressDialog = ProgressDialog.show(getActivity(), "",
		// "لطفا منتظر بمانید...", true);
		//
		// }
		// });

		// lst.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView arg0, int arg1) {
		//
		// switch (key) {
		// case value:
		//
		// break;
		//
		// default:
		// break;
		// }
		// }
		//
		// @Override
		// public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3)
		// {
		// // TODO Auto-generated method stub
		//
		// }
		// })
		// if (lst.getSelectedItemPosition() == countList)
		// Toast.makeText(getActivity(), "yes" + countList, 0).show();

		// if (lst != null) {
		// lst.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// switch (arg1) {
		// case SCROLL_STATE_FLING:
		// action.hide(true);
		//
		// break;
		// case SCROLL_STATE_TOUCH_SCROLL: {
		// action.show(true);
		// break;
		// }
		// }
		//
		// }
		//
		// @Override
		// public void onScroll(AbsListView arg0, int arg1, int arg2,
		// int arg3) {
		// }
		// });
		// }

		// if (mylist != null && !mylist.isEmpty()) {

		// lstNews = (PullAndLoadListView) view.findViewById(R.id.lstComment);

		// lstNews.setAdapter(ListAdapter);
		// ((PullAndLoadListView) lstNews)
		// .setOnRefreshListener(new OnRefreshListener() {
		//
		// public void onRefresh() {
		// // Do work to refresh the list here.
		//
		// updating = new Updating(getActivity());
		// updating.delegate = TitlepaperFragment.this;
		// String[] params = new String[4];
		// params[0] = "Paper";
		// // params[1] = setting.getServerDate_Paper() != null
		// // ? setting
		// // .getServerDate_Paper() : "";
		// params[2] = "0";
		// params[3] = "5";
		//
		// updating.execute(params);
		// // REFRESSH !!!!!
		// }
		// });
		// ((PullAndLoadListView) lstNews)
		// .setOnLoadMoreListener(new OnLoadMoreListener() {
		//
		// public void onLoadMore() {
		// // Do the work to load more items at the end of list
		// // here
		// updating = new Updating(getActivity());
		// updating.delegate = TitlepaperFragment.this;
		// String[] params = new String[4];
		// params[0] = "Paper";
		// // params[1] = setting.getServerDate_Paper() != null
		// // ? setting
		// // .getServerDate_Paper() : "";
		// params[2] = "0";
		// params[3] = "5";
		//
		// updating.execute(params);
		//
		// }
		// });

		// if (getArguments() != null) {

		// mLastFirstVisibleItem = getArguments().getInt("Froum_List_Id");
		// lstNews.setSelection(mLastFirstVisibleItem);
		// }
		// }

		// if (lstNews != null) {
		//
		// lstNews.setOnScrollListener(new OnScrollListener() {
		//
		// @Override
		// public void onScrollStateChanged(AbsListView arg0, int arg1) {
		// switch (arg1) {
		// case SCROLL_STATE_FLING:
		// action.hide(true);
		// break;
		// case SCROLL_STATE_TOUCH_SCROLL:
		// action.show(true);
		// break;
		// }
		//
		// }
		//
		// @Override
		// public void onScroll(AbsListView arg0, int arg1, int arg2,
		// int arg3) {
		// }
		// });
		// }
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
			// ((PullAndLoadListView) lstNews).onLoadMoreComplete();

			super.onPostExecute(result);
		}

		@Override
		protected void onCancelled() {
			// Notify the loading more operation has finished
			// ((PullAndLoadListView) lstNews).onLoadMoreComplete();
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
			// ((PullAndLoadListView) lstNews).onRefreshComplete();

			super.onPostExecute(result);
		}

	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();

		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
		lst.setAdapter(ListAdapter);

		ListAdapter.notifyDataSetChanged();
		// lstNews = (PullAndLoadListView) view.findViewById(R.id.lstComment);
		// lstNews.setAdapter(ListAdapter);
		// lstNews.addHeaderView(headerMore);
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
		// ((PullAndLoadListView) lstNews).onLoadMoreComplete();
		if (output != null
				&& !(output.contains("Exception") || output.contains("java")
						|| output.contains("SoapFault") || output
							.contains("anyType"))) {

			utility.parseQuery(output);
			mylist.clear();
			mdb.open();
			mylist.addAll(mdb.getAllPaper());
			mdb.close();

			ListAdapter = new PapertitleListAdapter(getActivity(),
					R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
			lst.setAdapter(ListAdapter);
			ListAdapter.notifyDataSetChanged();

			if (swipeLayout != null) {

				swipeLayout.setRefreshing(false);
				// if (ringProgressDialog != null) {
				// ringProgressDialog.dismiss();
			}
			int countList = ListAdapter.getCount();

			Toast.makeText(getActivity(), "به روز رسانی با موفقیت انجام شد \n  تعداد مقالات  = " + countList , Toast.LENGTH_LONG)
					.show();

		}
	}
}
