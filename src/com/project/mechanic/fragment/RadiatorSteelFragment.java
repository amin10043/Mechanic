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
import com.project.mechanic.adapter.RadiatorSteelListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class RadiatorSteelFragment extends Fragment {


	DataBaseAdapter dbAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.RadiatorSteel);
		View view = inflater.inflate(R.layout.fragment_radiatorsteel, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(78);
		dbAdapter.close();

		ListView lstRadiatorSteel = (ListView) view.findViewById(R.id.lstVradiatorsteel);
		RadiatorSteelListAdapter ListAdapter = new RadiatorSteelListAdapter(getActivity(),
				R.layout.row_radiatorsteel, mylist);

		lstRadiatorSteel.setAdapter(ListAdapter);


		
		return view;
	}
}

