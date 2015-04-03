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
import com.project.mechanic.adapter.TubeListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class TubeFragment extends Fragment {


	DataBaseAdapter dbAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.Tube);
		View view = inflater.inflate(R.layout.fragment_tube, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(9);
		dbAdapter.close();

		ListView lstTube = (ListView) view.findViewById(R.id.lstVtube);
		TubeListAdapter ListAdapter = new TubeListAdapter(getActivity(),
				R.layout.row_tube, mylist);

		lstTube.setAdapter(ListAdapter);


		
		return view;
	}
}

