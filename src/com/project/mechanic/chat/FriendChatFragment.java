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

public class FriendChatFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View convertView = inflater.inflate(R.layout.fragment_freind_chat, null);
		
		ListView itemList = (ListView)convertView.findViewById(R.id.lstComment);
		
		ArrayList<String> childFavorite = new ArrayList<String>();
		
		childFavorite.add("محمد ارزمان زاده");
		childFavorite.add("مهندس حسینی");
		childFavorite.add("مهندس هامونی");
		childFavorite.add("مهدی ذبیحی");
		childFavorite.add("محمد عربیان");
		childFavorite.add("روح الله پور آرین");
		
		ChatItemAdapter ListAdapter = new ChatItemAdapter(getActivity(), R.layout.row_child_type_chat, childFavorite , StaticValues.TypeFriendChat);
		itemList.setAdapter(ListAdapter);
				
				
		
		return convertView;
	}

}
