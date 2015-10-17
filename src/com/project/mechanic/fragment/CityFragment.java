package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.CityListAdapter;
import com.project.mechanic.entity.City;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment")
public class CityFragment extends Fragment {

	DataBaseAdapter adapter;
	List<City> cityList = null;
	Utility util;

	public CityFragment(List<City> allCity) {
		super();
		cityList = allCity;
	}

	public CityFragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

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
		ListView lstCity = (ListView) view.findViewById(R.id.lstCity);
		CityListAdapter ListAdapter = new CityListAdapter(getActivity(),
				R.layout.row_city, cityList);

		lstCity.setAdapter(ListAdapter);

		// SharedPreferences sendData = getActivity().getSharedPreferences(
		// "Id", 0);
		// int id = sendData.getInt("main_Id", -1);
		util.ShowFooterAgahi(getActivity() , false);

		return view;
	}

	public void onBackPressed() {

		FragmentTransaction trans = getActivity().getSupportFragmentManager()
				.beginTransaction();
		trans.replace(R.id.content_frame, new ProvinceFragment());
		trans.commit();

	}
}