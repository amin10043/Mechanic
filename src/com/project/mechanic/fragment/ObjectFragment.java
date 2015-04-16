package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ObjectListAdapter;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.row_items.RowMain;
import com.project.mechanic.entity.*;
import com.project.mechanic.entity.Object;

public class ObjectFragment extends Fragment {

	DataBaseAdapter adapter;
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setTitle(R.string.object);

		View view = inflater.inflate(R.layout.fragment_object, null);
		
		SharedPreferences sendData = getActivity().getSharedPreferences(
				"Id", 0);
		int id = sendData.getInt("main_Id", -1);
		int city_id = Integer.valueOf(getArguments().getString("cityId"));


		

		adapter = new DataBaseAdapter(getActivity());

		adapter.open();
		ArrayList<Object> mylist = adapter.getObjectBy_BTId_CityId(id, city_id);
		adapter.close();

		ListView lstObject = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		ObjectListAdapter ListAdapter = new ObjectListAdapter(getActivity(),
				R.layout.row_object, mylist);

		lstObject.setAdapter(ListAdapter);


		return view;
	}
}
