package com.project.mechanic.fragment;

import java.util.List;

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
import com.project.mechanic.adapter.CountryListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class CountryFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int id;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// id = Integer.valueOf(getArguments().getString("Id"));

		//((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		View view = inflater.inflate(R.layout.fragment_city, null);

		dbAdapter = new DataBaseAdapter(getActivity());
		
		Utility utility = new Utility(getActivity());
		RelativeLayout timeLine = utility.timeLineDrawing(getActivity());
		timeLine.setVisibility(View.GONE);

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(2);
		dbAdapter.close();

		ListView lstCountry = (ListView) view.findViewById(R.id.lstCity);
		CountryListAdapter ListAdapter = new CountryListAdapter(getActivity(),
				R.layout.row_city, mylist, id);

		lstCountry.setAdapter(ListAdapter);

		return view;
	}
}
