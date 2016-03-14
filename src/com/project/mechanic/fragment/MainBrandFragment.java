package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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

	int pos = 0;
	int controllerFollower = 0;
	int IdObject;
	String typeGetValue = "";

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

		if (getArguments() != null) {
			pos = Integer.valueOf(getArguments().getInt("position"));
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

		lstObject.setSelection(pos);

		refreshListView();

		createNewObject();

		onScrollListView();

		util.ShowFooterAgahi(getActivity(), false, 2);
		return rootView;
	}

	public void UpdateList() {
		adapter.open();
		mylist = adapter.getObjectbyParentId(parentId);
		ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object, mylist,
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

		// LoadMoreFooter.setVisibility(View.INVISIBLE);

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
				// LoadMoreFooter.setVisibility(View.INVISIBLE);

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

			String imagePath = util.CreateFile(output, objectItem.getId(), "Mechanical", "Profile", "profile",
					"Object");

			if (!"".equals(imagePath))
				mylist.get(userItemId).setImagePath2(imagePath);

			ListAdapter.notifyDataSetChanged();
		}

		userItemId++;
		getImageProfile();
	}

	@Override
	public void CommProcessFinish(String output) {

		if (output.contains("Exception") || output.contains(("anyType")))
			output = "";

		if (typeGetValue.equals("getFollower")) {

			adapter.open();
			adapter.updateCountFollower(IdObject, Integer.valueOf(output));
			adapter.close();
			
			ListAdapter.notifyDataSetChanged();


			controllerFollower++;
			getCountFollwers();

		} else {

			adapter.open();
			adapter.updateObjectImage2ServerDate(objectItem.getId(), output);
			adapter.close();

			userItemId++;

			getServerDateImage();
		}

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

			getCountFollwers();
		}

	}

	@Override
	public void resultCountView(String output) {

		if (!output.contains("Exception")) {

			adapter.open();
			adapter.updateCountView("Object", objectItem.getId(), Integer.valueOf(output));
			adapter.close();

			mylist.get(visitCounter).setCountView(Integer.valueOf(output));
			ListAdapter.notifyDataSetChanged();

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
				typeGetValue = "";

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
		// lstObject.addFooterView(LoadMoreFooter);
		// LoadMoreFooter.setVisibility(View.INVISIBLE);

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
				// dialog = new DialogCreatePage(getActivity(), message);
				// dialog.show();
				//
				// SharedPreferences sendParentID =
				// getActivity().getSharedPreferences("Id", 0);
				// sendParentID.edit().putInt("ParentId", parentId).commit();
				// sendParentID.edit().putInt("mainObject",
				// MainObjectId).commit();
				// sendParentID.edit().putInt("objectId", 0).commit();

				final RelativeLayout bottomSheet = (RelativeLayout) rootView.findViewById(R.id.bottmSheet);
				TextView titleSheet = (TextView) rootView.findViewById(R.id.titleSheet);
				TextView SeenBetter = (TextView) rootView.findViewById(R.id.better);
				ImageView createPage = (ImageView) rootView.findViewById(R.id.createDialogPage);
				ImageView close = (ImageView) rootView.findViewById(R.id.delete);

				titleSheet.setText(message);
				titleSheet.setTextSize(20);
				titleSheet.setLineSpacing(10, 1);
				titleSheet.setTypeface(util.SetFontIranSans());
				SeenBetter.setTypeface(util.SetFontIranSans());
				SeenBetter.setTextSize(20);

				bottomSheet.setVisibility(View.VISIBLE);
				createItem.setVisibility(View.INVISIBLE);

				Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
				bottomSheet.startAnimation(anim);

				createPage.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						if (util.getCurrentUser() == null) {

							Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();

							Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
							bottomSheet.startAnimation(anim);

							bottomSheet.setVisibility(View.GONE);
							util.ShowFooterAgahi(getActivity(), false, 2);
							createItem.setVisibility(View.VISIBLE);

						} else {
							FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.replace(R.id.content_frame, new CreateIntroductionFragment());
							trans.commit();

							SharedPreferences sendParentID = getActivity().getSharedPreferences("Id", 0);
							sendParentID.edit().putInt("ParentId", parentId).commit();
							sendParentID.edit().putInt("mainObject", MainObjectId).commit();
							sendParentID.edit().putInt("objectId", 0).commit();
						}

					}
				});

				close.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
						bottomSheet.startAnimation(anim);

						bottomSheet.setVisibility(View.GONE);
						util.ShowFooterAgahi(getActivity(), false, 2);
						createItem.setVisibility(View.VISIBLE);

					}
				});
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

					// LoadMoreFooter.setVisibility(View.VISIBLE);
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

	private void getCountFollwers() {

		if (mylist.size() > 0) {

			if (controllerFollower < mylist.size()) {

				IdObject = mylist.get(controllerFollower).getId();

				ServiceComm comm = new ServiceComm(getActivity());
				comm.delegate = this;
				Map<String, String> params = new LinkedHashMap<String, String>();
				params.put("tableName", "getFollowerCountByObjectId");
				params.put("objectId", String.valueOf(IdObject));
				comm.execute(params);

				typeGetValue = "getFollower";
			}

		}

	}

}
