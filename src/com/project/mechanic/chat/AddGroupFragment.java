package com.project.mechanic.chat;

import java.util.ArrayList;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class AddGroupFragment extends Fragment {

	ListView contactList;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_create_group_chat, null);
		View header = getActivity().getLayoutInflater().inflate(R.layout.header_add_group, null);

		contactList = (ListView) convertView.findViewById(R.id.lstComment);

		contactList.addHeaderView(header);

		ArrayList<String> childFavorite = new ArrayList<String>();

		childFavorite.add("محمد ارزمان زاده");
		childFavorite.add("مهندس حسینی");
		childFavorite.add("مهندس هامونی");
		childFavorite.add("مهدی ذبیحی");
		childFavorite.add("مسعود محمودزاده");
		childFavorite.add("محمد اسماعیلی");
		childFavorite.add("اسماعیل شعبانی");
		childFavorite.add("آیدین غیبی");
		childFavorite.add("داوود امینی");

		SelectContactAdapter ListAdapter = new SelectContactAdapter(getActivity(), R.layout.row_select_contact,
				childFavorite);
		contactList.setAdapter(ListAdapter);

		return convertView;
	}

}
