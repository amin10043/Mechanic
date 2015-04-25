package com.project.mechanic.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.project.mechanic.R;
import com.project.mechanic.adapter.MainListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class MainFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	MainListAdapter ListAdapter;
	LinearLayout footer_layLayout;
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		util = new Utility(getActivity());
		View view = inflater.inflate(R.layout.fragment_main, null);
		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(0);
		dbAdapter.close();

		ListView lstMain = (ListView) view.findViewById(R.id.lstMain);
		ListAdapter = new MainListAdapter(getActivity(),
				R.layout.main_item_list, mylist);

		lstMain.setAdapter(ListAdapter);
		ImageView v = (ImageView) view.findViewById(R.id.imgAdverst);
		v.getLayoutParams().height = util.getScreenHeightWithPadding() / 9;
		v.requestLayout();

		return view;
	}

}
