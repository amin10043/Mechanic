package com.project.mechanic.fragment;


import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FloorheatingListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class FloorheatingFragment extends Fragment {


	DataBaseAdapter dbAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_floorheating, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(10);
		dbAdapter.close();

		ListView lstFloorheating = (ListView) view.findViewById(R.id.lstVfloorheating);
		FloorheatingListAdapter ListAdapter = new FloorheatingListAdapter(getActivity(),
				R.layout.row_floorheating, mylist);

		lstFloorheating.setAdapter(ListAdapter);


		
		return view;
	}
}

