package com.project.mechanic.fragment;

import java.util.ArrayList;
import java.util.List;

import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.row_items.FroumtitleItem;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class FroumtitleFragment extends Fragment {
	private ImageButton addtitle;
	@SuppressLint("InflateParams")
	@Override
	
	public View onCreateView(android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
		
		 View view = inflater.inflate(R.layout.fragment_titlefrm, null);
		 addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt);
		 
		 addtitle.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Dialog dialog = new Dialog(getActivity());
				 dialog.setContentView(R.layout.dialog_addtitle);
				 dialog.show();
			}
			 
			 
			 
		 });
		
		 List<FroumtitleItem> titlelist1 = new ArrayList<FroumtitleItem>();

			FroumtitleItem p1 = new FroumtitleItem();
			FroumtitleItem p2 = new FroumtitleItem();
			FroumtitleItem p3 = new FroumtitleItem();
			FroumtitleItem p4 = new FroumtitleItem();
			
			p1.setTitle("ÚäæÇä 1");
			p2.setTitle("ÚäæÇä 1");
			p3.setTitle("ÚäæÇä 1");
			p4.setTitle("ÚäæÇä 1");
			
			titlelist1.add(p1);
            titlelist1.add(p2);
            titlelist1.add(p3);
            titlelist1.add(p4);
			

			ListView lst = (ListView) view.findViewById(R.id.lstTitle);
			FroumtitleListadapter ListAdapter = new FroumtitleListadapter(getActivity(),R.layout.froumtitleitem, titlelist1);

			lst.setAdapter(ListAdapter);
			lst.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
					.getSupportFragmentManager().beginTransaction();
				     trans.replace(R.id.content_frame, new FroumFragment());
				     trans.commit();
			}
		});
			return view;
	}
	
	
	
}