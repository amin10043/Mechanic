package com.project.mechanic.fragment;

import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.entity.News;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class NewsmoreFragment extends Fragment {

	int id;
	DataBaseAdapter adapter;
	Utility util;

	// @SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//((MainActivity) getActivity()).setActivityTitle(R.string.news);

		View view = inflater.inflate(R.layout.fragment_newsmore, null);
		TextView titletxt = (TextView) view.findViewById(R.id.titletxt);
		TextView destxt = (TextView) view.findViewById(R.id.describtiontxt);
		adapter = new DataBaseAdapter(getActivity());
		adapter.open();

		int id = Integer.valueOf(getArguments().getString("Id"));

		News x = adapter.getNewsById(id);
		adapter.close();

		titletxt.setText(x.getTitle());
		destxt.setText(x.getDescription());
		util = new Utility(getActivity());

		
		titletxt.setTypeface(util.SetFontCasablanca());
		destxt.setTypeface(util.SetFontCasablanca());

		return view;

	}

}
