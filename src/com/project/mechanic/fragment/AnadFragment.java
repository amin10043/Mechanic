package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class AnadFragment extends Fragment {

	
	

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_anad, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.anad);

	
		
		
		
		return view;

	}
}
