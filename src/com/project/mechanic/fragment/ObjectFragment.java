package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
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
import android.widget.ListView;
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

public class ObjectFragment extends Fragment implements AsyncInterface {

	DataBaseAdapter adapter;
	private Intent intent;
	Users currentUser;
	Utility util;
	DialogCreatePage dialog;
	ListView lstObject;
	int city_id, m;
	ObjectListAdapter ListAdapter;

	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;

	Updating updating;
	Settings setting;

	boolean FindPosition;
	int beforePosition;

	ArrayList<Object> mylist;
	int typeList;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);

		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		final int id = sendData.getInt("main_Id", -1);
		m = id;
		city_id = Integer.valueOf(getArguments().getString("cityId"));
		if (getArguments().getString("advisorId") != null)
			typeList = Integer.valueOf(getArguments().getString("advisorId"));

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();

		lstObject = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		// RelativeLayout createPage = (RelativeLayout) view
		// .findViewById(R.id.relative);
		// SharedPreferences sendObjectId = getActivity().getSharedPreferences(
		// "Id", 0);
		// final int ObjectId = sendData.getInt("main_Id", -1);

		adapter.open();
		setting = adapter.getSettings();

		if (id == 2 || id == 3 || id == 4) {

			if (id == 2)
				typeList = 0;

			mylist = adapter.getObjectBy_BTId_CityId(id, city_id, typeList);

			if (mylist != null || !mylist.isEmpty())
				ListAdapter = new ObjectListAdapter(getActivity(),
						R.layout.row_object, mylist, ObjectFragment.this);
			lstObject.setAdapter(ListAdapter);

		} else {
			SharedPreferences pageId = getActivity().getSharedPreferences("Id",
					0);
			int brand = pageId.getInt("brandID", -1);

			Toast.makeText(getActivity(), "agency", 0).show();
			mylist = adapter.subBrandObject(brand, city_id);

			if (mylist != null || !mylist.isEmpty()) {

				ListAdapter = new ObjectListAdapter(getActivity(),
						R.layout.row_object, mylist, ObjectFragment.this);
				lstObject.setAdapter(ListAdapter);
			}
		}

		adapter.close();
		swipeLayout = (SwipeRefreshLayout) view
				.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				updating = new Updating(getActivity());
				updating.delegate = ObjectFragment.this;
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
		LoadMoreFooter = getActivity().getLayoutInflater().inflate(
				R.layout.load_more_footer, null);
		lstObject.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);

		final FloatingActionButton createItem = (FloatingActionButton) view
				.findViewById(R.id.fab);
		final String message = "کاربر گرامی اگر مشخصات برند یا فعالیت شما در این نرم افزار ثبت نشده می توانید با ایجاد صفحه،  فعالیت خود را به سایر کاربران این نرم افزار معرفی نمایید ";

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

				if (lastInScreen == totalItemCount) {

					LoadMoreFooter.setVisibility(View.VISIBLE);
					//
					updating = new Updating(getActivity());
					updating.delegate = ObjectFragment.this;
					String[] params = new String[4];
					params[0] = "Object";
					params[1] = setting.getServerDate_Start_Object() != null ? setting
							.getServerDate_Start_Object() : "";
					params[2] = setting.getServerDate_End_Object() != null ? setting
							.getServerDate_End_Object() : "";

					params[3] = "1";
					updating.execute(params);

					int countList = ListAdapter.getCount();
					beforePosition = countList;

					FindPosition = false;

				}

			}
		});

		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new DialogCreatePage(getActivity(), message);
				dialog.show();
				SharedPreferences sendToCreate = getActivity()
						.getSharedPreferences("Id", 0);

				if (id == 2 || id == 3 || id == 4) {

					if (id == 2)
						typeList = 0;

					sendToCreate.edit().putInt("MainObjectId", id).commit();
					sendToCreate.edit().putInt("CityId", city_id).commit();
					sendToCreate.edit().putInt("objectId", 0).commit();
					sendToCreate.edit().putInt("ObjectTypeId", typeList)
							.commit();

					Toast.makeText(getActivity(), "main id  = " + id,
							Toast.LENGTH_SHORT).show();

				} else {
					SharedPreferences pageId = getActivity()
							.getSharedPreferences("Id", 0);
					int brandId = pageId.getInt("brandID", -1);
					int MainObjID = pageId.getInt("main object id", -1);

					sendToCreate.edit().putInt("MainObjectId", MainObjID)
							.commit();
					sendToCreate.edit().putInt("CityId", city_id).commit();
					sendToCreate.edit().putInt("objectId", brandId).commit();

					Toast.makeText(getActivity(), "brand id  = " + brandId,
							Toast.LENGTH_SHORT).show();
					Toast.makeText(getActivity(), "main object = " + MainObjID,
							Toast.LENGTH_SHORT).show();
				}

			}
		});

		// createPage.setOnClickListener(new OnClickListener() {
		//
		// @Override
		// public void onClick(View arg0) {
		//
		// dialog = new DialogCreatePage(getActivity());
		// dialog.show();
		//
		// }
		// });

		return view;
	}

	public void UpdateList() {
		adapter.open();
		if (mylist != null || !mylist.isEmpty()) {

			ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(m,
					city_id, typeList);
			ObjectListAdapter ListAdapter = new ObjectListAdapter(
					getActivity(), R.layout.row_object, mylist,
					ObjectFragment.this);
			lstObject.setAdapter(ListAdapter);

			ListAdapter.notifyDataSetChanged();
		}
		adapter.close();
	}

	public int getCityId() {
		return city_id;
	}

	@Override
	public void processFinish(String output) {
		if (output.contains("anyType")) {
			LoadMoreFooter.setVisibility(View.INVISIBLE);
			// lst.removeFooterView(LoadMoreFooter);

		}
		if (swipeLayout != null) {
			//
			// swipeLayout.setRefreshing(false);
			// }
			//
			// if (output != null
			// && !(output.contains("Exception") || output.contains("java")
			// || output.contains("SoapFault") || output
			// .contains("anyType"))) {
			//
			// util.parseQuery(output);
			// mylist.clear();
			// adapter.open();
			// mylist = adapter.getObjectBy_BTId_CityId(m, city_id);
			// // mylist.addAll(adapter.getAllObject());
			// adapter.close();
			// if (mylist != null || !mylist.isEmpty())
			// ListAdapter = new ObjectListAdapter(getActivity(),
			// R.layout.row_object, mylist, ObjectFragment.this);
			//
			// lstObject.setAdapter(ListAdapter);
			//
			// // if (FindPosition == false) {
			// // lstObject.setSelection(beforePosition);
			// //
			// // }
			// LoadMoreFooter.setVisibility(View.INVISIBLE);
			//
			// ListAdapter.notifyDataSetChanged();

		}

	}
}