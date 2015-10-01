package com.project.mechanic.fragment;

import java.util.List;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.AdvertisementListAdapter;
import com.project.mechanic.entity.TicketType;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class AdvertisementFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int provinceId = 0;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		dbAdapter = new DataBaseAdapter(getActivity());
		if (getArguments() != null
				&& getArguments().getString("provinceId") != null) {
			provinceId = Integer
					.valueOf(getArguments().getString("provinceId"));

		}
		SharedPreferences sendIDpro = getActivity().getSharedPreferences("Id",
				0);
		sendIDpro.edit().putInt("main_Id", provinceId).commit();
		Toast.makeText(getActivity(), "advers fragment " + provinceId,
				Toast.LENGTH_SHORT).show();

		dbAdapter.open();
		List<TicketType> mylist = dbAdapter.getAllTicketType();

		dbAdapter.close();

		//((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		View view = inflater.inflate(R.layout.fragment_shop, null);
		
		Utility util = new Utility(getActivity());
			

		dbAdapter = new DataBaseAdapter(getActivity());

		ListView lstAdvertisement = (ListView) view
				.findViewById(R.id.listVshop);
		AdvertisementListAdapter ListAdapter = new AdvertisementListAdapter(
				getActivity(), R.layout.row_shop, mylist, provinceId);

		lstAdvertisement.setAdapter(ListAdapter);

		return view;
	}
}
