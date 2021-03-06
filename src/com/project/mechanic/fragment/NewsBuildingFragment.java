package com.project.mechanic.fragment;



import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.NewspaperAdapter;
import com.project.mechanic.adapter.ShopListAdapter;
import com.project.mechanic.adapter.NewsListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.NewsPaper;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;


public class NewsBuildingFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int id;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		id = Integer.valueOf(getArguments().getString("Id"));

		//((MainActivity) getActivity()).setActivityTitle(R.string.News);
		View view = inflater.inflate(R.layout.fragment_news, null);
		

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();
		List<NewsPaper> mylist = dbAdapter.getNewsPaperTypeId(id);
		dbAdapter.close();
		
		Utility util = new Utility(getActivity());
		
		ListView lstNewsPaper = (ListView) view.findViewById(R.id.lstVnews);
		
		NewspaperAdapter ListAdapter = new NewspaperAdapter(getActivity(),
				R.layout.row_news, mylist,id);
		lstNewsPaper.setAdapter(ListAdapter);
		return view;
	}
}

	

    	







	
	

	
	

