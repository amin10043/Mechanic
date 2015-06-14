package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.inter.AsyncInterface;
import com.project.mechanic.inter.GetAsyncInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.service.ServerDate;
import com.project.mechanic.service.UpdatingImage;
import com.project.mechanic.utility.Utility;

public class FroumtitleFragment extends Fragment implements GetAsyncInterface,
		AsyncInterface {
	private ImageButton addtitle;
	private DialogfroumTitle dialog;
	DialogcmtInfroum dialog2;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Froum> mylist;
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
	Users u;
	FloatingActionButton action;
	RelativeLayout header;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		view = inflater.inflate(R.layout.fragment_titlefrm, null);
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);
		action = (FloatingActionButton) view.findViewById(R.id.fab);
		header = (RelativeLayout) view.findViewById(R.id.re);
		header.setVisibility(View.GONE);

		action.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				if (Currentuser == null)
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				else {

					dialog = new DialogfroumTitle(getActivity(),
							R.layout.dialog_addtitle, FroumtitleFragment.this);
					dialog.show();
				}

			}
		});

		mdb = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());

		Currentuser = util.getCurrentUser();

		mdb.open();
		mylist = mdb.getAllFroum();

		mdb.close();

		date = new ServerDate(getActivity());
		date.delegate = this;
		date.execute("");

		addtitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				if (Currentuser == null)
					Toast.makeText(getActivity(), "ابتدا باید وارد شوید",
							Toast.LENGTH_SHORT).show();
				else {

					dialog = new DialogfroumTitle(getActivity(),
							R.layout.dialog_addtitle, FroumtitleFragment.this);
					dialog.show();
				}
			}
		});

		lst = (ListView) view.findViewById(R.id.lstComment);
		ListAdapter = new FroumtitleListadapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		lst.setAdapter(ListAdapter);

		lst.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView arg0, int arg1) {
				switch (arg1) {
				case SCROLL_STATE_FLING:
					action.setVisibility(View.GONE);
					break;
				case SCROLL_STATE_IDLE:
					action.setVisibility(View.VISIBLE);
					break;
				case SCROLL_STATE_TOUCH_SCROLL:
					action.setVisibility(View.GONE);
					break;
				}

			}

			@Override
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {

			}
		});

		return view;
	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllFroum();
		mdb.close();
		ListAdapter = new FroumtitleListadapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);

	}

	@Override
	public void processFinish(byte[] output) {

		Froum f;
		mdb.open();
		// Users u;
		if (output != null) {
			f = mylist.get(userItemId);
			mdb.UpdateUserImage(f.getUserId(), output, serverDate);

			ListAdapter.notifyDataSetChanged();
		}

		Users u = mdb.getUserById(mylist.get(userItemId).getUserId());
		userItemId++;

		if (userItemId < mylist.size() && getActivity() != null) {
			updating = new UpdatingImage(getActivity());
			updating.delegate = this;
			maps = new LinkedHashMap<String, String>();
			maps.put("tableName", "Users");
			maps.put("Id", String.valueOf(u.getId()));
			maps.put("fromDate", u.getImageServerDate());
			updating.execute(maps);
		}
		mdb.close();
	}

	@Override
	public void processFinish(String output) {
		if (!"".equals(output) && output != null) {
			if (mylist != null && mylist.size() > 0) {
				mdb.open();
				u = mdb.getUserById(mylist.get(userItemId).getUserId());
				mdb.close();
				serverDate = output; // agar khata dashte bashad !
				if (getActivity() != null) {
					updating = new UpdatingImage(getActivity());
					updating.delegate = this;
					maps = new LinkedHashMap<String, String>();
					maps.put("tableName", "Users");
					maps.put("Id", String.valueOf(u.getId()));
					maps.put("fromDate", u.getImageServerDate());
					updating.execute(maps);
				}
			}
		}
	}
}
