package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.adapter.CityListAdapter;
import com.project.mechanic.entity.City;
import com.project.mechanic.entity.Province;
import com.project.mechanic.interfaceServer.CountAgencySerViceInterface;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.CountAgencyServiceServer;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

@SuppressLint("ValidFragment")
public class CityFragment extends Fragment implements CountAgencySerViceInterface {

	DataBaseAdapter adapter;
	List<City> cityList = null;
	Utility util;

	int objectId = -1;;
	int AgencyService;
	int controller = 0;
	int cityId;
	int mainObjectId;
	CityListAdapter ListAdapter;

	public CityFragment(List<City> allCity) {
		super();
		cityList = allCity;
	}

	public CityFragment(List<City> allCity, int objectId, int AgencyService, int mainObjectId) {
		this.AgencyService = AgencyService;
		this.objectId = objectId;
		cityList = allCity;
		this.mainObjectId = mainObjectId;

	}

	public CityFragment() {
		super();
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.city);
		View view = inflater.inflate(R.layout.fragment_city, null);
		util = new Utility(getActivity());
		adapter = new DataBaseAdapter(getActivity());

		if (cityList == null) {
			adapter.open();
			cityList = adapter.getCitysByProvinceId(1);
			// ArrayList<RowMain> mylist = adapter.getAllCityName();
			adapter.close();
		}
		ListView lstCity = (ListView) view.findViewById(R.id.lstCity);
		ListAdapter = new CityListAdapter(getActivity(), R.layout.row_city, cityList, objectId, AgencyService,
				mainObjectId);

		lstCity.setAdapter(ListAdapter);

		// SharedPreferences sendData = getActivity().getSharedPreferences(
		// "Id", 0);
		// int id = sendData.getInt("main_Id", -1);

		// if (objectId != -1) {
		getCountObjectInCity();
		// }

		return view;
	}

	private void getCountObjectInCity() {

		if (controller < cityList.size()) {

			cityId = cityList.get(controller).getId();

			CountAgencyServiceServer getCount = new CountAgencyServiceServer(getActivity());
			getCount.delegate = CityFragment.this;
			Map<String, String> items = new LinkedHashMap<String, String>();

			items.put("tableName", "getSubObjectsInCity");
			items.put("objectId", String.valueOf(objectId));
			items.put("cityId", String.valueOf(cityId));

			if (AgencyService == StaticValues.TypeObjectIsAgency)
				items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsAgency));
			else
				items.put("agencyService", String.valueOf(StaticValues.TypeObjectIsService));

			getCount.execute(items);

		}

	}

	public void onBackPressed() {

		FragmentTransaction trans = getActivity().getSupportFragmentManager().beginTransaction();
		trans.replace(R.id.content_frame, new ProvinceFragment());
		trans.commit();

	}

	@Override
	public void ResultCountAgency(String output) {

		if (util.checkError(output) == false) {

			if (util.checkError(output) == false) {

				adapter.open();

				int count = adapter.getCountOfAgencyInCity(objectId, cityId, AgencyService);

				if (count == 0) {

					adapter.insertCountOfAgencyInCity(objectId, cityId, AgencyService, Integer.valueOf(output));

				} else {

					adapter.updateCountBrandInCity(objectId, cityId, Integer.valueOf(output));

				}
				adapter.close();

				ListAdapter.notifyDataSetChanged();

				controller++;
				getCountObjectInCity();

			}
		}
	}
}