package com.project.mechanic.fragment;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mechanic.R;
import com.project.mechanic.model.DataBaseAdapter;

public class IntroductionEditFragment extends Fragment {
	Context context;
	DataBaseAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction_edit, null);
		adapter = new DataBaseAdapter(getActivity());

		return view;
	}

}
