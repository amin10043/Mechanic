package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

import com.project.mechanic.R;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.row_items.RowMain;

public class ProvinceFragment extends Fragment {

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_ostan, null);
	

		List<RowMain> mylist = new ArrayList<RowMain>();

		
		
		
		ListView lst = (ListView) view.findViewById(R.id.listvOstan);
		ProvinceListAdapter  ListAdapter = new  ProvinceListAdapter(getActivity(),R.layout.row_ostan, mylist);

		lst.setAdapter(ListAdapter);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new CityFragment());
				trans.commit();
			}
		});

		return view;
	}
}
