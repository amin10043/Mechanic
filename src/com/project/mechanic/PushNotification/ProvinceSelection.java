package com.project.mechanic.PushNotification;

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
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

@SuppressLint("ValidFragment") public class ProvinceSelection extends Fragment {

	DataBaseAdapter adapter;
	Utility util;
	String type;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.ostan);
		util = new Utility(getActivity());

		View view = inflater.inflate(R.layout.fragment_ostan, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		ArrayList<Province> mylist = adapter.getAllProvinceName();
		adapter.close();

		ListView lstProvince = (ListView) view.findViewById(R.id.listvOstan);
		ProVinceSelectionAdapter ListAdapter = new ProVinceSelectionAdapter(
				getActivity(), R.layout.row_ostan, mylist , type);

		lstProvince.setAdapter(ListAdapter);

		return view;
	}

	public ProvinceSelection(String type) {
		this.type = type;
	}

}
