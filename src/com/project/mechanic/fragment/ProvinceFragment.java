package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.entity.Province;
import com.project.mechanic.interfaceServer.CountAgencySerViceInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.CountAgencyServiceServer;
import com.project.mechanic.utility.Utility;

public class ProvinceFragment extends Fragment implements CountAgencySerViceInterface {

	DataBaseAdapter adapter;
	Utility util;
	int controller;
	ArrayList<Province> mylist;

	int ostanId;
	int objectId = -1;
	int mainObjectId = -1;
	int AgencyService = -1;
	ProvinceListAdapter ListAdapter;

	public ProvinceFragment(int mainObjectId, int objectId, int AgencyService) {

		this.objectId = objectId;
		this.AgencyService = AgencyService;
		this.mainObjectId = mainObjectId;
	}

	public ProvinceFragment() {
		// TODO Auto-generated constructor stub
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.ostan);
		util = new Utility(getActivity());

		View view = inflater.inflate(R.layout.fragment_ostan, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		mylist = adapter.getAllProvinceName();
		adapter.close();

		ListView lstProvince = (ListView) view.findViewById(R.id.listvOstan);
		ListAdapter = new ProvinceListAdapter(getActivity(), R.layout.row_ostan, mylist, objectId, AgencyService,
				mainObjectId);

		lstProvince.setAdapter(ListAdapter);

		if (objectId != -1)
			getCountOfObjectInProvince();

		return view;
	}

	private void getCountOfObjectInProvince() {

		if (controller < mylist.size()) {

			ostanId = mylist.get(controller).getId();

			CountAgencyServiceServer getCount = new CountAgencyServiceServer(getActivity());
			getCount.delegate = ProvinceFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getSubObjectsInProvince");
			items.put("objectId", String.valueOf(objectId));
			items.put("provinceId", String.valueOf(ostanId));

			if (AgencyService == StaticValues.TypeObjectIsAgency)
				items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsAgency));
			else
				items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsService));

			getCount.execute(items);

		}

	}

	@Override
	public void ResultCountAgency(String output) {

		if (util.checkError(output) == false) {

			adapter.open();

			int count = adapter.getCountOfAgencyInProvince(objectId, ostanId, AgencyService);

			if (count == 0) {

				adapter.insertCountOfAgencyInProvince(objectId, ostanId, AgencyService, Integer.valueOf(output));

			} else {

				adapter.updateCountBrandInProvince(objectId, ostanId, Integer.valueOf(output));

			}
			adapter.close();

			ListAdapter.notifyDataSetChanged();

			controller++;
			getCountOfObjectInProvince();

		}

	}

}
