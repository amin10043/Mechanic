package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.entity.Anad;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.ExtraSettings;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Province;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.interfaceServer.CountCommentInterface;
import com.project.mechanic.interfaceServer.CountLikeInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetCountComment;
import com.project.mechanic.server.GetCountLike;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class FroumtitleFragment extends Fragment implements GetAsyncInterface, CommInterface, AsyncInterface,
		AsyncInterfaceVisit, CountLikeInterface, CountCommentInterface {
	// private ImageButton addtitle;
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
	// int positionFroum = 0;
	int positionListFroum = 0;

	int counterComment = 0;
	int counterLike = 0;
	int pId = 0;

	private ScrollView verticalScrollview;
	private LinearLayout verticalOuterLayout;

	List<Anad> anadlist;
	int provinceId = -1;
	List<ImageView> imageList = new ArrayList<ImageView>();
	ImageView imageButton;
	private Boolean isFaceDown = true;
	private Timer clickTimer = null;
	private Timer faceTimer = null;
	private ImageView clickedButton = null;
	private TimerTask faceAnimationSchedule;
	private Timer scrollTimer = null;
	private TimerTask clickSchedule;
	private TimerTask scrollerSchedule;

	public int scrollPos = 0;
	public int scrollViewHeight = 0;

	TextView countAnad;
	int count;
	private int verticalScrollMax;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlefrm, null);

		// if (getArguments() != null)
		// positionFroum = getArguments().getInt("positionFroum");

		init();

		fillListView();

		collectUsersDontExist();
		//
		collectUnicUserIds();
		//
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

		getLikeNumberOfFroum();

		addImagesToView();

		totalMethodeScroll();

		getCountVisitFromServer();
		
		if (getActivity() !=null){
			util.inputCommentAndPickFile(getActivity(), false);
		}
		
		return view;
	}

	public void setPostionListFroum(int pos) {
		positionListFroum = pos;

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
		lst.setSelection(positionListFroum);

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

				setPostionListFroum(0);
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

		// addtitle = (ImageButton)
		// view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		action = (FloatingActionButton) view.findViewById(R.id.fab);

		lst = (ListView) view.findViewById(R.id.listVanad);
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);

		mdb = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		Currentuser = util.getCurrentUser();

		mdb.open();
		setting = mdb.getSettings();
		mdb.close();

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.load_more_footer, null);
		// lst.addFooterView(LoadMoreFooter);
		LoadMoreFooter.setVisibility(View.INVISIBLE);

		titleFroumEditText = (EditText) view.findViewById(R.id.txtTitleP);
		descriptionFroumEditText = (EditText) view.findViewById(R.id.txttitleDes);

		bottomSheet = (RelativeLayout) view.findViewById(R.id.bottmSheet);
		mainSheet = (RelativeLayout) view.findViewById(R.id.mainSheet);

		enterFromDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
		exitToDown = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);

		send = (ImageView) view.findViewById(R.id.createDialogPage);
		close = (ImageView) view.findViewById(R.id.delete);

		titleFroumEditText.setTypeface(util.SetFontIranSans());
		descriptionFroumEditText.setTypeface(util.SetFontIranSans());

		verticalScrollview = (ScrollView) view.findViewById(R.id.vertical_scrollview_id);

		verticalOuterLayout = (LinearLayout) view.findViewById(R.id.vertical_outer_layout_id);
		countAnad = (TextView) view.findViewById(R.id.countA);

		if (Currentuser != null) {
			mdb.open();

			City city = mdb.getCityById(Currentuser.getCityId());
			Province pr = mdb.getProvinceById(city.getProvinceId());
			provinceId = pr.getId();

			mdb.close();
		} else {
			provinceId = 0;
		}

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

	private void totalMethodeScroll() {

		ViewTreeObserver vto = verticalOuterLayout.getViewTreeObserver();
		vto.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {

			@SuppressWarnings("deprecation")
			@Override
			public void onGlobalLayout() {
				verticalOuterLayout.getViewTreeObserver().removeGlobalOnLayoutListener(this);
				getScrollMaxAmount();
				startAutoScrolling();
			}

		});
	}

	public void getScrollMaxAmount() {
		int actualWidth = (verticalOuterLayout.getMeasuredHeight() /*- (256 * 3)*/);

		verticalScrollMax = actualWidth;

		scrollPos = returnSavedPosition();

		verticalScrollview.scrollTo(0, scrollPos);

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

	@Override
	public void onResume() {

		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN) {

					if (keyCode == KeyEvent.KEYCODE_BACK) {

						savePosition(provinceId, false);

						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();

						trans.setCustomAnimations(R.anim.push_out_right, R.anim.pull_in_left);
						trans.replace(R.id.content_frame, new MainFragment());
						trans.commit();

						return true;
					}

				}

				return false;
			}
		});
		super.onResume();
	}

	private void refreshListView() {

		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// if (mylist.size() > 0) {
				// if (getActivity() != null) {
				//
				// update = new Updating(getActivity());
				// update.delegate = FroumtitleFragment.this;
				// String[] params = new String[4];
				// params[0] = "Froum";
				// params[1] = setting.getServerDate_Start_Froum() != null ?
				// setting.getServerDate_Start_Froum()
				// : "";
				// params[2] = setting.getServerDate_End_Froum() != null ?
				// setting.getServerDate_End_Froum() : "";
				// params[3] = "1";
				// update.execute(params);
				//
				// IsNewFroum = true;
				// }
				//
				// }
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

					// LoadMoreFooter.setVisibility(View.VISIBLE);

					// if (mylist.size() > 0) {
					// if (getActivity() != null) {
					//
					// update = new Updating(getActivity());
					// update.delegate = FroumtitleFragment.this;
					// String[] params = new String[4];
					// params[0] = "Froum";
					// params[1] = setting.getServerDate_Start_Froum() != null
					// ? setting.getServerDate_Start_Froum() : "";
					// params[2] = setting.getServerDate_End_Froum() != null ?
					// setting.getServerDate_End_Froum()
					// : "";
					// params[3] = "0";
					// update.execute(params);
					// IsNewFroum = true;
					//
					// }
					// }
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

	private void getLikeNumberOfFroum() {

		if (counterLike < mylist.size()) {

			Froum p = mylist.get(counterLike);

			pId = p.getId();

			GetCountLike getCountLike = new GetCountLike(getActivity());
			getCountLike.delegate = FroumtitleFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getLikeInFroumCount");
			items.put("id", String.valueOf(pId));

			getCountLike.execute(items);

		} else {
			getCountComment();
		}

	}

	@Override
	public void ResultCountLike(String output) {

		if (util.checkError(output) == false) {

			mdb.open();

			mdb.updateCountLike("Froum", pId, Integer.valueOf(output));

			mdb.close();

			mylist.get(counterLike).setCountLike(Integer.valueOf(output));
			ListAdapter.notifyDataSetChanged();

			counterLike++;
			getLikeNumberOfFroum();

		}

	}

	private void getCountComment() {

		if (counterComment < mylist.size()) {

			Froum p = mylist.get(counterComment);

			pId = p.getId();

			GetCountComment get = new GetCountComment(getActivity());
			get.delegate = FroumtitleFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getCommentInFroumCount");
			items.put("id", String.valueOf(pId));

			get.execute(items);

		}
	}

	@Override
	public void ReultCountComment(String output) {

		if (util.checkError(output) == false) {

			mdb.open();

			mdb.updateCountComment("Froum", pId, Integer.valueOf(output));

			mdb.close();

			mylist.get(counterComment).setCountComment(Integer.valueOf(output));
			ListAdapter.notifyDataSetChanged();

			counterComment++;
			getCountComment();

		}

	}

	public List<ImageView> addImagesToView() {

		mdb.open();
		anadlist = mdb.getAnadtByTypeIdProId(provinceId);
		mdb.close();

		imageList.clear();
		verticalOuterLayout.removeAllViewsInLayout();

		for (int t = 0; t < anadlist.size(); t++) {
			// byte[] tmpImage = lst.get(t).getImage();
			String imagePath = anadlist.get(t).getImagePath();

			// final ImageView
			imageButton = new ImageView(getActivity());
			if (imagePath == null) {
				Drawable image = this.getResources().getDrawable(R.drawable.propagand);

				imageButton.setImageDrawable(image);
				imageButton.setScaleType(ScaleType.FIT_XY);

			} else {
				imageButton.setImageBitmap(BitmapFactory.decodeFile(imagePath));

			}
			imageButton.setTag(t);

			imageButton.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {

					int position = (Integer) arg0.getTag();
					if (isFaceDown) {
						if (clickTimer != null) {
							clickTimer.cancel();
							clickTimer = null;
						}
						clickedButton = (ImageView) arg0;

						stopAutoScrolling();
						clickedButton.startAnimation(scaleFaceUpAnimation());
						clickedButton.setSelected(true);
						clickTimer = new Timer();

						if (clickSchedule != null) {
							clickSchedule.cancel();
							clickSchedule = null;
						}

						clickSchedule = new TimerTask() {
							public void run() {
								startAutoScrolling();
							}
						};
						Anad t = anadlist.get(position);

						int objectId = t.getObjectId();
						clickTimer.schedule(clickSchedule, 1500);

						if (objectId == 0) {
							if (Currentuser == null) {
								Toast.makeText(getActivity(), " برای ثبت تبلیغات وارد شوید", Toast.LENGTH_LONG).show();
								return;
							} else {

								DialogAnadimg dialog1 = new DialogAnadimg(getActivity(), R.layout.dialog_imganad,
										FroumtitleFragment.this, 1, provinceId, t.getId(),
										StaticValues.CreateAnadFromFroum);

								util.dialogCreateAnadInFroum(dialog1);

								// util.setSizeDialog(dialog1);'
								// bottomSheetView(t.getId(), objectId);
								//
								// bottomSheet.setVisibility(View.VISIBLE);
								// // createItem.setVisibility(View.INVISIBLE);
								//
								// Animation anim =
								// AnimationUtils.loadAnimation(getActivity(),
								// R.anim.down_from_top);
								// bottomSheet.startAnimation(anim);

								savePosition(provinceId, false);

							}
						} else {
							// Toast.makeText(getActivity(), " عکس قبلا انتخاب
							// شده",
							// Toast.LENGTH_LONG).show();
							FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
									.beginTransaction();
							IntroductionFragment fragment = new IntroductionFragment(-1);
							Bundle bundle = new Bundle();
							bundle.putString("Id", String.valueOf(t.getObjectId()));
							// bundlei.putString("I",
							// String.valueOf(t.getObjectId()));
							fragment.setArguments(bundle);
							trans.replace(R.id.content_frame, fragment);
							trans.addToBackStack(null);
							trans.commit();

							savePosition(provinceId, false);

						}

					}
				}

			});

			LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(util.getScreenwidth() / 3,
					util.getScreenwidth() / 3);
			imageButton.setLayoutParams(params);
			imageButton.setScaleType(ScaleType.FIT_XY);
			verticalOuterLayout.addView(imageButton);
			imageList.add(imageButton);
		}
		// startShowImageAfterDownload = true;
		return imageList;

	}

	public Animation scaleFaceUpAnimation() {
		Animation scaleFace = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleFace.setDuration(500);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
				isFaceDown = false;
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				if (faceTimer != null) {
					faceTimer.cancel();
					faceTimer = null;
				}
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				if (faceTimer != null) {
					faceTimer.cancel();
					faceTimer = null;
				}

				faceTimer = new Timer();
				if (faceAnimationSchedule != null) {
					faceAnimationSchedule.cancel();
					faceAnimationSchedule = null;
				}
				faceAnimationSchedule = new TimerTask() {
					@Override
					public void run() {
						faceScaleHandler.sendEmptyMessage(0);
					}
				};

				faceTimer.schedule(faceAnimationSchedule, 750);
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}

	public void stopAutoScrolling() {
		if (scrollTimer != null) {
			scrollTimer.cancel();
			scrollTimer = null;
		}
	}

	private Handler faceScaleHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {

			if (clickedButton.isSelected() == true)
				clickedButton.startAnimation(scaleFaceDownAnimation(500));
		}
	};

	public void moveScrollView() {

		scrollPos = (int) (verticalScrollview.getScrollY() + 1.0);
		scrollViewHeight = verticalScrollview.getHeight();

		if (imageButton.getHeight() > 0) {

			int space = scrollViewHeight % imageButton.getHeight();

			if (space > 0)
				count = ((scrollPos + scrollViewHeight - space) / imageButton.getHeight()) + 1;
			else
				count = ((scrollPos + scrollViewHeight) / imageButton.getHeight());

			if ((scrollPos + scrollViewHeight) >= verticalScrollMax) {

				scrollPos = 0;
				verticalScrollview.scrollTo(0, scrollPos);
				savePosition(provinceId, true);
				;
			}

		}

		if (anadlist.size() > 0)
			countAnad.setText(count + " / " + anadlist.size());

		verticalScrollview.scrollTo(0, scrollPos);

	}

	public void startAutoScrolling() {
		if (scrollTimer == null) {
			scrollTimer = new Timer();
			final Runnable Timer_Tick = new Runnable() {
				public void run() {
					moveScrollView();
				}
			};

			if (scrollerSchedule != null) {
				scrollerSchedule.cancel();
				scrollerSchedule = null;
			}
			scrollerSchedule = new TimerTask() {
				@Override
				public void run() {
					getActivity().runOnUiThread(Timer_Tick);
				}
			};

			scrollTimer.schedule(scrollerSchedule, 1000, 20);
		}
	}

	public void savePosition(int provinceId, boolean flag) {

		String[] positionArray = ArrayPosition();

		String p = "";

		if (flag == false) {

			for (int x = 0; x < positionArray.length; x++)
				if (x == provinceId) {
					positionArray[x] = String.valueOf(scrollPos);
					break;
				}

		} else {
			for (int x = 0; x < positionArray.length; x++)
				if (x == provinceId) {
					positionArray[x] = String.valueOf(0);
					break;
				}
		}

		for (int a = 0; a < positionArray.length; a++)
			p += positionArray[a] + "-";

		mdb.open();
		mdb.updatePositionScrollAnad(p);
		mdb.close();

	}

	public String[] ArrayPosition() {

		ExtraSettings extraSettings = null;
		String[] positionArray = new String[32];

		mdb.open();
		extraSettings = mdb.getPositionScroll();
		mdb.close();

		String tempPosition = extraSettings.getValue();

		positionArray = tempPosition.split("-");

		return positionArray;

	}

	public int returnSavedPosition() {

		ExtraSettings extraSettings = null;
		String[] positionArray = new String[32];

		mdb.open();
		extraSettings = mdb.getPositionScroll();
		mdb.close();

		String tempPosition = extraSettings.getValue();

		positionArray = tempPosition.split("-");

		if (positionArray[provinceId] != null && !positionArray[provinceId].equals("-")) {

			scrollPos = Integer.valueOf(positionArray[provinceId]);
		} else
			scrollPos = 0;

		return scrollPos;

	}

	public Animation scaleFaceDownAnimation(int duration) {
		Animation scaleFace = new ScaleAnimation(1.2f, 1.0f, 1.2f, 1.0f, Animation.RELATIVE_TO_SELF, 0.5f,
				Animation.RELATIVE_TO_SELF, 0.5f);
		scaleFace.setDuration(duration);
		scaleFace.setFillAfter(true);
		scaleFace.setInterpolator(new AccelerateInterpolator());
		Animation.AnimationListener scaleFaceAnimationListener = new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation arg0) {
			}

			@Override
			public void onAnimationRepeat(Animation arg0) {
				// verticalTextView.setText("");
				isFaceDown = true;
			}

			@Override
			public void onAnimationEnd(Animation arg0) {
				// verticalTextView.setText("");
				isFaceDown = true;
			}
		};
		scaleFace.setAnimationListener(scaleFaceAnimationListener);
		return scaleFace;
	}
}
