package com.project.mechanic.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.MainListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;

public class MainFragment extends Fragment {

	DataBaseAdapter dbAdapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View view = inflater.inflate(R.layout.fragment_main, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(1);
		dbAdapter.close();

		ListView lstMain = (ListView) view.findViewById(R.id.lstMain);
		MainListAdapter ListAdapter = new MainListAdapter(getActivity(),
				R.layout.main_item_list, mylist);

		lstMain.setAdapter(ListAdapter);


		
		return view;
	}
}