package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.utility.Utility;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

@SuppressLint("ValidFragment")
public class TopItems extends Fragment {
	String type;
	Utility util;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_top_items, null);
		TextView headerTile = (TextView) rootView.findViewById(R.id.head);
		
		util = new Utility(getActivity());
		
		if (type.equals("page")) {
			headerTile.setText("20 صفحه که بیشترین بازدید را داشته اند");
		}
		if (type.equals("post")) {
			headerTile.setText("20 مطلب که بیشترین بازدید را داشته اند");
		}
		if (type.equals("paper")) {
			headerTile.setText("20 مقاله که بیشترین بازدید را داشته اند");
		}

		return rootView;
	}

	public TopItems(String type) {
		this.type = type;
	}
}
