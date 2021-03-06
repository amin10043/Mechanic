package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

public class TypeFroum extends Fragment {
	RelativeLayout professionalChat, publicChat;
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_type_froum, null);
		util = new Utility(getActivity());

		professionalChat = (RelativeLayout) view
				.findViewById(R.id.layout_group_chat_professional);
		publicChat = (RelativeLayout) view.findViewById(R.id.layout_public);
		TextView lab1 = (TextView) view.findViewById(R.id.lab1);
		TextView lab2 = (TextView) view.findViewById(R.id.lab2);
		
		lab1.setTypeface(util.SetFontCasablanca());
		lab2.setTypeface(util.SetFontCasablanca());

		publicChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new FroumtitleFragment());

				trans.addToBackStack(null);
				trans.commit();

			}
		});

		professionalChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new TypeExpertFroum());

				trans.addToBackStack(null);
				trans.commit();
			}
		});

		return view;
	}
}
