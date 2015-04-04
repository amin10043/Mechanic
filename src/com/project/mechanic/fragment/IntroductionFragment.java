package com.project.mechanic.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class IntroductionFragment extends Fragment {

	Context context;
	private ImageView peykan6;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);

	 peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		
		
		peykan6.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				
		
				
				FragmentTransaction trans = ((MainActivity) context)
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.addToBackStack(null);
				trans.commit();
				
				
				
			}
		});
		
		
		
		
		
		return view;

	}
}
