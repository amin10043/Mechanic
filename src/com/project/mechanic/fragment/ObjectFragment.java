package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

public class ObjectFragment extends Fragment
		implements AsyncInterface, GetAsyncInterface, AsyncInterfaceVisit, CommInterface {

	DataBaseAdapter adapter;
	Users currentUser;
	Utility util;
	DialogCreatePage dialog;
	ListView lstObject;
	int city_id /* , m */;
	ObjectListAdapter ListAdapter;

	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;

	Updating updating;
	Settings setting;

	int totalItemCountBeforeSwipe = 0;

	ArrayList<Object> mylist = new ArrayList<Object>();
	int typeList;

	SharedPreferences pageId;
	int AgencyService, brand;

	int userItemId = 0;
	// Object obj;
	UpdatingImage ImageUpdating;

	Map<String, String> maps;
	int visitCounter = 0;

	/////////////////

	View rootView;
	FloatingActionButton createItem;

	int mainId;
	Object objectItem;
	ServerDate date;
	String serverDate;
	int typeRunServer;

	///////////////////

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);

		rootView = inflater.inflate(R.layout.fragment_object, null);

		sharedPrefrences();

		init();

		fillListView();

		// request get image
		if (mylist.size() > 0)
			if (getActivity() != null) {
				date = new ServerDate(getActivity());
				date.delegate = ObjectFragment.this;
				date.execute("");
				typeRunServer = StaticValues.TypeRunServerForGetDate;
			}

		refreshListView();

		createNewObject();

		onScrollListView();

		// ////
		// if (mainId == 2 || mainId == 3 || mainId == 4) {
		// if (mainId == 2)
		// typeList = 0;
		//
		// mylist = adapter.getObjectBy_BTId_CityId(mainId, city_id, typeList);
		//
		// if (mylist != null && !mylist.isEmpty()) {
		// ListAdapter = new ObjectListAdapter(getActivity(),
		// R.layout.row_object, mylist, ObjectFragment.this,
		// true, null, 1);
		// lstObject.setAdapter(ListAdapter);
		//
		// // start code get image profile from server
		//
		// obj = adapter.getObjectbyid(mylist.get(userItemId).getId());
		//
		// ImageUpdating = new UpdatingImage(getActivity());
		// ImageUpdating.delegate = ObjectFragment.this;
		// maps = new LinkedHashMap<String, String>();
		// maps.put("tableName", "Object2");
		// maps.put("Id", String.valueOf(obj.getId()));
		// maps.put("fromDate", obj.getImage2ServerDate());
		// ImageUpdating.execute(maps);
		//
		// // end code for get image from server
		// }
		// } else {
		//
		// brand = pageId.getInt("brandID", -1);
		//
		// mylist = adapter.subBrandObject(brand, city_id, AgencyService);
		//
		// if (mylist != null && !mylist.isEmpty()) {
		//
		// ListAdapter = new ObjectListAdapter(getActivity(),
		// R.layout.row_object, mylist, ObjectFragment.this,
		// true, null, 1);
		// lstObject.setAdapter(ListAdapter);
		// }
		// }
		//
		// adapter.close();
		//

		util.ShowFooterAgahi(getActivity(), false, 1);

		return rootView;
	}

	public void UpdateList() {
		adapter.open();
		if (mylist != null || !mylist.isEmpty()) {

			ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(mainId, city_id, typeList);
			ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object, mylist,
					ObjectFragment.this, false, null, 1);
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

				fillListView();
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

	private void getCountVisitFromServer() {
		adapter.open();

		if (mainId == 2 || mainId == 3 || mainId == 4)
			mylist = adapter.getObjectBy_BTId_CityId(mainId, city_id, typeList);
		else
			mylist = adapter.subBrandObject(brand, city_id, AgencyService);

		if (visitCounter < mylist.size()) {

			objectItem = adapter.getObjectbyid(mylist.get(visitCounter).getId());
			adapter.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = ObjectFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(objectItem.getId()));
			serv.put("typeId", StaticValues.TypeObjectVisit + "");
			updateVisit.execute(serv);

		} else {

			fillListView();
			// if (getActivity() != null) {
			//
			// if (mainId == 2 || mainId == 3 || mainId == 4)
			// mylist = adapter.getObjectBy_BTId_CityId(mainId, city_id,
			// typeList);
			// else
			// mylist = adapter.subBrandObject(brand, city_id, AgencyService);
			//
			// ListAdapter = new ObjectListAdapter(getActivity(),
			// R.layout.row_object, mylist, ObjectFragment.this,
			// false, null, 1);
			// lstObject.setAdapter(ListAdapter);
			// }
		}
		adapter.close();
	}

	@Override
	public void resultCountView(String output) {

		if (!output.contains("Exception")) {

			adapter.open();
			adapter.updateCountView("Object", objectItem.getId(), Integer.valueOf(output));
			adapter.close();
		}
		visitCounter++;
		getCountVisitFromServer();
	}

	private void sharedPrefrences() {

		pageId = getActivity().getSharedPreferences("Id", 0);
		AgencyService = pageId.getInt("IsAgency", -1);

		SharedPreferences sendData = getActivity().getSharedPreferences("Id", 0);
		mainId = sendData.getInt("main_Id", -1);
		city_id = Integer.valueOf(getArguments().getString("cityId"));
		if (getArguments().getString("advisorId") != null)
			typeList = Integer.valueOf(getArguments().getString("advisorId"));
	}

	private void init() {

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();

		lstObject = (ListView) rootView.findViewById(R.id.listvCmt_Introduction);
		swipeLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.swipe_container);
		createItem = (FloatingActionButton) rootView.findViewById(R.id.fab);

		adapter.open();
		setting = adapter.getSettings();
		adapter.close();

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.load_more_footer, null);
		lstObject.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

	}

	private void fillListView() {

		mylist.clear();

		if (mainId == 2 || mainId == 3 || mainId == 4) {
			if (mainId == 2)
				typeList = 0;

			adapter.open();
			mylist = adapter.getObjectBy_BTId_CityId(mainId, city_id, typeList);
			adapter.close();

		} else {

			brand = pageId.getInt("brandID", -1);
			adapter.open();
			mylist = adapter.subBrandObject(brand, city_id, AgencyService);
			adapter.close();
		}

		ListAdapter = new ObjectListAdapter(getActivity(), R.layout.row_object, mylist, ObjectFragment.this, true, null,
				1);
		lstObject.setAdapter(ListAdapter);

	}

	private void refreshListView() {

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				if (getActivity() != null) {

					updating = new Updating(getActivity());
					updating.delegate = ObjectFragment.this;
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

	private void getServerDateImage() {

		if (userItemId < mylist.size()) {

			objectItem = mylist.get(userItemId);

			if (getActivity() != null) {

				ServiceComm getDateService = new ServiceComm(getActivity());
				getDateService.delegate = ObjectFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();

				items.put("tableName", "getObject2ImageDate");
				items.put("Id", String.valueOf(objectItem.getId()));

				getDateService.execute(items);

			}

		} else {
			userItemId = 0;
			getCountVisitFromServer();
		}

	}

	private void createNewObject() {

		createItem.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				final String message = "کاربر گرامی اگر مشخصات برند یا فعالیت شما در این نرم افزار ثبت نشده می توانید با ایجاد صفحه،  فعالیت خود را به سایر کاربران این نرم افزار معرفی نمایید ";

				// dialog = new DialogCreatePage(getActivity(), message);
				// dialog.show();
				// SharedPreferences sendToCreate =
				// getActivity().getSharedPreferences("Id", 0);
				//
				// if (mainId == 2 || mainId == 3 || mainId == 4) {
				//
				// if (mainId == 2)
				// typeList = 0;
				//
				// sendToCreate.edit().putInt("MainObjectId", mainId).commit();
				// sendToCreate.edit().putInt("CityId", city_id).commit();
				// sendToCreate.edit().putInt("objectId", 0).commit();
				// sendToCreate.edit().putInt("ObjectTypeId",
				// typeList).commit();
				//
				// } else {
				// SharedPreferences pageId =
				// getActivity().getSharedPreferences("Id", 0);
				// int brandId = pageId.getInt("brandID", -1);
				// int MainObjID = pageId.getInt("main object id", -1);
				//
				// sendToCreate.edit().putInt("MainObjectId",
				// MainObjID).commit();
				// sendToCreate.edit().putInt("CityId", city_id).commit();
				// sendToCreate.edit().putInt("objectId", brandId).commit();
				// sendToCreate.edit().putInt("IsAgency",
				// AgencyService).commit();
				//
				// }

				final RelativeLayout bottomSheet = (RelativeLayout) rootView.findViewById(R.id.bottmSheet);
				TextView titleSheet = (TextView) rootView.findViewById(R.id.titleSheet);
				Button create = (Button) rootView.findViewById(R.id.createDialogPage);
				ImageView close = (ImageView) rootView.findViewById(R.id.delete);

				titleSheet.setText(message);
				titleSheet.setTextSize(20);
				titleSheet.setLineSpacing(10, 1);
				titleSheet.setTypeface(util.SetFontIranSans());

				create.setTypeface(util.SetFontCasablanca());

				bottomSheet.setVisibility(View.VISIBLE);
				util.ShowFooterAgahi(getActivity(), false, 1);
				createItem.setVisibility(View.INVISIBLE);

				Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
				bottomSheet.startAnimation(anim);

				create.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						if (util.getCurrentUser() == null) {

							Toast.makeText(getActivity(), "ابتدا باید وارد شوید", Toast.LENGTH_SHORT).show();

							Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
							bottomSheet.startAnimation(anim);

							bottomSheet.setVisibility(View.GONE);
							util.ShowFooterAgahi(getActivity(), true, 1);
							createItem.setVisibility(View.VISIBLE);

						} else {
							FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							trans.replace(R.id.content_frame, new CreateIntroductionFragment());
							trans.commit();

							SharedPreferences sendToCreate = getActivity().getSharedPreferences("Id", 0);
							//
							if (mainId == 2 || mainId == 3 || mainId == 4) {

								if (mainId == 2)
									typeList = 0;

								sendToCreate.edit().putInt("MainObjectId", mainId).commit();
								sendToCreate.edit().putInt("CityId", city_id).commit();
								sendToCreate.edit().putInt("objectId", 0).commit();
								sendToCreate.edit().putInt("ObjectTypeId", typeList).commit();

							} else {
								SharedPreferences pageId = getActivity().getSharedPreferences("Id", 0);
								int brandId = pageId.getInt("brandID", -1);
								int MainObjID = pageId.getInt("main object id", -1);

								sendToCreate.edit().putInt("MainObjectId", MainObjID).commit();
								sendToCreate.edit().putInt("CityId", city_id).commit();
								sendToCreate.edit().putInt("objectId", brandId).commit();
								sendToCreate.edit().putInt("IsAgency", AgencyService).commit();

							}
						}

					}
				});

				close.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);
						bottomSheet.startAnimation(anim);

						bottomSheet.setVisibility(View.GONE);
						util.ShowFooterAgahi(getActivity(), true, 1);
						createItem.setVisibility(View.VISIBLE);


					}
				});
			}
		});

	}

	private void getImageProfile() {

		if (userItemId < mylist.size()) {

			objectItem = mylist.get(userItemId);
			String imageProfileServerDate = objectItem.getImage2ServerDate();

			if (imageProfileServerDate == null)
				imageProfileServerDate = "";

			if (getActivity() != null) {

				ImageUpdating = new UpdatingImage(getActivity());
				ImageUpdating.delegate = ObjectFragment.this;
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

	private void onScrollListView() {

		lstObject.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				switch (arg1) {
				case SCROLL_STATE_IDLE: {
					createItem.hide(true);

				}
					break;
				case SCROLL_STATE_TOUCH_SCROLL: {
					createItem.show(true);
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

				if (lastInScreen == totalItemCount) {

					LoadMoreFooter.setVisibility(View.VISIBLE);
					//
					updating = new Updating(getActivity());
					updating.delegate = ObjectFragment.this;
					String[] params = new String[4];
					params[0] = "Object";
					params[1] = setting.getServerDate_Start_Object() != null ? setting.getServerDate_Start_Object()
							: "";
					params[2] = setting.getServerDate_End_Object() != null ? setting.getServerDate_End_Object() : "";

					params[3] = "0";
					updating.execute(params);
					totalItemCountBeforeSwipe = totalItemCount;
					typeRunServer = StaticValues.TypeRunServerForRefreshItems;

				}
			}
		});

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
}
