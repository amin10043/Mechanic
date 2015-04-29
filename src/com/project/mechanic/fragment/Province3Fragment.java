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
import com.project.mechanic.adapter.Province3ListAdapter;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;

public class Province3Fragment extends Fragment {

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.ostan);

		View view = inflater.inflate(R.layout.fragment_ostan, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		ArrayList<Province> mylist = adapter.getAllProvinceName();
		adapter.close();

		ListView lstProvince3 = (ListView) view.findViewById(R.id.listvOstan);
		Province3ListAdapter ListAdapter = new Province3ListAdapter(
				getActivity(), R.layout.row_ostan, mylist);

		lstProvince3.setAdapter(ListAdapter);

		return view;
	}
}
