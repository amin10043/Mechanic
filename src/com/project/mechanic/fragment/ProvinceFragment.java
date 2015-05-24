package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.ListView.PullAndLoadListView;
import com.project.mechanic.ListView.PullAndLoadListView.OnLoadMoreListener;
import com.project.mechanic.ListView.PullToRefreshListView.OnRefreshListener;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class ProvinceFragment extends Fragment {

	DataBaseAdapter adapter;
	Utility util;
	private ImageView search;
	ListView lstProvince;
	ProvinceListAdapter ListAdapter;
	ArrayList<Province> mylist;
	List<Province> subList;
	List<Province> tempList;
	View view;


	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//((MainActivity) getActivity()).setActivityTitle(R.string.ostan);
		util = new Utility(getActivity());

		View view = inflater.inflate(R.layout.fragment_ostan, null);
		search = (ImageView) view.findViewById(R.id.sedarch_v);
		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		mylist = adapter.getAllProvinceName();
		adapter.close();
		

		lstProvince = (ListView) view.findViewById(R.id.listvOstan);
		ListAdapter = new ProvinceListAdapter(getActivity(),
				R.layout.row_ostan, mylist);

		lstProvince.setAdapter(ListAdapter);



		return view;
	}

	



}
