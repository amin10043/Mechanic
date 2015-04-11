package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class IntroductionFragment extends Fragment {

	Context context;
	private ImageView peykan6,peykan5;
	public RelativeLayout link1,link2;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);

	 peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
	 peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
	 link1=(RelativeLayout) view.findViewById(R.id.Layoutlink1);

	 link2=(RelativeLayout) view.findViewById(R.id.Layoutlink2);

	 
	 
	 link2=(RelativeLayout) view.findViewById(R.id.Layoutlink2);
	 

	 link1.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
				
				
				
				
			}
		});
		
	 link2.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
				
				
				
				
			}
		});
		
	 peykan6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
				
				
				
				
			}
		});
	 
	 peykan5.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();
				
				
				
				
			}
		});
		
		return view;

	}
}
