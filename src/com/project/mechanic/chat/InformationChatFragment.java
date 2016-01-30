package com.project.mechanic.chat;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;

public class InformationChatFragment extends Fragment {

	ListView memberListView;
	View header;
	List<String> menuItems;
	Utility util;
	
	static final String copyLable = "کپی لینک";
	static final String EditLable = "تغیر لینک";
	static final String shareLable="اشتراک گذاری لینک توسط کاربران";

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		util = new Utility(getActivity());
		View convertView = inflater.inflate(R.layout.fragment_information_chat, null);
		header = getActivity().getLayoutInflater().inflate(R.layout.header_information_chat, null);

		memberListView = (ListView) convertView.findViewById(R.id.lstComment);
		memberListView.addHeaderView(header);
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

		MemberChatAdapter listAdapter = new MemberChatAdapter(getActivity(), R.layout.row_member, childFavorite);

		memberListView.setAdapter(listAdapter);

		setonCliclItems();

		return convertView;
	}

	private void setonCliclItems() {

		RelativeLayout linkGroup = (RelativeLayout) header.findViewById(R.id.linkGroup);
		linkGroup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				menuItems = new ArrayList<String>();
				menuItems.clear();

				menuItems.add(copyLable);
				menuItems.add(EditLable);
				menuItems.add(shareLable);

				final PopupMenu popupMenu = util.ShowPopupMenu(menuItems, v);
				popupMenu.show();
			}
		});
	}

}
