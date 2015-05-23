package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ExecutertypeListAdapter;
import com.project.mechanic.adapter.ProvinceListAdapter;
import com.project.mechanic.entity.Executertype;
import com.project.mechanic.entity.Province;
import com.project.mechanic.model.DataBaseAdapter;

public class ExecutertypeFragment extends Fragment {

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//((MainActivity) getActivity()).setActivityTitle(R.string.Executertyper);

		View view = inflater.inflate(R.layout.fragment_executertype, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		ArrayList<Executertype> mylist = adapter.getAllExecutertypeName();
		adapter.close();

		ListView lstExecutertype = (ListView) view.findViewById(R.id.listVexecutertype);
		ExecutertypeListAdapter ListAdapter = new ExecutertypeListAdapter(
				getActivity(), R.layout.row_executertype, mylist);

		lstExecutertype.setAdapter(ListAdapter);

		return view;
	}
}
