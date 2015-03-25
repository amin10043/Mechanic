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
import com.project.mechanic.adapter.CityListAdapter;
import com.project.mechanic.row_items.RowMain;

public class CityFragment extends Fragment {

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_city, null);

		List<RowMain> mylist = new ArrayList<RowMain>();

		RowMain p1 = new RowMain();
		RowMain p2 = new RowMain();
		RowMain p3 = new RowMain();
		RowMain p4 = new RowMain();
		RowMain p5 = new RowMain();
		RowMain p6 = new RowMain();
		RowMain p7 = new RowMain();
		RowMain p8 = new RowMain();

		p1.setName("سبزوار");
		p2.setName("بجنورد");
		p3.setName("بابل");
		p4.setName("رشت");
		p5.setName("لاهیجان");
		p6.setName("سنندج");
		p7.setName("خرم آباد");
		p8.setName("مراغه");

	
		mylist.add(p1);
		mylist.add(p2);
		mylist.add(p3);
		mylist.add(p4);
		mylist.add(p5);
		mylist.add(p6);
		mylist.add(p7);
		mylist.add(p8);

		ListView lst1 = (ListView) view.findViewById(R.id.ListvCity);
		CityListAdapter ListAdapter = new CityListAdapter(getActivity(),R.layout.row_city, mylist);

		lst1.setAdapter(ListAdapter);
		lst1.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionFragment());
				trans.commit();
			}
		});

		return view;
	}
}



