package com.project.mechanic.fragment;

import java.io.ObjectOutputStream.PutField;
import java.util.ArrayList;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.NewspaperListAdapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.News;
import com.project.mechanic.model.DataBaseAdapter;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.Adapter;
import android.widget.ListView;
import android.widget.TextView;


public class NewsmoreFragment extends Fragment {

	
	
	int id;
	DataBaseAdapter adapter;

	//@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.news);

		View view = inflater.inflate(R.layout.fragment_newsmore, null);
		TextView titletxt = (TextView) view.findViewById(R.id.titletxt);
		TextView destxt = (TextView) view.findViewById(R.id.describtiontxt);
	adapter = new DataBaseAdapter(getActivity());
	adapter.open();

	// int id = Integer.valueOf(getArguments().getString("Id"));
	
		News x =adapter.getNewsById(2);
		adapter.close();
		
		titletxt.setText(x.getTitle());
	destxt.setText(x.getDescription());

	
		return view;
		
		
	
	}


}

	
	
