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
import com.project.mechanic.adapter.NewsListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class NewsFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int id = 5;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		if (getArguments() != null && getArguments().getString("Id") != null) {
			id = Integer.valueOf(getArguments().getString("Id"));
		}

		// SharedPreferences getData = getActivity().getSharedPreferences("Id",
		// Context.MODE_PRIVATE);
		// id = getData.getInt("main_Id", 0);

		((MainActivity) getActivity()).setActivityTitle(R.string.News);
		View view = inflater.inflate(R.layout.fragment_news, null);

		dbAdapter = new DataBaseAdapter(getActivity());

		Utility utility = new Utility(getActivity());

		dbAdapter.open();
		List<ListItem> mylist = dbAdapter.getListItemsById(id);
		dbAdapter.close();

		ListView lstNews = (ListView) view.findViewById(R.id.lstVnews);

		NewsListAdapter ListAdapter = new NewsListAdapter(getActivity(),
				R.layout.row_news, mylist, id);
		lstNews.setAdapter(ListAdapter);
		utility.ShowFooterAgahi(getActivity(), false, 0);
		return view;
	}
}
