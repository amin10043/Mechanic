package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.CityListAdapter;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;

public class CityFragment extends Fragment {

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_city, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();
		ArrayList<RowMain> mylist = adapter.getAllCityName();
		adapter.close();

		ListView lst1 = (ListView) view.findViewById(R.id.lstCity);
		CityListAdapter ListAdapter = new CityListAdapter(getActivity(),
				R.layout.row_city, mylist);

		lst1.setAdapter(ListAdapter);
		lst1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionFragment());
				trans.commit();
			}
		});

		return view;
	}
}
