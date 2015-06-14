package com.project.mechanic.fragment;



import java.util.List;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.ShopListAdapter;
import com.project.mechanic.adapter.NewsListAdapter;
import com.project.mechanic.entity.ListItem;
import com.project.mechanic.entity.NewsPaper;
import com.project.mechanic.model.DataBaseAdapter;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.ListView;


public class UrlNewsPaperFragment extends Fragment {

	DataBaseAdapter dbAdapter;
	int id;
	String a;
	    
	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		id = Integer.valueOf(getArguments().getString("Id"));

		((MainActivity) getActivity()).setActivityTitle(R.string.News);
		View view = inflater.inflate(R.layout.fragment_urlnewspaper, null);
		

		dbAdapter = new DataBaseAdapter(getActivity());

		dbAdapter.open();

		NewsPaper newsPaper=dbAdapter.getNewsPaperId(id);

		dbAdapter.close();

		if(newsPaper != null){
			WebView webview = (WebView) view.findViewById(R.id.webView1);
			
			webview.loadUrl(newsPaper.getUrl());
		}

		
		
		return view;
	}
	
}








	
	

	
	

