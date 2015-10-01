package com.project.mechanic.PushNotification;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
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

@SuppressLint("ValidFragment") public class CitySelection extends Fragment {
	DataBaseAdapter adapter;
	int ostanId;
	String type;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.city);
		View view = inflater.inflate(R.layout.fragment_city, null);

		adapter = new DataBaseAdapter(getActivity());

//		SharedPreferences sendData = getActivity()
//				.getSharedPreferences("Id", 0);
//		OstanId = sendData.getInt("ostan", -1);

		adapter.open();
		List<City> allItems = adapter.getCitysByProvinceId(ostanId);
		adapter.close();

		ListView lstCity3 = (ListView) view.findViewById(R.id.lstCity);
		CitySelectionAdapter ListAdapter = new CitySelectionAdapter(
				getActivity(), R.layout.row_city, allItems , type);

		lstCity3.setAdapter(ListAdapter);

		return view;
	}

	public CitySelection(String type , int ostanId) {
		this.type = type;
		this.ostanId = ostanId;
	}
}
