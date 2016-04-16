package com.project.mechanic.fragment;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.BerandListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.Settings;
import com.project.mechanic.interfaceServer.InterfaceGetListItem;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.server.GetListItem;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class BerandFragment extends Fragment implements InterfaceGetListItem {

	DataBaseAdapter dbAdapter;
	// int id = 1;
	int mainObjectId;;
	Utility util;
	BerandListAdapter ListAdapter;

	public BerandFragment(int mainObjectId) {
		this.mainObjectId = mainObjectId;
	}

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		// if (getArguments() != null && getArguments().getString("Id") != null)
		// {
		// id = Integer.valueOf(getArguments().getString("Id"));
		// }

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		View view = inflater.inflate(R.layout.fragment_berand, null);
		util = new Utility(getActivity());

		dbAdapter = new DataBaseAdapter(getActivity());
		getNewItems();

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(mainObjectId);
		dbAdapter.close();

		ListView lstBerand = (ListView) view.findViewById(R.id.lstVberand);

		ListAdapter = new BerandListAdapter(getActivity(), R.layout.row_berand, mylist);

		lstBerand.setAdapter(ListAdapter);

		return view;
	}

	public int getCurrentId() {
		return mainObjectId;
	}

	private void getNewItems() {

		// final SharedPreferences currentTime =
		// getActivity().getSharedPreferences("time", 0);
		//
		// String time = currentTime.getString("time", "-1");
		// dbAdapter.open();
		// Settings settings = dbAdapter.getSettings();
		// dbAdapter.close();
		//
		// GetListItem getDateService = new GetListItem(getActivity());
		// getDateService.delegate = BerandFragment.this;
		// Map<String, String> items = new LinkedHashMap<String, String>();
		//
		// String fromDate = settings.getServerDate_Start_ListItem();
		// String endDate = settings.getServerDate_End_ListItem();
		//
		//// if ()
		//
		// items.put("tableName", "getListItemByListId");
		// items.put("listId", String.valueOf(mainObjectId));
		// items.put("fromDate", settings.getServerDate_Start_ListItem());
		// items.put("endDate", settings.getServerDate_End_ListItem());
		// items.put("isRefresh", String.valueOf(0));
		//
		// getDateService.execute(items);

	}

	@Override
	public void ResultLisItem(String output) {

		// if (util.checkError(output) == false) {
		//
		// util.parseQuery(output);
		// ListAdapter.notifyDataSetChanged();
		//
		// }

	}

}