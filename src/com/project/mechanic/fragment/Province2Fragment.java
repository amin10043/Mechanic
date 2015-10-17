package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.Province2ListAdapter;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class Province2Fragment extends Fragment {
	Utility util;

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//((MainActivity) getActivity()).setActivityTitle(R.string.ostan);

		View view = inflater.inflate(R.layout.fragment_ostan, null);
		util = new Utility(getActivity());
		
		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		ArrayList<Province> mylist = adapter.getAllProvinceName();
		adapter.close();

		ListView lstProvince2 = (ListView) view.findViewById(R.id.listvOstan);
		Province2ListAdapter ListAdapter = new Province2ListAdapter(
				getActivity(), R.layout.row_ostan, mylist);

		lstProvince2.setAdapter(ListAdapter);
		util.ShowFooterAgahi(getActivity() , false , 1);

		return view;
	}
}
