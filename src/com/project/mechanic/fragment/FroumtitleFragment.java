package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.entity.Users;
import com.project.mechanic.model.DataBaseAdapter;
import com.project.mechanic.utility.Utility;

public class FroumtitleFragment extends Fragment {
	private ImageButton addtitle;
	private DialogfroumTitle dialog;
	DialogcmtInfroum dialog2;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Froum> mylist;
	ListView lst;
	FroumtitleListadapter ListAdapter;
	ImageButton Replytocm;
	public static final int DIALOG_FRAGMENT = 1;
	Utility util;
	Users Currentuser;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		//((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		view = inflater.inflate(R.layout.fragment_titlefrm, null);
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt_CmtFroum);

		mdb = new DataBaseAdapter(getActivity());
		mdb.open();
		mylist = mdb.getAllFroum();
		mdb.close();

		util = new Utility(getActivity());

		Currentuser = util.getCurrentUser();

		if (Currentuser == null)
			addtitle.setVisibility(View.INVISIBLE);

		addtitle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				dialog = new DialogfroumTitle(getActivity(),
						R.layout.dialog_addtitle, FroumtitleFragment.this);
				dialog.show();
			}
		});

		lst = (ListView) view.findViewById(R.id.lstComment);
		ListAdapter = new FroumtitleListadapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		lst.setAdapter(ListAdapter);

		return view;
	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllFroum();
		mdb.close();

		ListAdapter = new FroumtitleListadapter(getActivity(),
				R.layout.raw_froumtitle, mylist);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);

	}

}
