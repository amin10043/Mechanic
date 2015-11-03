package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
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
	ProgressDialog ringProgressDialog;
	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;

	boolean FindPosition;
	int beforePosition;

	ProgressBar progress;

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlepaper, null);
		action = (FloatingActionButton) view.findViewById(R.id.fab);

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

		final FloatingActionButton action = (FloatingActionButton) view
				.findViewById(R.id.fab);

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(
				R.layout.load_more_footer, null);
		lst.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);
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

				params[3] = "1";
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

		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);

		lst.setAdapter(ListAdapter);

		int countList = ListAdapter.getCount();
		Toast.makeText(getActivity(), "تعداد مقالات = " + countList,
				Toast.LENGTH_SHORT).show();

		lst.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int scrollState) {
				switch (scrollState) {
				case SCROLL_STATE_TOUCH_SCROLL: {
					action.setVisibility(View.GONE);
				}
					break;
				case SCROLL_STATE_IDLE: {
					action.setVisibility(View.VISIBLE);

				}
					break;
				default:
					break;
				}
			}

			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;
				//
				if (lastInScreen == totalItemCount) {

					LoadMoreFooter.setVisibility(View.VISIBLE);
					//
					updating = new Updating(getActivity());
					updating.delegate = TitlepaperFragment.this;
					String[] params = new String[4];
					params[0] = "Paper";
					params[1] = setting.getServerDate_Start_Paper() != null ? setting
							.getServerDate_Start_Paper() : "";
					params[2] = setting.getServerDate_End_Paper() != null ? setting
							.getServerDate_End_Paper() : "";

					params[3] = "0";
					updating.execute(params);

					int countList = ListAdapter.getCount();
					beforePosition = countList;

					// FindPosition = false;
				}
			}
		});
		utility.ShowFooterAgahi(getActivity(), true, 1);

		return view;
	}

	public void updateView(Context context) {

		mdb = new DataBaseAdapter(context);
		View view = ((Activity) context).getLayoutInflater().inflate(
				R.layout.fragment_titlepaper, null);

		ListView lst = (ListView) view.findViewById(R.id.lstComment);

		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();
		ListAdapter = new PapertitleListAdapter(context,
				R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
		lst.setAdapter(ListAdapter);

		ListAdapter.notifyDataSetChanged();
		// LoadMoreFooter.setVisibility(View.INVISIBLE);

	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();
		ListAdapter = new PapertitleListAdapter(getActivity(),
				R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
		lst.setAdapter(ListAdapter);

		ListAdapter.notifyDataSetChanged();
		LoadMoreFooter.setVisibility(View.INVISIBLE);

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
			updateView(getActivity());
		} else {
			Toast.makeText(getActivity(), "خطا در بروز رسانی داده های سرور",
					Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void processFinish(String output) {
		if (output.contains("anyType")) {
			LoadMoreFooter.setVisibility(View.INVISIBLE);
			// lst.removeFooterView(LoadMoreFooter);

		}
		if (swipeLayout != null) {

			swipeLayout.setRefreshing(false);
		}
		// lst.removeHeaderView(LoadMoreFooter);

		// LoadMoreFooter.setVisibility(View.GONE);

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

			// if (FindPosition == false) {
			// lst.setSelection(beforePosition);
			//
			// }
			LoadMoreFooter.setVisibility(View.INVISIBLE);

			// ListAdapter.notifyDataSetChanged();

			if (ringProgressDialog != null) {
				ringProgressDialog.dismiss();
			}

			// Toast.makeText(getActivity(), "به روز رسانی با موفقیت انجام شد ",
			// Toast.LENGTH_LONG).show();

		}

	}
}
