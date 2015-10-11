package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
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

public class MainBrandFragment extends Fragment implements AsyncInterface,
		GetAsyncInterface, CommInterface {
	DataBaseAdapter adapter;
	int parentId;
	Users CurrentUser;
	Utility util;
	DialogCreatePage dialog;
	ListView lstObject;
	ObjectListAdapter ListAdapter;
	ArrayList<Object> mylist;

	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;

	Updating updating;
	Settings setting;
	int totalItemCountBeforeSwipe = 0;

	int userItemId = 0;
	Object obj;
	UpdatingImage ImageUpdating;

	Map<String, String> maps;
	ServerDate date;
	String serverDate;
	int code = 100;

	public MainBrandFragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getArguments() != null && getArguments().getString("Id") != null) {
			parentId = Integer.valueOf(getArguments().getString("Id"));
			Toast.makeText(getActivity(), "parent Id = " + parentId, 0).show();
		}

		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);

		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		final int MainObjectId = sendData.getInt("main_Id", -1);
		// RelativeLayout createPage = (RelativeLayout) view
		// .findViewById(R.id.relative);

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		CurrentUser = util.getCurrentUser();

		adapter.open();
		// objectList = adapter.getObjectbyParentId(id);
		mylist = adapter.getObjectbyParentId(parentId);

		// start code get image profile from server

		obj = adapter.getObjectbyid(mylist.get(userItemId).getId());

		if (getActivity() != null) {
			date = new ServerDate(getActivity());
			date.delegate = MainBrandFragment.this;
			date.execute("");
		}

		// end code for get image from server
		setting = adapter.getSettings();

		adapter.close();

		lstObject = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object,
				mylist, MainBrandFragment.this, true, null, 1);

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(
				R.layout.load_more_footer, null);
		lstObject.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (getActivity() != null) {
					updating = new Updating(getActivity());
					updating.delegate = MainBrandFragment.this;
					String[] params = new String[4];
					params[0] = "Object";
					params[1] = setting.getServerDate_Start_Object() != null ? setting
							.getServerDate_Start_Object() : "";
					params[2] = setting.getServerDate_End_Object() != null ? setting
							.getServerDate_End_Object() : "";

					params[3] = "1";
					updating.execute(params);
				}
			}
		});

		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		RelativeLayout rl = (RelativeLayout) view
				.findViewById(R.id.tablighRelative);
		RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(
				rl.getLayoutParams());

		lp.width = (util.getScreenwidth() / 8);
		lp.height = (util.getScreenwidth() / 8);
		lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		lp.setMargins(5, 5, 5, 5);
		ImageButton iconCreateTabligh = (ImageButton) view
				.findViewById(R.id.iconCreateTabligh);
		iconCreateTabligh.setLayoutParams(lp);

		final FloatingActionButton createItem = (FloatingActionButton) view
				.findViewById(R.id.fab);
		final String message = "کاربر گرامی اگر مشخصات برند یا فعالیت شما در این نرم افزار ثبت نشده می توانید با ایجاد صفحه،  فعالیت خود را به سایر کاربران این نرم افزار معرفی نمایید ";
		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new DialogCreatePage(getActivity(), message);
				dialog.show();

				SharedPreferences sendParentID = getActivity()
						.getSharedPreferences("Id", 0);
				sendParentID.edit().putInt("ParentId", parentId).commit();
				sendParentID.edit().putInt("mainObject", MainObjectId).commit();
				sendParentID.edit().putInt("objectId", 0).commit();

			}
		});

		lstObject.setAdapter(ListAdapter);
		lstObject.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				switch (arg1) {
				case SCROLL_STATE_IDLE: {
					createItem.show(true);

				}
					break;
				case SCROLL_STATE_TOUCH_SCROLL: {
					createItem.hide(true);

				}
					break;
				}

			}

			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				if (totalItemCount == visibleItemCount) {
					return;
				}

				int lastInScreen = firstVisibleItem + visibleItemCount;

				if (lastInScreen == totalItemCount
						&& OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == 1) {

					LoadMoreFooter.setVisibility(View.VISIBLE);
					if (getActivity() != null) {

						updating = new Updating(getActivity());
						updating.delegate = MainBrandFragment.this;
						String[] params = new String[4];
						params[0] = "Object";
						params[1] = setting.getServerDate_Start_Object() != null ? setting
								.getServerDate_Start_Object() : "";
						params[2] = setting.getServerDate_End_Object() != null ? setting
								.getServerDate_End_Object() : "";

						params[3] = "0";
						updating.execute(params);
						totalItemCountBeforeSwipe = totalItemCount;
					}
				}
			}
		});

		return view;
	}

	public void UpdateList() {
		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectbyParentId(parentId);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist, MainBrandFragment.this, true,
				null, 1);
		ListAdapter.notifyDataSetChanged();

		lstObject.setAdapter(ListAdapter);

		adapter.close();
	}

	public int getParentId() {
		return parentId;
	}

	@Override
	public void processFinish(String output) {

		LoadMoreFooter.setVisibility(View.INVISIBLE);

		if (output.length() == 18 && code == 100) {
			serverDate = output;
			ImageUpdating = new UpdatingImage(getActivity());
			ImageUpdating.delegate = MainBrandFragment.this;
			maps = new LinkedHashMap<String, String>();
			maps.put("tableName", "Object2");
			maps.put("Id", String.valueOf(obj.getId()));
			maps.put("fromDate", obj.getImage2ServerDate());
			ImageUpdating.execute(maps);
		}

		if (output.contains("anyType")) {

			Toast.makeText(getActivity(), "صفحه جدیدی یافت نشد", 0).show();
			LoadMoreFooter.setVisibility(View.INVISIBLE);

			if (swipeLayout != null) {

				swipeLayout.setRefreshing(false);
			}
			return;

		}
		if (swipeLayout != null) {

			swipeLayout.setRefreshing(false);
		}

		if (output != null
				&& !(output.contains("Exception") || output.contains("java")
						|| output.contains("SoapFault") || output
							.contains("anyType")) && code == -1) {

			util.parseQuery(output);
			adapter.open();
			mylist = adapter.getObjectbyParentId(parentId);
			adapter.close();

			if (totalItemCountBeforeSwipe != mylist.size()) {
				mylist.clear();
				if (mylist.size() > 0) {
					ListAdapter = new ObjectListAdapter(getActivity(),
							R.layout.row_object, mylist,
							MainBrandFragment.this, true, null, 1);
					lstObject.setAdapter(ListAdapter);
				}
			}
		}
	}

	@Override
	public void processFinish(byte[] output) {

		if (output != null) {

			util.CreateFile(output, obj.getId(), "Mechanical", "Profile",
					"profile", "Object");

			ServiceComm getDateService = new ServiceComm(getActivity());

			getDateService.delegate = MainBrandFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();
			items.put("tableName", "getObject2ImageDate");
			items.put("Id", String.valueOf(obj.getId()));
			getDateService.execute(items);

		}

	}

	@Override
	public void CommProcessFinish(String output) {
		if (output.equals("java.lang.NullPointerException") || output.equals("anyType"))
			output = "";

		adapter.open();
		adapter.updateObjectImage2ServerDate(obj.getId(), output);
		adapter.close();

		userItemId++;

		if (userItemId < mylist.size()) {
			adapter.open();
			mylist = adapter.getObjectbyParentId(parentId);

			obj = adapter.getObjectbyid(mylist.get(userItemId).getId());

			adapter.close();
			if (getActivity() != null) {

				ImageUpdating = new UpdatingImage(getActivity());
				ImageUpdating.delegate = this;
				maps = new LinkedHashMap<String, String>();
				maps.put("tableName", "Object2");
				maps.put("Id", String.valueOf(obj.getId()));
				maps.put("fromDate", obj.getImage2ServerDate());
				ImageUpdating.execute(maps);
			}
		} else {
			mylist.clear();
			adapter.open();
			mylist = adapter.getObjectbyParentId(parentId);
			adapter.close();

			ListAdapter = new ObjectListAdapter(getActivity(),
					R.layout.row_object, mylist, MainBrandFragment.this, false,
					null, 1);
			lstObject.setAdapter(ListAdapter);

		}
	}
}
