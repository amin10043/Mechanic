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
import com.project.mechanic.adapter.BerandListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class BerandFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int id = 1;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getArguments() != null && getArguments().getString("Id") != null) {
			id = Integer.valueOf(getArguments().getString("Id"));
		}

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		View view = inflater.inflate(R.layout.fragment_berand, null);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(id);
		dbAdapter.close();

		ListView lstBerand = (ListView) view.findViewById(R.id.lstVberand);

		BerandListAdapter ListAdapter = new BerandListAdapter(getActivity(),
				R.layout.row_berand, mylist, id);

		lstBerand.setAdapter(ListAdapter);
		
		Utility utility = new Utility(getActivity());
		
		
		return view;
	}

	public int getCurrentId() {
		return id;
	}

}