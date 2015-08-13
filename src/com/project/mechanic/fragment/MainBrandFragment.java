package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
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
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Updating;
import com.project.mechanic.utility.Utility;

public class MainBrandFragment extends Fragment implements AsyncInterface {
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

	boolean FindPosition, IsRunning = true;
	int beforePosition;

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

		setting = adapter.getSettings();

		adapter.close();

		lstObject = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object,
				mylist, MainBrandFragment.this);

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(
				R.layout.load_more_footer, null);
		lstObject.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
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
		});

		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		// createPage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {

		// dialog = new DialogCreatePage(getActivity());
		// dialog.show();
		//
		// SharedPreferences sendParentID = getActivity()
		// .getSharedPreferences("Id", 0);
		// sendParentID.edit().putInt("ParentId", id).commit();
		// Toast.makeText(getActivity(), "ParentId send = " + id,
		// Toast.LENGTH_SHORT).show();
		// }
		//
		// });
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
				case SCROLL_STATE_FLING:
					createItem.hide(true);
					break;
				case SCROLL_STATE_TOUCH_SCROLL:
					createItem.show(true);
					break;
				}

			}

			@Override
			public void onScroll(AbsListView arg0, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;

				if (lastInScreen == totalItemCount
						&& OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == 1) {

					LoadMoreFooter.setVisibility(View.VISIBLE);
					//
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

					int countList = ListAdapter.getCount();
					beforePosition = countList;

					FindPosition = false;
					IsRunning = false;

				}

			}
		});

		return view;
	}

	public void UpdateList() {
		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectbyParentId(parentId);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist, MainBrandFragment.this);
		ListAdapter.notifyDataSetChanged();

		lstObject.setAdapter(ListAdapter);

		adapter.close();
	}

	public int getParentId() {
		return parentId;
	}

	@Override
	public void processFinish(String output) {

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
							.contains("anyType"))) {

			util.parseQuery(output);
			mylist.clear();
			adapter.open();
			mylist = adapter.getObjectbyParentId(parentId);

			// mylist.addAll(adapter.getAllObject());
			adapter.close();

			if (mylist.size() > 0) {

				ListAdapter = new ObjectListAdapter(getActivity(),
						R.layout.row_object, mylist, MainBrandFragment.this);

				lstObject.setAdapter(ListAdapter);

				LoadMoreFooter.setVisibility(View.INVISIBLE);

				updating.cancel(true);
				IsRunning = true;

			}

			if (mylist.size() > beforePosition && FindPosition == false) {
				lstObject.setSelection(beforePosition);
				updating.cancel(true);
				IsRunning = true;
				return;

			}
		} else
			LoadMoreFooter.setVisibility(View.INVISIBLE);

	}
}
