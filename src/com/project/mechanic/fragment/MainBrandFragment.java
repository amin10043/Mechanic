package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MainBrandFragment extends Fragment {
	DataBaseAdapter adapter;
	int parentId;
	Users CurrentUser;
	Utility util;
	DialogCreatePage dialog;
	ListView lstObject;

	public MainBrandFragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getArguments() != null && getArguments().getString("Id") != null) {
			parentId = Integer.valueOf(getArguments().getString("Id"));
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
		ArrayList<Object> mylist = adapter.getObjectbyParentId(parentId);

		adapter.close();

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

		lstObject = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist, MainBrandFragment.this);

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
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
				// TODO Auto-generated method stub

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
}
