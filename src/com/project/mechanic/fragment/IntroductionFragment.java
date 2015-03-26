package com.project.mechanic.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class IntroductionFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);

		return view;

	}
}
