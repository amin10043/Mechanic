package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.IntroductionListAdapter;
import com.project.mechanic.entity.CommentInObject;
import com.project.mechanic.model.DataBaseAdapter;

public class IntroductionFragment extends Fragment {

	Context context;
	private ImageView peykan6, peykan5;
	public RelativeLayout link1, link2;
	public ImageButton btnCmt;
	public DialogcmtInfroum dialog;
	public int id = 0;
	ArrayList<CommentInObject> mylist;
	DataBaseAdapter adapter;
	ListView lst;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_introduction, null);

		((MainActivity) getActivity()).setActivityTitle(R.string.brand);
		adapter = new DataBaseAdapter(getActivity());
		peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
		btnCmt = (ImageButton) view.findViewById(R.id.imgbtnCmt_introduction);
		link1 = (RelativeLayout) view.findViewById(R.id.Layoutlink1);
		lst = (ListView) view.findViewById(R.id.listvCmt_Introduction);
		// id = Integer.valueOf(getArguments().getString("Id"));
		adapter.open();
		mylist = adapter.getAllCommentInObjectById(id);
		adapter.close();
		IntroductionListAdapter listAdapter = new IntroductionListAdapter(
				getActivity(), R.layout.raw_froumcmt, mylist);
		lst.setAdapter(listAdapter);

		peykan6 = (ImageView) view.findViewById(R.id.imageButton6);
		peykan5 = (ImageView) view.findViewById(R.id.imageButton7);
		link1 = (RelativeLayout) view.findViewById(R.id.Layoutlink1);

		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		link2 = (RelativeLayout) view.findViewById(R.id.Layoutlink2);

		link1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new ProvinceFragment());
				trans.commit();

			}
		});

		btnCmt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog = new DialogcmtInfroum(IntroductionFragment.this,
						getActivity(), R.layout.dialog_addcomment);
				dialog.show();

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
