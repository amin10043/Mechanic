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
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ObjectFragment extends Fragment {

	DataBaseAdapter adapter;
	Users currentUser;
	Utility util;
	DialogCreatePage dialog;
	ListView lstObject;
	int city_id, m;

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

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();

		lstObject = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		adapter.open();
		if (id == 2 || id == 3 || id == 4) {
			// Toast.makeText(getActivity(), "come from main", 0).show();
			ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(id,
					city_id);
			ObjectListAdapter ListAdapter = new ObjectListAdapter(
					getActivity(), R.layout.row_object, mylist,
					ObjectFragment.this);
			lstObject.setAdapter(ListAdapter);

		} else {
			SharedPreferences pageId = getActivity().getSharedPreferences("Id",
					0);
			int brand = pageId.getInt("brandID", -1);

			// Toast.makeText(getActivity(), "come from agency", 0).show();

			ArrayList<Object> mylist = adapter.subBrandObject(brand, city_id);

			ObjectListAdapter ListAdapter = new ObjectListAdapter(
					getActivity(), R.layout.row_object, mylist,
					ObjectFragment.this);
			lstObject.setAdapter(ListAdapter);

		}

		adapter.close();

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
			public void onScroll(AbsListView arg0, int arg1, int arg2, int arg3) {
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

					sendToCreate.edit().putInt("MainObjectId", id).commit();
					sendToCreate.edit().putInt("CityId", city_id).commit();
					sendToCreate.edit().putInt("objectId", 0).commit();

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

		return view;
	}

	public void UpdateList() {
		adapter.open();

		ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(m, city_id);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist, ObjectFragment.this);
		lstObject.setAdapter(ListAdapter);

		ListAdapter.notifyDataSetChanged();

		adapter.close();
	}

	public int getCityId() {
		return city_id;
	}
}
