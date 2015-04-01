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
import com.project.mechanic.adapter.PackagedWaterListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class PackagedWaterFragment extends Fragment {


	DataBaseAdapter dbAdapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_packagedwater, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(13);
		dbAdapter.close();

		ListView lstPackagedWater = (ListView) view.findViewById(R.id.lstVpackagedwater);
		PackagedWaterListAdapter ListAdapter = new PackagedWaterListAdapter(getActivity(),
				R.layout.row_packagedwater, mylist);

		lstPackagedWater.setAdapter(ListAdapter);


		
		return view;
	}
}

