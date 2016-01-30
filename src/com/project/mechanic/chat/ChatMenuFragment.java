package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class ChatMenuFragment extends Fragment {

	static final String inviteLable = "دعوت از دوستان";
	static final String newGroupLable = "گروه جدید";
	static final String newChanalLable = "تالار جدید";
	static final String settingsLable = "تنظیمات";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_chat_menu, null);
		ListView lv = (ListView) convertView.findViewById(R.id.lstComment);

		List<String> menuItems = new ArrayList<String>();

		menuItems.add(inviteLable);
		menuItems.add(newGroupLable);
		menuItems.add(newChanalLable);
		menuItems.add(settingsLable);

		ChatMenuAdapter listAdapter = new ChatMenuAdapter(getActivity(), R.layout.row_menu_chat, menuItems);
		lv.setAdapter(listAdapter);

		return convertView;
	}

}
