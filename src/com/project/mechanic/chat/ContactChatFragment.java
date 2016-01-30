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

public class ContactChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_contact_chat, null);

		ListView itemList = (ListView) convertView.findViewById(R.id.lstComment);

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

		ChatItemAdapter ListAdapter = new ChatItemAdapter(getActivity(), R.layout.row_contact_chat, childFavorite,
				StaticValues.TypeContactChat);
		itemList.setAdapter(ListAdapter);

		return convertView;
	}

}
