package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.City3ListAdapter;
import com.project.mechanic.entity.City;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class City3Fragment extends Fragment {

	DataBaseAdapter adapter;
	List<City> cityList = null;
	Utility util;

	int mainObjectId;

	public City3Fragment(List<City> allCity, int mainObjectId) {
		super();
		cityList = allCity;
		this.mainObjectId = mainObjectId;
	}

	public City3Fragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.city);
		View view = inflater.inflate(R.layout.fragment_city, null);

		util = new Utility(getActivity());
		adapter = new DataBaseAdapter(getActivity());

		if (cityList == null) {
			adapter.open();
			cityList = adapter.getCitysByProvinceId(1);
			ArrayList<RowMain> mylist = adapter.getAllCityName();
			adapter.close();
		}
		ListView lstCity3 = (ListView) view.findViewById(R.id.lstCity);
		City3ListAdapter ListAdapter = new City3ListAdapter(getActivity(), R.layout.row_city, cityList, mainObjectId);

		lstCity3.setAdapter(ListAdapter);

		return view;
	}

	@Override
	public void onResume() {

		getView().setFocusableInTouchMode(true);
		getView().requestFocus();
		getView().setOnKeyListener(new OnKeyListener() {

			@Override
			public boolean onKey(View arg0, int keyCode, KeyEvent event) {

				if (event.getAction() == KeyEvent.ACTION_DOWN)
					if (keyCode == KeyEvent.KEYCODE_BACK) {
						
						FragmentTransaction trans = ((MainActivity) getActivity()).getSupportFragmentManager()
								.beginTransaction();
						trans.setCustomAnimations(R.anim.push_out_right, R.anim.pull_in_left);

						trans.replace(R.id.content_frame, new Province3Fragment(mainObjectId));
						trans.commit();
						
						
						

						return true;
					}

				return false;
			}
		});
		super.onResume();
	}

}
