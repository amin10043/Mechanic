package com.project.mechanic.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;

public class show_pay_fragment extends Fragment {

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		((MainActivity) getActivity()).setActivityTitle(R.string.showad);
		// id = Integer.valueOf(getArguments().getString("Id"));

		View view = inflater.inflate(R.layout.fragment_pay, null);

		// img = (ImageView) view.findViewById(R.id.fragment_anad_imgadd);

		// dbAdapter = new DataBaseAdapter(getActivity());

		return view;
	}
}
