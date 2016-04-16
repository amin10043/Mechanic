package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.City2ListAdapter;
import com.project.mechanic.entity.City;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;

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
public class City2Fragment extends Fragment {

	DataBaseAdapter adapter;
	List<City> cityList = null;

	int mainObjectId;

	public City2Fragment(List<City> allCity, int mainObjectId) {
		super();
		cityList = allCity;
		this.mainObjectId = mainObjectId;
	}

	public City2Fragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// ((MainActivity) getActivity()).setTitle(R.string.city);
		View view = inflater.inflate(R.layout.fragment_city, null);

		adapter = new DataBaseAdapter(getActivity());

//		if (cityList == null) {
//			adapter.open();
//			cityList = adapter.getCitysByProvinceId(1);
//			ArrayList<RowMain> mylist = adapter.getAllCityName();
//			adapter.close();
//		}
		ListView lstCity2 = (ListView) view.findViewById(R.id.lstCity);
		City2ListAdapter ListAdapter = new City2ListAdapter(getActivity(), R.layout.row_city, cityList, mainObjectId);

		lstCity2.setAdapter(ListAdapter);

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
						trans.replace(R.id.content_frame, new Province2Fragment(mainObjectId));
						// trans.addToBackStack(null);
						trans.commit();

						return true;
					}

				return false;
			}
		});

		super.onResume();
	}
}
