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
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.adapter.PapertitleListAdapter;
import com.project.mechanic.entity.Paper;
import com.project.mechanic.entity.Post;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.AsyncInterfaceVisit;
import com.project.mechanic.inter.CommInterface;
import com.project.mechanic.interfaceServer.CountCommentInterface;
import com.project.mechanic.interfaceServer.CountLikeInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetCountComment;
import com.project.mechanic.server.GetCountLike;
import com.project.mechanic.service.Saving;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.ServiceComm;
import com.project.mechanic.service.Updating;
import com.project.mechanic.service.UpdatingVisit;
import com.project.mechanic.utility.Utility;

public class TitlepaperFragment extends Fragment
		implements CommInterface, AsyncInterface, AsyncInterfaceVisit, CountLikeInterface, CountCommentInterface {
	private ImageButton addtitle;
	private DialogPaperTitle dialog;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Paper> mylist = new ArrayList<Paper>();
	// List<Paper> subList;
	// List<Paper> tempList;
	// int i = 0, j = 9;
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

	boolean FindPosition, IsNewPaper = false;
	int beforePosition;
	Paper p;
	ProgressBar progress;
	int visitCounter = 0;
	String titlePaper, descriptionPaper, severDate;
	Map<String, String> params;

	EditText titlePaperEditText, descriptionPaperEditText;
	RelativeLayout bottomSheet, mainSheet;
	ImageView send, close;

	Animation enterFromDown, exitToDown;

	int positionPaper = 0;

	int counterLike = 0;
	int counterComment = 0;
	int pId = 0;

	@SuppressWarnings("unchecked")
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container,
			Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlepaper, null);

		if (getArguments() != null)
			positionPaper = getArguments().getInt("positionPaper");

		init();

		fillListView();

		getMissedItemsFormServer();

		refreshListView();

		onScrollListView();

		createNewItem();

		getCountVisitFromServer();

		getLikeNumberOfPaper();

		if (getActivity() != null) {
			utility.inputCommentAndPickFile(getActivity(), false);
		}

		return view;
	}

	public void updateView(Context context) {

		mdb = new DataBaseAdapter(context);
		View view = ((Activity) context).getLayoutInflater().inflate(R.layout.fragment_titlepaper, null);

		ListView lst = (ListView) view.findViewById(R.id.lstComment);

		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();
		ListAdapter = new PapertitleListAdapter(context, R.layout.raw_froumtitle, mylist, TitlepaperFragment.this);
		lst.setAdapter(ListAdapter);

		ListAdapter.notifyDataSetChanged();
		// LoadMoreFooter.setVisibility(View.INVISIBLE);

	}

	public void updateView() {
		mdb.open();
		mylist.clear();
		mylist = mdb.getAllPaper();
		mdb.close();
		ListAdapter = new PapertitleListAdapter(getActivity(), R.layout.raw_froumtitle, mylist,
				TitlepaperFragment.this);
		lst.setAdapter(ListAdapter);

		ListAdapter.notifyDataSetChanged();
		// LoadMoreFooter.setVisibility(View.INVISIBLE);

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
		if (!"".equals(output) && output != null
				&& !(output.contains("Exception") || output.contains("java") || output.contains("soap"))) {
			utility.parseQuery(output);
			updateView(getActivity());
		} else {
			Toast.makeText(getActivity(), "خطا در بروز رسانی داده های سرور", Toast.LENGTH_SHORT).show();
		}

	}

	@Override
	public void processFinish(String output) {

		if (IsNewPaper == true) {
			if (output.contains("anyType")) {
				// LoadMoreFooter.setVisibility(View.INVISIBLE);
				// lst.removeFooterView(LoadMoreFooter);

			}
			if (swipeLayout != null) {

				swipeLayout.setRefreshing(false);
			}
			// lst.removeHeaderView(LoadMoreFooter);

			// LoadMoreFooter.setVisibility(View.GONE);

			if (output != null && !(output.contains("Exception") || output.contains("java")
					|| output.contains("SoapFault") || output.contains("anyType"))) {

				utility.parseQuery(output);
				mylist.clear();
				mdb.open();
				mylist.addAll(mdb.getAllPaper());
				mdb.close();

				// ListAdapter = new PapertitleListAdapter(getActivity(),
				// R.layout.raw_froumtitle, mylist,
				// TitlepaperFragment.this);
				//
				// lst.setAdapter(ListAdapter);

				// if (FindPosition == false) {
				// lst.setSelection(beforePosition);
				//
				// }
				// LoadMoreFooter.setVisibility(View.INVISIBLE);

				// ListAdapter.notifyDataSetChanged();

				if (ringProgressDialog != null) {
					ringProgressDialog.dismiss();
				}

				// Toast.makeText(getActivity(), "به روز رسانی با موفقیت انجام
				// شد ",
				// Toast.LENGTH_LONG).show();

			}
		} else {

			if (ringProgressDialog != null)
				ringProgressDialog.dismiss();

			int id = -1;
			try {
				id = Integer.valueOf(output);
				mdb.open();
				mdb.insertPapertitletoDb(id, titlePaper, descriptionPaper, CurrentUser.getId(), severDate);
				mdb.close();

				updateView();

			} catch (NumberFormatException ex) {
				if (output != null && !(output.contains("Exception") || output.contains("java"))) {

					Saving saving = new Saving(getActivity());
					params = new LinkedHashMap<String, String>();

					saving.delegate = TitlepaperFragment.this;
					params.put("TableName", "Paper");
					params.put("Title", titlePaper);
					params.put("Context", descriptionPaper);
					params.put("UserId", String.valueOf(CurrentUser.getId()));
					params.put("Date", output);
					params.put("ModifyDate", output);

					params.put("IsUpdate", "0");
					params.put("Id", "0");
					severDate = output;

					saving.execute(params);

					ringProgressDialog = ProgressDialog.show(getActivity(), "", "لطفا منتظر بمانید...", true);

					ringProgressDialog.setCancelable(true);

					new Thread(new Runnable() {

						@Override
						public void run() {

							try {

								Thread.sleep(10000);

							} catch (Exception e) {

							}
						}
					}).start();

				} else {
					Toast.makeText(getActivity(), "خطا در ثبت. پاسخ نا مشخص از سرور", Toast.LENGTH_SHORT).show();
				}
			}

		}

	}

	@Override
	public void resultCountView(String output) {
		if (!output.contains("Exception")) {

			mdb.open();
			mdb.updateCountView("Paper", p.getId(), Integer.valueOf(output));
			mdb.close();
		}
		visitCounter++;
		getCountVisitFromServer();
	}

	private void getCountVisitFromServer() {

		if (visitCounter < mylist.size()) {

			mdb.open();
			p = mylist.get(visitCounter);
			mdb.close();

			UpdatingVisit updateVisit = new UpdatingVisit(getActivity());
			updateVisit.delegate = TitlepaperFragment.this;
			Map<String, String> serv = new LinkedHashMap<String, String>();

			serv.put("tableName", "Visit");
			serv.put("objectId", String.valueOf(p.getId()));
			serv.put("typeId", StaticValues.TypePaperVisit + "");
			updateVisit.execute(serv);

		} else {
			if (getActivity() != null) {

				mdb.open();
				mylist = mdb.getAllPaper();
				mdb.close();
				ListAdapter = new PapertitleListAdapter(getActivity(), R.layout.raw_froumtitle, mylist,
						TitlepaperFragment.this);
				lst.setAdapter(ListAdapter);
			}
		}

	}

	private void init() {

		action = (FloatingActionButton) view.findViewById(R.id.fab);
		lst = (ListView) view.findViewById(R.id.lstComment);

		mdb = new DataBaseAdapter(getActivity());
		utility = new Utility(getActivity());

		CurrentUser = utility.getCurrentUser();

		LoadMoreFooter = getActivity().getLayoutInflater().inflate(R.layout.load_more_footer, null);
		// lst.addFooterView(LoadMoreFooter);
		// LoadMoreFooter.setVisibility(View.INVISIBLE);

		mdb.open();
		setting = mdb.getSettings();
		mdb.close();

		////////////////
		titlePaperEditText = (EditText) view.findViewById(R.id.txtTitleP);
		descriptionPaperEditText = (EditText) view.findViewById(R.id.txttitleDes);

		bottomSheet = (RelativeLayout) view.findViewById(R.id.bottmSheet);
		mainSheet = (RelativeLayout) view.findViewById(R.id.mainSheet);

		enterFromDown = AnimationUtils.loadAnimation(getActivity(), R.anim.down_from_top);
		exitToDown = AnimationUtils.loadAnimation(getActivity(), R.anim.up_from_bottom);

		send = (ImageView) view.findViewById(R.id.createDialogPage);
		close = (ImageView) view.findViewById(R.id.delete);

		titlePaperEditText.setTypeface(utility.SetFontIranSans());
		descriptionPaperEditText.setTypeface(utility.SetFontIranSans());

	}

	private void fillListView() {

		mdb.open();
		mylist = mdb.getAllPaper();
		mdb.close();

		ListAdapter = new PapertitleListAdapter(getActivity(), R.layout.raw_froumtitle, mylist,
				TitlepaperFragment.this);

		lst.setAdapter(ListAdapter);
		lst.setSelection(positionPaper);

	}

	private void getMissedItemsFormServer() {
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
	}

	private void refreshListView() {
		swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				// if (mylist.size() > 0) {
				// if (getActivity() != null) {
				// updating = new Updating(getActivity());
				// updating.delegate = TitlepaperFragment.this;
				// String[] params = new String[4];
				// params[0] = "Paper";
				// params[1] = setting.getServerDate_Start_Paper() != null ?
				// setting.getServerDate_Start_Paper()
				// : "";
				// params[2] = setting.getServerDate_End_Paper() != null ?
				// setting.getServerDate_End_Paper() : "";
				//
				// params[3] = "1";
				// updating.execute(params);
				// IsNewPaper = true;
				// }
				// }
			}
		});
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright, android.R.color.holo_green_light,
				android.R.color.holo_orange_light, android.R.color.holo_red_light);

	}

	private void onScrollListView() {
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
			public void onScroll(AbsListView arg0, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

				int lastInScreen = firstVisibleItem + visibleItemCount;
				//
				if (lastInScreen == totalItemCount) {

					// LoadMoreFooter.setVisibility(View.VISIBLE);
					// if (mylist.size() > 0) {
					// if (getActivity() != null) {
					//
					// //
					// updating = new Updating(getActivity());
					// updating.delegate = TitlepaperFragment.this;
					// String[] params = new String[4];
					// params[0] = "Paper";
					// params[1] = setting.getServerDate_Start_Paper() != null
					// ? setting.getServerDate_Start_Paper() : "";
					// params[2] = setting.getServerDate_End_Paper() != null ?
					// setting.getServerDate_End_Paper()
					// : "";
					//
					// params[3] = "0";
					// updating.execute(params);
					//
					// int countList = ListAdapter.getCount();
					// beforePosition = countList;
					// IsNewPaper = true;
					// }
					// }

					// FindPosition = false;
				}
			}
		});

	}

	private void createNewItem() {

		RelativeLayout.LayoutParams llp = new RelativeLayout.LayoutParams(mainSheet.getLayoutParams());
		llp.width = utility.getScreenwidth();
		llp.height = utility.getScreenwidth() / 2;
		llp.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);

		RelativeLayout.LayoutParams ep = new RelativeLayout.LayoutParams(bottomSheet.getLayoutParams());
		ep.width = utility.getScreenwidth();
		ep.height = LayoutParams.MATCH_PARENT;
		ep.addRule(RelativeLayout.BELOW, R.id.txtTitleP);
		ep.setMargins(10, 10, 10, 10);

		descriptionPaperEditText.setLayoutParams(ep);
		bottomSheet.setLayoutParams(llp);

		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				bottomSheet.startAnimation(enterFromDown);
				bottomSheet.setVisibility(View.VISIBLE);
				action.setVisibility(View.INVISIBLE);

			}
		});

		send.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (CurrentUser == null) {
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید", 0).show();

					bottomSheet.startAnimation(exitToDown);

					bottomSheet.setVisibility(View.GONE);
					action.setVisibility(View.VISIBLE);
				} else {

					titlePaper = titlePaperEditText.getText().toString();
					descriptionPaper = descriptionPaperEditText.getText().toString();

					if (!"".equals(titlePaper) && !"".equals(descriptionPaper)) {

						ServerDate sDate = new ServerDate(getActivity());
						sDate.delegate = TitlepaperFragment.this;
						sDate.execute("");

						titlePaperEditText.setText("");
						descriptionPaperEditText.setText("");

						bottomSheet.startAnimation(enterFromDown);

						bottomSheet.setVisibility(View.GONE);
						action.setVisibility(View.VISIBLE);

					} else {
						Toast.makeText(getActivity(), "پر کردن عنوان و متن الزامی است", 0).show();

					}

				}

			}
		});

		close.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				bottomSheet.startAnimation(exitToDown);

				bottomSheet.setVisibility(View.GONE);
				action.setVisibility(View.VISIBLE);

				titlePaperEditText.setText("");
				descriptionPaperEditText.setText("");
			}
		});
	}

	private void getLikeNumberOfPaper() {

		if (counterLike < mylist.size()) {

			Paper p = mylist.get(counterLike);

			pId = p.getId();

			GetCountLike getCountLike = new GetCountLike(getActivity());
			getCountLike.delegate = TitlepaperFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getLikeInPaperCount");
			items.put("id", String.valueOf(pId));

			getCountLike.execute(items);

		} else {
			getCountComment();
		}

	}

	@Override
	public void ResultCountLike(String output) {

		if (utility.checkError(output) == false) {

			mdb.open();

			mdb.updateCountLike("Paper", pId, Integer.valueOf(output));

			mdb.close();

			mylist.get(counterLike).setCountLike(Integer.valueOf(output));
			ListAdapter.notifyDataSetChanged();

			counterLike++;
			getLikeNumberOfPaper();

		}

	}

	private void getCountComment() {

		if (counterComment < mylist.size()) {

			Paper p = mylist.get(counterComment);

			pId = p.getId();

			GetCountComment get = new GetCountComment(getActivity());
			get.delegate = TitlepaperFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getCommentInPaperCount");
			items.put("id", String.valueOf(pId));

			get.execute(items);

		}
	}

	@Override
	public void ReultCountComment(String output) {

		if (utility.checkError(output) == false) {

			mdb.open();

			mdb.updateCountComment("Paper", pId, Integer.valueOf(output));

			mdb.close();

			mylist.get(counterComment).setCountComment(Integer.valueOf(output));
			ListAdapter.notifyDataSetChanged();

			counterComment++;
			getCountComment();

		}

	}

}