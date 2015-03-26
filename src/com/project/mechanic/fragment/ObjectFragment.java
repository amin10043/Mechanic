package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;

public class ObjectFragment extends Fragment {

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);
		
		View view = inflater.inflate(R.layout.fragment_object, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();
		ArrayList<RowMain> mylist = adapter.getAllObjectName();
		adapter.close();

		ListView lst = (ListView) view.findViewById(R.id.listvOstan);
		ProvinceListAdapter ListAdapter = new ProvinceListAdapter(
				getActivity(), R.layout.row_ostan, mylist);

		
		lst.setAdapter(ListAdapter);

		lst.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new IntroductionFragment());
				trans.addToBackStack(null);
				trans.commit();
			}
		});

		return view;
	}
}
