package com.project.mechanic.fragment;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.project.mechanic.MainActivity;
import com.project.mechanic.R;
import com.project.mechanic.adapter.FroumtitleListadapter;
import com.project.mechanic.entity.Froum;
import com.project.mechanic.model.DataBaseAdapter;

public class FroumtitleFragment extends Fragment {
	private ImageButton addtitle;
	private DialogfroumTitle dialog;
	DataBaseAdapter mdb;
	View view;
	ArrayList<Froum> mylist;
	ListView lst;
	FroumtitleListadapter ListAdapter;
	public static final int DIALOG_FRAGMENT = 1;

	@SuppressLint("InflateParams")
	@Override
	public View onCreateView(android.view.LayoutInflater inflater,
			android.view.ViewGroup container, Bundle savedInstanceState) {

		((MainActivity) getActivity()).setActivityTitle(R.string.Forums);
		view = inflater.inflate(R.layout.fragment_titlefrm, null);
		addtitle = (ImageButton) view.findViewById(R.id.imgBtnAddcmt);

		mdb = new DataBaseAdapter(getActivity());
		mdb.open();
		mylist = mdb.getAllFroum();
		mdb.close();

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
				R.layout.froumtitleitem, mylist);
		lst.setAdapter(ListAdapter);

		lst.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

				FragmentTransaction trans = getActivity()
						.getSupportFragmentManager().beginTransaction();
				trans.replace(R.id.content_frame, new FroumFragment());
				trans.commit();
			}
		});
		return view;
	}

	public void updateView() {
		mdb.open();
		mylist = mdb.getAllFroum();
		mdb.close();

		ListAdapter = new FroumtitleListadapter(getActivity(),
				R.layout.froumtitleitem, mylist);
		ListAdapter.notifyDataSetChanged();
		lst.setAdapter(ListAdapter);

	}

}