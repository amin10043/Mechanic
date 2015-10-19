package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.PosttitleListadapter;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class PosttitleFragment extends Fragment implements GetAsyncInterface,
		CommInterface, AsyncInterface {
	private ImageButton addtitle;
	private DialogpostTitle dialog;
	DialogcmtInfroum dialog2;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Post> mylist;
	ListView lst;
	PosttitleListadapter ListAdapter;
	ImageButton Replytocm;
	public static final int DIALOG_FRAGMENT = 1;
	Utility util;
	Users Currentuser;
	UpdatingImage updating;
	Map<String, String> maps;
	int userItemId = 0;
	String serverDate = "";
	ServerDate date;
	Users u;
	FloatingActionButton action;
	int mLastFirstVisibleItem = 0;
	ArrayList<Integer> ids;
	ArrayList<Integer> missedIds;
	ServiceComm service;

	Updating update;
	Settings setting;

	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlepost, null);
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		action = (FloatingActionButton) view.findViewById(R.id.fab);

		lst = (ListView) view.findViewById(R.id.lstComment);
		ids = new ArrayList<Integer>();
		missedIds = new ArrayList<Integer>();

		final SharedPreferences realize = getActivity().getSharedPreferences(
				"Id", 0);

		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Currentuser == null)
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				else {
					dialog = new DialogpostTitle(getActivity(),
							R.layout.dialog_addtitle, PosttitleFragment.this);
					dialog.getWindow()
							.setSoftInputMode(
									WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
					dialog.show();
					realize.edit().putInt("main_Id", 1).commit();
				}
			}
		});

		mdb = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		Currentuser = util.getCurrentUser();

		mdb.open();
		mylist = mdb.getAllPost();

		String strIdes = "";
		if (mylist != null) {
			Users uId;
			for (int i = 0; i < mylist.size(); ++i) {
				int uidd = mylist.get(i).getUserId();
				uId = mdb.getUserById(uidd);
				if (uId == null) {
					missedIds.add(uidd);
					strIdes += uidd + "-";
				}
			}
		}
		setting = mdb.getSettings();

		mdb.close();

		if (!"".equals(strIdes)) {

			if (getActivity() != null) {

				service = new ServiceComm(getActivity());
				service.delegate = this;
				Map<String, String> items = new LinkedHashMap<String, String>();
				items.put("tableName", "getUserById");
				items.put("Id", strIdes);

				service.execute(items);
			}
		}
		if (getActivity() != null) {

			date = new ServerDate(getActivity());
			date.delegate = this;
			date.execute("");
		}
		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (getActivity() != null) {

					update = new Updating(getActivity());
					update.delegate = PosttitleFragment.this;
					String[] params = new String[4];
					params[0] = "Post";
					params[1] = setting.getServerDate_Start_Post() != null ? setting
							.getServerDate_Start_Post() : "";
					params[2] = setting.getServerDate_End_Post() != null ? setting
							.getServerDate_End_Post() : "";
					params[3] = "1";
					update.execute(params);

				}
			}
		});
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(
				R.layout.load_more_footer, null);
		lst.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);
		ListAdapter = new PosttitleListadapter(getActivity(),
				R.layout.raw_froumtitle, mylist, PosttitleFragment.this);
		lst.setAdapter(ListAdapter);

		int countList = ListAdapter.getCount();
		Toast.makeText(getActivity(), "تعداد فروم ها = " + countList, 0).show();

		if (getArguments() != null) {
			mLastFirstVisibleItem = getArguments().getInt("Post_List_Id");
			lst.setSelection(mLastFirstVisibleItem);
		}

		registerForContextMenu(lst);

		lst.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				switch (arg1) {
				case SCROLL_STATE_TOUCH_SCROLL: {
					action.hide(true);
				}
					break;
				case SCROLL_STATE_IDLE: {
					action.show(true);
					break;
				}
				}

			}

			public void onScroll(AbsListView arg0, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;

				if (lastInScreen == totalItemCount) {

					LoadMoreFooter.setVisibility(View.VISIBLE);

					if (getActivity() != null) {

						update = new Updating(getActivity());
						update.delegate = PosttitleFragment.this;
						String[] params = new String[4];
						params[0] = "Post";
						params[1] = setting.getServerDate_Start_Post() != null ? setting
								.getServerDate_Start_Post() : "";
						params[2] = setting.getServerDate_End_Post() != null ? setting
								.getServerDate_End_Post() : "";
						params[3] = "0";
						update.execute(params);

					}
				}

			}
		});

		util.ShowFooterAgahi(getActivity(), true, 1);
		return view;
	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllPost();
		mdb.close();
		ListAdapter = new PosttitleListadapter(getActivity(),
				R.layout.raw_posttitle, mylist, PosttitleFragment.this);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(byte[] output) {

		Post f;
		mdb.open();
		if (output != null && mylist != null && mylist.size() > userItemId) {
			f = mylist.get(userItemId);

			util.CreateFile(output, f.getUserId(), "Mechanical", "Users",
					"user", "Users");
			mdb.UpdateImageServerDate(f.getUserId(), "Users", serverDate);

			// mdb.UpdateUserImage(f.getUserId(), output, serverDate);
			ListAdapter.notifyDataSetChanged();
			LoadMoreFooter.setVisibility(View.INVISIBLE);

		}

		userItemId++;

		if (userItemId < mylist.size() && getActivity() != null) {
			Users u = mdb.getUserById(mylist.get(userItemId).getUserId());
			if (u != null && !ids.contains(u.getId())) {
				ids.add(u.getId());
				if (getActivity() != null) {

					updating = new UpdatingImage(getActivity());
					updating.delegate = this;
					maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Users");
					maps.put("Id", String.valueOf(u.getId()));
					maps.put("fromDate", u.getImageServerDate());
					updating.execute(maps);
				}
			} else {
				byte[] b = null;
				processFinish(b);
			}
		}
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		mdb.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(String output) {
		if (!"".equals(output)
				&& output != null
				&& !(output.contains("Exception") || output.contains("java") || output
						.contains("soap"))) {
			if (mylist != null && mylist.size() > 0) {
				mdb.open();
				if (mylist.size() > userItemId) {
					u = mdb.getUserById(mylist.get(userItemId).getUserId());
					if (u != null) {
						ids.add(u.getId());
						serverDate = output;
						if (getActivity() != null) {
							updating = new UpdatingImage(getActivity());
							updating.delegate = this;
							maps = new LinkedHashMap<String, String>();
							maps.put("tableName", "Users");
							maps.put("Id", String.valueOf(u.getId()));
							maps.put("fromDate", u.getImageServerDate());
							updating.execute(maps);

							if (swipeLayout != null)
								swipeLayout.setRefreshing(false);
							LoadMoreFooter.setVisibility(View.INVISIBLE);

						}
					} else {

						byte[] b = null;
						processFinish(b);
					}
				}
				mdb.close();
				ListAdapter.notifyDataSetChanged();

			}
			if (output.contains("anyType")) {
				LoadMoreFooter.setVisibility(View.INVISIBLE);
				// lst.removeFooterView(LoadMoreFooter);

			}
			if (swipeLayout != null)
				swipeLayout.setRefreshing(false);
			LoadMoreFooter.setVisibility(View.INVISIBLE);

		}

	}

	@Override
	public void CommProcessFinish(String output) {
		if (!"".equals(output)
				&& output != null
				&& !(output.contains("Exception") || output.contains("java") || output
						.contains("soap"))) {
			util.parseQuery(output);
			updateView();
			if (swipeLayout != null)
				swipeLayout.setRefreshing(false);

		} else {
			if (swipeLayout != null)
				swipeLayout.setRefreshing(false);
			Toast.makeText(getActivity(), "خطا در بروز رسانی داده های سرور",
					Toast.LENGTH_SHORT).show();
		}

	}
}
