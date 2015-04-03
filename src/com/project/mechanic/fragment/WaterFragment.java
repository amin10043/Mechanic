package com.project.mechanic.fragment;


import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.WaterListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class WaterFragment extends Fragment {


	DataBaseAdapter dbAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.Water);
		View view = inflater.inflate(R.layout.fragment_water, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(61);
		dbAdapter.close();

		ListView lstWater = (ListView) view.findViewById(R.id.lstVwater);
		WaterListAdapter ListAdapter = new WaterListAdapter(getActivity(),
				R.layout.row_water, mylist);

		lstWater.setAdapter(ListAdapter);


		
		return view;
	}
}

