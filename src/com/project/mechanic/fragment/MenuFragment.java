package com.project.mechanic.fragment;

import com.project.mechanic.R;
import com.project.mechanic.R.id;
import com.project.mechanic.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MenuFragment extends Fragment {
	public static final String ARG_OS = "OS";
	private int value;
	private TextView textView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_layout, null);
		textView = (TextView) view.findViewById(R.id.RowOstantxt);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		textView.setText(String.valueOf(value));

	}

	@Override
	public void setArguments(Bundle args) {
		value = args.getInt(ARG_OS);
	}
}