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
import com.project.mechanic.StaticValues;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class MainBrandFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, CommInterface, AsyncInterfaceVisit {
	DataBaseAdapter adapter;
	int parentId;
	Users CurrentUser;
	Utility util;
	DialogCreatePage dialog;
	ListView lstObject;
	ObjectListAdapter ListAdapter;

	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;

	Updating updating;
	Settings setting;
	int totalItemCountBeforeSwipe = 0;

	int userItemId = 0;
	// Object obj;
	UpdatingImage ImageUpdating;

	Map<String, String> maps;
	ServerDate date;
	String serverDate;
	// int code = 100;
	int visitCounter = 0;

	///////////////

	Object objectItem;
	View rootView;
	FloatingActionButton createItem;
	int MainObjectId;
	ArrayList<Object> mylist = new ArrayList<Object>();

	int typeRunServer;

	//////

	public MainBrandFragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		if (getArguments() != null && getArguments().getString("Id") != null) {
			parentId = Integer.valueOf(getArguments().getString("Id"));
		}

		rootView = inflater.inflate(R.layout.fragment_object, null);

		SharedPreferences sendData = getActivity().getSharedPreferences("Id", 0);
		MainObjectId = sendData.getInt("main_Id", -1);

		init();

		fillListView();

		// request get image
		if (mylist.size() > 0)
			if (getActivity() != null) {
				date = new ServerDate(getActivity());
				date.delegate = MainBrandFragment.this;
				date.execute("");
				typeRunServer = StaticValues.TypeRunServerForGetDate;
			}

		refreshListView();

		createNewObject();

		onScrollListView();

		util.ShowFooterAgahi(getActivity(), true, 2);
		return rootView;
	}

	public void UpdateList() {
		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectbyParentId(parentId);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object, mylist,
				MainBrandFragment.this, true, null, 1);
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

		if (!output.contains("exception") && !output.contains("anyType")) {

			if (output.length() == 18 && typeRunServer == StaticValues.TypeRunServerForGetDate) {
				serverDate = output;
				getImageProfile();

			}

		}

		if (output != null && !(output.contains("Exception") || output.contains("java") || output.contains("SoapFault"))
				&& typeRunServer == StaticValues.TypeRunServerForRefreshItems) {

			if (output.contains("anyType")) {

				Toast.makeText(getActivity(), "صفحه جدیدی یافت نشد", 0).show();
				LoadMoreFooter.setVisibility(View.INVISIBLE);

				if (swipeLayout != null) {
					swipeLayout.setRefreshing(false);
				}
				return;

			} else {

				util.parseQuery(output);

				adapter.open();
				mylist = adapter.getObjectbyParentId(parentId);
				adapter.close();

				if (totalItemCountBeforeSwipe != mylist.size()) {
					mylist.clear();
					if (mylist.size() > 0) {
						ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object, mylist,
								MainBrandFragment.this, true, null, 1);
						lstObject.setAdapter(ListAdapter);
					}
				}
			}

		} else {
			if (swipeLayout != null) {
				swipeLayout.setRefreshing(false);
			}
		}
	}

	@Override
	public void processFinish(byte[] output) {

		if (output != null) {

			util.CreateFile(output, objectItem.getId(), "Mechanical", "Profile", "profile", "Object");

		}

		userItemId++;
		getImageProfile();
	}

	@Override
	public void CommProcessFinish(String output) {

		if (output.contains("Exception") || output.contains(("anyType")))
			output = "";

		adapter.open();
		adapter.updateObjectImage2ServerDate(objectItem.getId(), output);
		adapter.close();

		userItemId++;

		getServerDateImage();

	}

	private void getCountVisitFromServer() {

		if (visitCounter < mylist.size()) {

			adapter.open();
			objectItem = mylist.get(visitCounter);
			adapter.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = MainBrandFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(objectItem.getId()));
			serv.put("typeId", StaticValues.TypeObjectVisit + "");
			updateVisit.execute(serv);

		} else {

			fillListView();
		}

	}

	@Override
	public void processFinishVisit(String output) {

		if (!output.contains("Exception")) {

			adapter.open();
			adapter.updateCountView("Object", objectItem.getId(), Integer.valueOf(output));
			adapter.close();
		}
		visitCounter++;
		getCountVisitFromServer();

	}

	private void getImageProfile() {

		if (userItemId < mylist.size()) {

			objectItem = mylist.get(userItemId);
			String imageProfileServerDate = objectItem.getImage2ServerDate();

			if (imageProfileServerDate == null)
				imageProfileServerDate = "";

			if (getActivity() != null) {

				ImageUpdating = new UpdatingImage(getActivity());
				ImageUpdating.delegate = MainBrandFragment.this;
				maps = new LinkedHashMap<String, String>();

				maps.put("tableName", "Object2");
				maps.put("Id", String.valueOf(objectItem.getId()));
				maps.put("fromDate", imageProfileServerDate);

				ImageUpdating.execute(maps);
			}

		} else {

			userItemId = 0;
			getServerDateImage();

		}
	}

	private void getServerDateImage() {

		if (userItemId < mylist.size()) {

			objectItem = mylist.get(userItemId);

			if (getActivity() != null) {

				ServiceComm getDateService = new ServiceComm(getActivity());
				getDateService.delegate = MainBrandFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();

				items.put("tableName", "getObject2ImageDate");
				items.put("Id", String.valueOf(objectItem.getId()));

				getDateService.execute(items);

			}

		} else {

			getCountVisitFromServer();
		}

	}

	private void fillListView() {

		mylist.clear();
		adapter.open();
		mylist = adapter.getObjectbyParentId(parentId);
		adapter.close();

		ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object, mylist, MainBrandFragment.this, true,
				null, 1);

		lstObject.setAdapter(ListAdapter);

	}

	private void init() {

		adapter = new DataBaseAdapter(getActivity());

		util = new Utility(getActivity());

		CurrentUser = util.getCurrentUser();

		adapter.open();
		setting = adapter.getSettings();
		adapter.close();

		lstObject = (ListView) rootView.findViewById(R.id.listvCmt_Introduction);

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.load_more_footer, null);
		lstObject.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		createItem = (FloatingActionButton) rootView.findViewById(R.id.fab);

		((MainActivity) getActivity()).setTitle(R.string.object);

	}

	private void refreshListView() {
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (getActivity() != null) {

					updating = new Updating(getActivity());
					updating.delegate = MainBrandFragment.this;
					String[] params = new String[4];
					params[0] = "Object";
					params[1] = setting.getServerDate_Start_Object() != null ? setting.getServerDate_Start_Object()
							: "";
					params[2] = setting.getServerDate_End_Object() != null ? setting.getServerDate_End_Object() : "";

					params[3] = "1";
					updating.execute(params);

					typeRunServer = StaticValues.TypeRunServerForRefreshItems;
				}
			}
		});

		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);
	}

	private void createNewObject() {

		final String message = "کاربر گرامی اگر مشخصات برند یا فعالیت شما در این نرم افزار ثبت نشده می توانید با ایجاد صفحه،  فعالیت خود را به سایر کاربران این نرم افزار معرفی نمایید ";
		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				dialog = new DialogCreatePage(getActivity(), message);
				dialog.show();

				SharedPreferences sendParentID = getActivity().getSharedPreferences("Id", 0);
				sendParentID.edit().putInt("ParentId", parentId).commit();
				sendParentID.edit().putInt("mainObject", MainObjectId).commit();
				sendParentID.edit().putInt("objectId", 0).commit();

			}
		});

	}

	private void onScrollListView() {

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
			public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				if (totalItemCount == visibleItemCount) {
					return;
				}

				int lastInScreen = firstVisibleItem + visibleItemCount;

				if (lastInScreen == totalItemCount && OnScrollListener.SCROLL_STATE_TOUCH_SCROLL == 1) {

					LoadMoreFooter.setVisibility(View.VISIBLE);
					if (getActivity() != null) {

						updating = new Updating(getActivity());
						updating.delegate = MainBrandFragment.this;
						String[] params = new String[4];
						params[0] = "Object";
						params[1] = setting.getServerDate_Start_Object() != null ? setting.getServerDate_Start_Object()
								: "";
						params[2] = setting.getServerDate_End_Object() != null ? setting.getServerDate_End_Object()
								: "";

						params[3] = "0";
						updating.execute(params);
						totalItemCountBeforeSwipe = totalItemCount;
						typeRunServer = StaticValues.TypeRunServerForRefreshItems;

					}
				}
			}
		});
	}
}
