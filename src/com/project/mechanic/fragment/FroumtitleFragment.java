package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.EditText;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.adapter.PapertitleListAdapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

public class FroumtitleFragment extends Fragment
		implements GetAsyncInterface, CommInterface, AsyncInterface, AsyncInterfaceVisit {
	private ImageButton addtitle;
	private DialogfroumTitle dialog;
	DialogcmtInfroum dialog2;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Froum> mylist = new ArrayList<Froum>();
	ListView lst;
	FroumtitleListadapter ListAdapter;
	ImageButton Replytocm;
	public static final int DIALOG_FRAGMENT = 1;
	Utility util;
	Users Currentuser;
	UpdatingImage updating;
	Map<String, String> maps;
	int userItemId = 0;
	String serverDate = "";
	ServerDate date;
	// Users u;
	FloatingActionButton action;
	int mLastFirstVisibleItem = 0;
	List<Integer> ids = new ArrayList<Integer>();
	List<Integer> missedIds = new ArrayList<Integer>();
	ServiceComm service;

	Updating update;
	Settings setting;

	SwipeRefreshLayout swipeLayout;
	View LoadMoreFooter;
	Froum f;
	int visitCounter = 0;
	String titleFroum, descriptionFroum, severDate;
	boolean IsNewFroum = false;
	boolean getDate = false;

	ProgressDialog ringProgressDialog;
	Map<String, String> params;
	int counterUser = 0;
	Users user;

	EditText titleFroumEditText, descriptionFroumEditText;
	RelativeLayout bottomSheet, mainSheet;
	ImageView send, close;

	Animation enterFromDown, exitToDown;
	int positionFroum = 0;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlefrm, null);
		if (getArguments() !=null)
			positionFroum = getArguments().getInt("positionFroum");
		
		init();

		fillListView();

		collectUsersDontExist();

		collectUnicUserIds();

		getMissedItemsFormServer();

		// serverDate();

		refreshListView();

		onScrollListView();

		// final SharedPreferences realize =
		// getActivity().getSharedPreferences("Id", 0);

		createNewItem();

		// if (getArguments() != null) {
		// mLastFirstVisibleItem = getArguments().getInt("Froum_List_Id");
		// lst.setSelection(mLastFirstVisibleItem);
		// }

		if (getActivity() != null) {
			util.ShowFooterAgahi(getActivity(), false, 7);

		}

		getCountVisitFromServer();
		return view;
	}

	public void fillListView() {

		mdb.open();
		mylist.clear();
		mylist = mdb.getAllFroum();
		mdb.close();
		ListAdapter = new FroumtitleListadapter(getActivity(), R.layout.raw_froumtitle, mylist,
				FroumtitleFragment.this);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);
		lst.setSelection(positionFroum);

		LoadMoreFooter.setVisibility(View.GONE);

	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(byte[] output) {

		if (output != null && mylist != null && mylist.size() > userItemId) {
			// f = mylist.get(userItemId);

			util.CreateFile(output, user.getId(), "Mechanical", "Users", "user", "Users");

			ListAdapter.notifyDataSetChanged();
			LoadMoreFooter.setVisibility(View.GONE);

		}

		userItemId++;
		getImageUsers();
		LoadMoreFooter.setVisibility(View.GONE);

		//
		//
		// if (userItemId < mylist.size() && getActivity() != null) {
		// Users u = mdb.getUserById(mylist.get(userItemId).getUserId());
		// if (u != null && !ids.contains(u.getId())) {
		// ids.add(u.getId());
		// if (getActivity() != null) {
		//
		// updating = new UpdatingImage(getActivity());
		// updating.delegate = this;
		// maps = new LinkedHashMap<String, String>();
		// maps.put("tableName", "Users");
		// maps.put("Id", String.valueOf(u.getId()));
		// maps.put("fromDate", u.getImageServerDate());
		// updating.execute(maps);
		// }
		// } else {
		// byte[] b = null;
		// processFinish(b);
		// }
		// }
		//
		// mdb.close();
	}

	@SuppressWarnings("unchecked")
	@Override
	public void processFinish(String output) {

		if (IsNewFroum == true) {

			if (!"".equals(output) && output != null
					&& !(output.contains("Exception") || output.contains("java") || output.contains("soap"))) {

				getImageUsers();

				//
				// if (mylist != null && mylist.size() > 0) {
				// mdb.open();
				// if (mylist.size() > userItemId) {
				// u = mdb.getUserById(mylist.get(userItemId).getUserId());
				//
				// String imageDate = "";
				// if (u.getImageServerDate() == null)
				// imageDate = "";
				//
				// if (u != null) {
				// ids.add(u.getId());
				// serverDate = output;
				// if (getActivity() != null) {
				// updating = new UpdatingImage(getActivity());
				// updating.delegate = this;
				// maps = new LinkedHashMap<String, String>();
				// maps.put("tableName", "Users");
				// maps.put("Id", String.valueOf(u.getId()));
				// maps.put("fromDate", imageDate);
				// updating.execute(maps);
				//
				// if (swipeLayout != null)
				// swipeLayout.setRefreshing(false);
				// LoadMoreFooter.setVisibility(View.INVISIBLE);
				//
				// }
				// } else {
				//
				// byte[] b = null;
				// processFinish(b);
				// }
				// }
				// mdb.close();
				// ListAdapter.notifyDataSetChanged();
				//
				// }
				if (output.contains("anyType")) {
					LoadMoreFooter.setVisibility(View.GONE);
					// lst.removeFooterView(LoadMoreFooter);

				}
				if (swipeLayout != null)
					swipeLayout.setRefreshing(false);
				LoadMoreFooter.setVisibility(View.GONE);

			}
		} else {

			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();
			int id = -1;
			try {
				id = Integer.valueOf(output);
				mdb.open();
				mdb.insertFroumtitletoDb(id, titleFroum, descriptionFroum, util.getCurrentUser().getId(), severDate);
				mdb.close();

				fillListView();

			} catch (NumberFormatException ex) {
				if (output != null && !(output.contains("Exception") || output.contains("java"))) {
					params = new LinkedHashMap<String, String>();
					Saving saving = new Saving(getActivity());
					saving.delegate = FroumtitleFragment.this;

					params.put("TableName", "Froum");
					params.put("Title", titleFroum);
					params.put("Description", descriptionFroum);
					params.put("UserId", String.valueOf(util.getCurrentUser().getId()));
					params.put("Date", output);
					params.put("ModifyDate", output);
					params.put("IsUpdate", "0");
					params.put("Id", "0");
					severDate = output;
					saving.execute(params);
					ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);
				} else {
					Toast.makeText(getActivity(), "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
				}
			}
		}

	}

	@Override
	public void CommProcessFinish(String output) {

		if (getDate == true) {

			if (!"".equals(output) && output != null
					&& !(output.contains("Exception") || output.contains("java") || output.contains("soap"))) {
				util.parseQuery(output);
				fillListView();
				if (swipeLayout != null)
					swipeLayout.setRefreshing(false);

			} else {
				if (swipeLayout != null)
					swipeLayout.setRefreshing(false);
				Toast.makeText(getActivity(), "خطا در بروز رسانی داده های سرور", Toast.LENGTH_SHORT).show();
			}

			counterUser++;
			getMissedItemsFormServer();
		} else {

			if (output.contains("Exception") || output.contains(("anyType")))
				output = "";

			mdb.open();
			mdb.UpdateImageServerDate(user.getId(), "Users", output);
			mdb.close();

			userItemId++;

			getDateImage();

		}
	}

	private void getCountVisitFromServer() {

		if (visitCounter < mylist.size()) {

			mdb.open();
			f = mylist.get(visitCounter);
			mdb.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = FroumtitleFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(f.getId()));
			serv.put("typeId", StaticValues.TypeFroumVist + "");
			updateVisit.execute(serv);

		} else {
			if (getActivity() != null) {

				fillListView();
			}
		}

	}

	@Override
	public void resultCountView(String output) {
		if (!output.contains("Exception")) {

			mdb.open();
			mdb.updateCountView("Froum", f.getId(), Integer.valueOf(output));
			mdb.close();
		}
		visitCounter++;
		getCountVisitFromServer();
	}

	private void init() {

		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		action = (FloatingActionButton) view.findViewById(R.id.fab);

		lst = (ListView) view.findViewById(R.id.lstComment);
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

		mdb = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		Currentuser = util.getCurrentUser();

		mdb.open();
		setting = mdb.getSettings();
		mdb.close();

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.load_more_footer, null);
//		lst.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		titleFroumEditText = (EditText) view.findViewById(R.id.txtTitleP);
		descriptionFroumEditText = (EditText) view.findViewById(R.id.txttitleDes);

		bottomSheet = (RelativeLayout) view.findViewById(R.id.bottmSheet);
		mainSheet = (RelativeLayout) view.findViewById(R.id.mainSheet);

		enterFromDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
		exitToDown = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);

		send = (ImageView) view.findViewById(R.id.createDialogPage);
		close = (ImageView) view.findViewById(R.id.delete);

	}

	private void getMissedItemsFormServer() {

		if (missedIds.size() > 0) {

			if (counterUser < missedIds.size()) {

				int id = missedIds.get(counterUser);

				if (getActivity() != null) {

					service = new ServiceComm(getActivity());
					service.delegate = this;
					Map<String, String> items = new LinkedHashMap<String, String>();
					items.put("tableName", "getUserById");
					items.put("Id", String.valueOf(id));

					service.execute(items);
					getDate = true;
				}

			} else {
				getImageUsers();
			}
		} else
			getImageUsers();

		// String strIdes = getMissedIds();

		// if (!"".equals(strIdes)) {
		//
		// if (getActivity() != null) {
		//
		// service = new ServiceComm(getActivity());
		// service.delegate = this;
		// Map<String, String> items = new LinkedHashMap<String, String>();
		// items.put("tableName", "getUserById");
		// items.put("Id", strIdes);
		//
		// service.execute(items);
		// }
		// }
	}

	// public List<Integer> getMissedIds() {
	//
	// String strIdes = "";
	// if (mylist != null) {
	// mdb.open();
	// Users uId;
	// for (int i = 0; i < mylist.size(); ++i) {
	// int uidd = mylist.get(i).getUserId();
	// uId = mdb.getUserById(uidd);
	// if (uId == null) {
	// missedIds.add(uidd);
	// strIdes += uidd + "-";
	// }
	// }
	// mdb.close();
	// }
	//
	// return strIdes;
	//
	// }

	private void refreshListView() {

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				if (getActivity() != null) {

					update = new Updating(getActivity());
					update.delegate = FroumtitleFragment.this;
					String[] params = new String[4];
					params[0] = "Froum";
					params[1] = setting.getServerDate_Start_Froum() != null ? setting.getServerDate_Start_Froum() : "";
					params[2] = setting.getServerDate_End_Froum() != null ? setting.getServerDate_End_Froum() : "";
					params[3] = "1";
					update.execute(params);

					IsNewFroum = true;

				}
			}
		});
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);

	}

	private void onScrollListView() {

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

			public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;

				if (lastInScreen == totalItemCount) {

//					LoadMoreFooter.setVisibility(View.VISIBLE);

					if (getActivity() != null) {

						update = new Updating(getActivity());
						update.delegate = FroumtitleFragment.this;
						String[] params = new String[4];
						params[0] = "Froum";
						params[1] = setting.getServerDate_Start_Froum() != null ? setting.getServerDate_Start_Froum()
								: "";
						params[2] = setting.getServerDate_End_Froum() != null ? setting.getServerDate_End_Froum() : "";
						params[3] = "0";
						update.execute(params);
						IsNewFroum = true;

					}
				}

			}
		});
	}

	private void createNewItem() {

		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(mainSheet.getLayoutParams());
		llp.width = util.getScreenwidth();
		llp.height = util.getScreenwidth() / 2;
		llp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		RelativeLayout.LayoutParams ep = new RelativeLayout.LayoutParams(bottomSheet.getLayoutParams());
		ep.width = util.getScreenwidth();
		ep.height = LayoutParams.MATCH_PARENT;
		ep.addRule(RelativeLayout.BELOW, R.id.txtTitleP);
		ep.setMargins(10, 10, 10, 10);

		descriptionFroumEditText.setLayoutParams(ep);
		bottomSheet.setLayoutParams(llp);

		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				bottomSheet.startAnimation(enterFromDown);
				bottomSheet.setVisibility(View.VISIBLE);
				action.setVisibility(View.INVISIBLE);

			}
		});
		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				bottomSheet.startAnimation(exitToDown);

				bottomSheet.setVisibility(View.GONE);
				action.setVisibility(View.VISIBLE);
				
				titleFroumEditText.setText("");
				descriptionFroumEditText.setText("");
			}
		});
		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (util.getCurrentUser() == null) {

					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0).show();

					bottomSheet.startAnimation(enterFromDown);
					bottomSheet.setVisibility(View.GONE);
					action.setVisibility(View.VISIBLE);

				} else {

					titleFroum = titleFroumEditText.getText().toString();
					descriptionFroum = descriptionFroumEditText.getText().toString();

					if (!"".equals(titleFroum) && !"".equals(descriptionFroum)) {

						ServerDate sDate = new ServerDate(getActivity());
						sDate.delegate = FroumtitleFragment.this;
						sDate.execute("");

						titleFroumEditText.setText("");
						descriptionFroumEditText.setText("");

						bottomSheet.startAnimation(enterFromDown);

						bottomSheet.setVisibility(View.GONE);
						action.setVisibility(View.VISIBLE);

					} else {
						Toast.makeText(getActivity(), "پر کردن عنوان و متن الزامی است", 0).show();

					}

				}

			}
		});

	}

	private void serverDate() {
		if (getActivity() != null) {

			date = new ServerDate(getActivity());
			date.delegate = this;
			date.execute("");

			IsNewFroum = true;
		}
	}

	private void collectUsersDontExist() {

		List<Integer> userIds = new ArrayList<Integer>();

		missedIds.clear();

		if (mylist != null) {
			mdb.open();
			Users uId;
			for (int i = 0; i < mylist.size(); ++i) {
				int uidd = mylist.get(i).getUserId();
				uId = mdb.getUserById(uidd);
				if (uId == null) {
					userIds.add(uidd);
				}
			}
			mdb.close();
		}
		if (userIds.size() > 0)
			missedIds = util.createUnicId(userIds);

	}

	private void collectUnicUserIds() {

		List<Integer> userIds = new ArrayList<Integer>();

		if (mylist != null)
			for (int i = 0; i < mylist.size(); i++)
				userIds.add(mylist.get(i).getUserId());

		ids = util.createUnicId(userIds);

	}

	private void getDateImage() {

		if (userItemId < ids.size()) {

			mdb.open();
			user = mdb.getUserById(ids.get(userItemId));
			mdb.close();

			if (getActivity() != null) {

				ServiceComm getDateService = new ServiceComm(getActivity());
				getDateService.delegate = FroumtitleFragment.this;
				Map<String, String> items = new LinkedHashMap<String, String>();

				items.put("tableName", "getUserImageDate");
				items.put("Id", String.valueOf(user.getId()));

				getDateService.execute(items);

				getDate = false;

			}

		}

	}

	private void getImageUsers() {

		if (mylist != null) {

			if (userItemId < ids.size()) {

				mdb.open();
				user = mdb.getUserById(ids.get(userItemId));
				mdb.close();

				String userDate = user.getImageServerDate();
				if (userDate == null)
					userDate = "";

				if (getActivity() != null) {

					updating = new UpdatingImage(getActivity());
					updating.delegate = this;
					maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Users");
					maps.put("Id", String.valueOf(user.getId()));
					maps.put("fromDate", userDate);
					updating.execute(maps);
				}

			} else {
				userItemId = 0;
				getDateImage();
			}

		}

	}
}
