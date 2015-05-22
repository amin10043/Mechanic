package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.City3ListAdapter;
import com.project.mechanic.entity.City;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;

@SuppressLint("ValidFragment")
public class City3Fragment extends Fragment {

	DataBaseAdapter adapter;
	List<City> cityList = null;

	public City3Fragment(List<City> allCity) {
		super();
		cityList = allCity;
	}

	public City3Fragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//((MainActivity) getActivity()).setActivityTitle(R.string.city);
		View view = inflater.inflate(R.layout.fragment_city, null);

		adapter = new DataBaseAdapter(getActivity());

		if (cityList == null) {
			adapter.open();
			cityList = adapter.getCitysByProvinceId(1);
			ArrayList<RowMain> mylist = adapter.getAllCityName();
			adapter.close();
		}
		ListView lstCity3 = (ListView) view.findViewById(R.id.lstCity);
		City3ListAdapter ListAdapter = new City3ListAdapter(getActivity(),
				R.layout.row_city, cityList);

		lstCity3.setAdapter(ListAdapter);

		return view;
	}
}
