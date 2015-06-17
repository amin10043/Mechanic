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
import com.project.mechanic.adapter.AdvisorTypeListAdapter;
import com.project.mechanic.entity.AdvisorType;
import com.project.mechanic.model.DataBaseAdapter;

public class AdvisorTypeFragment extends Fragment {

	DataBaseAdapter adapter;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.advisor);

		View view = inflater.inflate(R.layout.fragment_advisortype, null);

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();

		ArrayList<AdvisorType> mylist = adapter.getAllAdvisorTypeName();
		adapter.close();

		ListView lstadvisortype = (ListView) view
				.findViewById(R.id.listVadvisortype);
		AdvisorTypeListAdapter ListAdapter = new AdvisorTypeListAdapter(
				getActivity(), R.layout.row_advisortype, mylist);

		lstadvisortype.setAdapter(ListAdapter);

		return view;
	}
}
