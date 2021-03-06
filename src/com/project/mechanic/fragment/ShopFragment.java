package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.adapter.ShopListAdapter;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ShopFragment extends Fragment {

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// ((MainActivity) getActivity()).setActivityTitle(R.string.ostan);

		View view = inflater.inflate(R.layout.fragment_shop, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		ArrayList<Province> mylist = adapter.getAllProvinceName();
		adapter.close();

		Utility util = new Utility(getActivity());
		ListView lstProvince = (ListView) view.findViewById(R.id.listVshop);
		ShopListAdapter ListAdapter = new ShopListAdapter(getActivity(),
				R.layout.row_ostan, mylist);

		lstProvince.setAdapter(ListAdapter);


		return view;
	}
}
