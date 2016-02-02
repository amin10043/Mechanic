package com.project.mechanic.chat;

import java.util.ArrayList;

import com.project.mechanic.R;
import com.project.mechanic.StaticValues;
import com.project.mechanic.chatAdapter.ChatItemAdapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class FamilyChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_family_chat, null);
		
		ListView itemList = (ListView)convertView.findViewById(R.id.lstComment);
		
		ArrayList<String> childFamily = new ArrayList<String>();
		
		childFamily.add("پدر");
		childFamily.add("مادر");
		childFamily.add("خواهر");
		childFamily.add("برادر");
		childFamily.add("همسر");
		childFamily.add("دایی");
		childFamily.add("خاله");
		childFamily.add("عمو");
		childFamily.add("عمه");

		ChatItemAdapter ListAdapter = new ChatItemAdapter(getActivity(), R.layout.row_child_type_chat, childFamily , StaticValues.TypeFamilyChat);
		itemList.setAdapter(ListAdapter);
				
				
		
		return convertView;
	}

}
