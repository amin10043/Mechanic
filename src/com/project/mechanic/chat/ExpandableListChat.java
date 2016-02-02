package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.Action.FloatingActionButton;
import com.project.mechanic.chatAdapter.ExpandTypeChatAdapter;
import com.project.mechanic.utility.Utility;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

public class ExpandableListChat extends Fragment {

	ExpandableListView typeChatList;
	FloatingActionButton createChat;
	static final String FavoriteLable = "علاقه مندی ها";
	static final String FriendLable = "دوستان";
	static final String GroupLable = "گروه ها";
	static final String ChanalLable = "تالارها";
	static final String ContactLable = "مخاطبین";
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View convertView = inflater.inflate(R.layout.fragment_expand_chat, null);
		util = new Utility(getActivity());

		typeChatList = (ExpandableListView) convertView.findViewById(R.id.lstComment);
		typeChatList.setGroupIndicator(null);
		typeChatList.setClickable(true);

		FloatingActionButton add = (FloatingActionButton) convertView.findViewById(R.id.fab);

		add.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
//				Intent firstpage= new Intent(getActivity(),TabHostChatType.class);
//				getActivity().startActivity(firstpage);			
			}
		});
		
		FillExpandListView();

		return convertView;
	}

	private void FillExpandListView() {

		if (util.getCurrentUser() != null) {

			ArrayList<String> parentItems = new ArrayList<String>();

			parentItems.add(FavoriteLable);
			parentItems.add(FriendLable);
			parentItems.add(GroupLable);
			parentItems.add(ChanalLable);
			parentItems.add(ContactLable);

			ArrayList<String> childFavorite = new ArrayList<String>();
			ArrayList<String> childFriend = new ArrayList<String>();
			ArrayList<String> childGroup = new ArrayList<String>();
			ArrayList<String> childChanal = new ArrayList<String>();
			ArrayList<String> childContact = new ArrayList<String>();

			childFavorite.add("محمد ارزمان زاده");
			childFavorite.add("مهندس حسینی");
			childFavorite.add("مهندس هامونی");
			childFavorite.add("مهدی ذبیحی");

			childFriend.add("محمد ارزمان زاده");
			childFriend.add("مهندس حسینی");
			childFriend.add("مهندس هامونی");
			childFriend.add("مهدی ذبیحی");
			childFriend.add("محمد عربیان");
			childFriend.add("روح الله پور آرین");

			childGroup.add("رئال مادرید");
			childGroup.add("شعر و ادب پارسی");
			childGroup.add("اندروید نویسان");
			childGroup.add("روانشناسی موفقیت");

			childChanal.add("کرمانج خراسان");
			childChanal.add("آخرین خبر");
			childChanal.add("طرفداری");
			childChanal.add("هنر هفتم ");

			childContact.add("محمد ارزمان زاده");
			childContact.add("مهندس حسینی");
			childContact.add("مهندس هامونی");
			childContact.add("مهدی ذبیحی");
			childContact.add("مسعود محمودزاده");
			childContact.add("محمد اسماعیلی");
			childContact.add("اسماعیل شعبانی");
			childContact.add("آیدین غیبی");
			childContact.add("داوود امینی");

			LinkedHashMap<String, List<String>> expandItems = new LinkedHashMap<String, List<String>>();

			expandItems.put(parentItems.get(0), childFavorite);
			expandItems.put(parentItems.get(1), childFriend);
			expandItems.put(parentItems.get(2), childGroup);
			expandItems.put(parentItems.get(3), childChanal);
			expandItems.put(parentItems.get(4), childContact);

			ExpandTypeChatAdapter expandAdapter = new ExpandTypeChatAdapter(getActivity(), parentItems, expandItems);

			typeChatList.setAdapter(expandAdapter);

		}

	}

}
