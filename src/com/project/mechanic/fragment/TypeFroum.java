package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class TypeFroum extends Fragment {
	RelativeLayout professionalChat , publicChat , tabridLayout , heatingLayout , floorLayout , BMSlayout;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_type_froum, null);
		
		 professionalChat = (RelativeLayout)view.findViewById(R.id.layout_group_chat_professional);
		 publicChat = (RelativeLayout)view.findViewById(R.id.layout_public);
		 tabridLayout = (RelativeLayout)view.findViewById(R.id.tabrid_layout);
		 heatingLayout = (RelativeLayout)view.findViewById(R.id.healing_cooling_layout);
		 floorLayout = (RelativeLayout)view.findViewById(R.id.floor_layout);
		 BMSlayout = (RelativeLayout)view.findViewById(R.id.bms_layout);



		
		publicChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
				FragmentTransaction trans = ((MainActivity) getActivity())
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new FroumtitleFragment());


				//trans.addToBackStack(null);
				trans.commit();
				
			}
		});
		
		
		professionalChat.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				publicChat.setVisibility(View.GONE);
				professionalChat.setVisibility(View.GONE);

				
				tabridLayout.setVisibility(View.VISIBLE);
				heatingLayout.setVisibility(View.VISIBLE);
				floorLayout.setVisibility(View.VISIBLE);
				BMSlayout.setVisibility(View.VISIBLE);
				
			}
		});
		
		
		
		return view;
	}

}
