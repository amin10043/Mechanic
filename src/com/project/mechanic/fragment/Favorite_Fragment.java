package com.project.mechanic.fragment;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FavoriteListAdapter;
import com.project.mechanic.entity.Ticket;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.ServiceComm;
import com.project.mechanic.utility.Utility;

public class Favorite_Fragment extends Fragment {

	ServiceComm service;
	Utility util;
	Dialogeml dialog;
	ListView listFavorite;
	DataBaseAdapter dbAdapter;
	List<Ticket> mylist;
	int id;
	int user;
	int ticket;
	Users u;
	View view;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		service = new ServiceComm(getActivity());
		util = new Utility(getActivity());

		((MainActivity) getActivity()).setActivityTitle(R.string.favorite);
		view = inflater.inflate(R.layout.favorite_fragment, null);

		listFavorite = (ListView) view.findViewById(R.id.listView_favorite);

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		u = util.getCurrentUser();
		if (u == null) {
			Toast.makeText(getActivity(),
					"برای مشاهده این صفحه شما باید وارد شوید.",
					Toast.LENGTH_SHORT).show();

		} else {

			mylist = dbAdapter.getTicketByusetId(u.getId());

			FavoriteListAdapter ListAdapter = new FavoriteListAdapter(
					getActivity(), R.layout.row_favorite, mylist, this);

			listFavorite.setAdapter(ListAdapter);
		}
		return view;

	}

	public void updateView() {
		dbAdapter.open();
		mylist = dbAdapter.getTicketByusetId(u.getId());
		dbAdapter.close();

		listFavorite = (ListView) view.findViewById(R.id.listView_favorite);
		FavoriteListAdapter ListAdapter = new FavoriteListAdapter(
				getActivity(), R.layout.row_favorite, mylist, this);

		listFavorite.setAdapter(ListAdapter);

	}

}
