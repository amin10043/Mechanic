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

public class RequestionChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_requestion_chat, null);

		ListView itemList = (ListView) convertView.findViewById(R.id.lstComment);

		ArrayList<String> childsItem = new ArrayList<String>();

		childsItem.add("˜ÇÑÈÑ 1");
		childsItem.add("˜ÇÑÈÑ 2");
		childsItem.add("˜ÇÑÈÑ 3");
		childsItem.add("˜ÇÑÈÑ 4");
		childsItem.add("˜ÇÑÈÑ 5");

		ChatItemAdapter ListAdapter = new ChatItemAdapter(getActivity(), R.layout.row_requestion_chat, childsItem,
				StaticValues.TypeRequestionChat);
		itemList.setAdapter(ListAdapter);

		return convertView;
	}

}
