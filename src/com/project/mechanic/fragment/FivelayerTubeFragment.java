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
import com.project.mechanic.adapter.FivelayerTubeListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class FivelayerTubeFragment extends Fragment {


	DataBaseAdapter dbAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_fivelayertube, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(16);
		dbAdapter.close();

		ListView lstFivelayerTube = (ListView) view.findViewById(R.id.lstVfivelayertube);
		FivelayerTubeListAdapter ListAdapter = new FivelayerTubeListAdapter(getActivity(),
				R.layout.row_fivelayertube, mylist);

		lstFivelayerTube.setAdapter(ListAdapter);


		
		return view;
	}
}

