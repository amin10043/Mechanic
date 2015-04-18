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
import com.project.mechanic.adapter.AdvertisementListAdapter;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.TicketType;
import com.project.mechanic.model.DataBaseAdapter;

public class AdvertisementFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	List<Ticket> TicketList = null;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// id = Integer.valueOf(getArguments().getString("Id"));

		((MainActivity) getActivity()).setActivityTitle(R.string.Propaganda);
		View view = inflater.inflate(R.layout.fragment_shop, null);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		TicketList = dbAdapter.getTicketByProvinceId(1);
		List<TicketType> mylist = dbAdapter.getAllTicketType();

		dbAdapter.close();

		ListView lstAdvertisement = (ListView) view
				.findViewById(R.id.listVshop);
		AdvertisementListAdapter ListAdapter = new AdvertisementListAdapter(
				getActivity(), R.layout.row_shop, mylist);

		lstAdvertisement.setAdapter(ListAdapter);

		return view;
	}
}
