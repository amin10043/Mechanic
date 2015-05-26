package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.entity.Object;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ObjectFragment extends Fragment {

	DataBaseAdapter adapter;
	private Intent intent;
	Users currentUser;
	Utility util;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);

		SharedPreferences sendData = getActivity()
				.getSharedPreferences("Id", 0);
		int id = sendData.getInt("main_Id", -1);
		int city_id = Integer.valueOf(getArguments().getString("cityId"));

		adapter = new DataBaseAdapter(getActivity());
		util = new Utility(getActivity());
		currentUser = util.getCurrentUser();
		RelativeLayout createPage = (RelativeLayout) view
				.findViewById(R.id.relative);
		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(id, city_id);
		adapter.close();

		ListView lstObject = (ListView) view
				.findViewById(R.id.listvCmt_Introduction);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist);

		lstObject.setAdapter(ListAdapter);

		if (currentUser == null) {
			createPage.setVisibility(View.GONE);
		}

		createPage.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame,
						new CreateIntroductionFragment());
				trans.commit();
			}
		});

		return view;
	}

}
